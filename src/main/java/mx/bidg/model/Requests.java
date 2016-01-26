/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "REQUESTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Requests implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "PURPOSE")
    @JsonView(JsonViews.Root.class)
    private String purpose;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Column(name = "USER_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUserRequest;
    
    @Column(name = "USER_RESPONSABLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUserResponsable;
    
    @Column(name = "ID_BUDGET_MONTH_BRANCH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthBranch;
    
    @Column(name = "ID_REQUEST_TYPE_PRODUCT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestTypeProduct;
    
    @Column(name = "ID_REQUEST_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestStatus;
    
    @JoinColumn(name = "USER_REQUEST", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users userRequest;
    
    @JoinColumn(name = "USER_RESPONSABLE", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users userResponsable;
    
    @JoinColumn(name = "ID_BUDGET_MONTH_BRANCH", referencedColumnName = "ID_BUDGET_MONTH_BRANCH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private BudgetMonthBranch budgetMonthBranch;
    
    @JoinColumn(name = "ID_REQUEST_TYPE_PRODUCT", referencedColumnName = "ID_REQUEST_TYPE_PRODUCT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private RequestTypesProduct requestTypeProduct;
    
    @JoinColumn(name = "ID_REQUEST_STATUS", referencedColumnName = "ID_REQUEST_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRequestStatus requestStatus;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<PriceEstimations> priceEstimationsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestProducts> requestProductsList;

    public Requests() {
    }

    public Requests(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Requests(Integer idRequest, String folio, String description, int idAccessLevel) {
        this.idRequest = idRequest;
        this.folio = folio;
        this.description = description;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdUserRequest() {
        return idUserRequest;
    }

    public void setIdUserRequest(Integer idUserRequest) {
        this.idUserRequest = idUserRequest;
    }

    public Integer getIdUserResponsable() {
        return idUserResponsable;
    }

    public void setIdUserResponsable(Integer idUserResponsable) {
        this.idUserResponsable = idUserResponsable;
    }

    public Integer getIdBudgetMonthBranch() {
        return idBudgetMonthBranch;
    }

    public void setIdBudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public Integer getIdRequestTypeProduct() {
        return idRequestTypeProduct;
    }

    public void setIdRequestTypeProduct(Integer idRequestTypeProduct) {
        this.idRequestTypeProduct = idRequestTypeProduct;
    }

    public Integer getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    public Users getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(Users userRequest) {
        this.userRequest = userRequest;
    }

    public Users getUserResponsable() {
        return userResponsable;
    }

    public void setUserResponsable(Users userResponsable) {
        this.userResponsable = userResponsable;
    }

    public BudgetMonthBranch getBudgetMonthBranch() {
        return budgetMonthBranch;
    }

    public void setBudgetMonthBranch(BudgetMonthBranch budgetMonthBranch) {
        this.budgetMonthBranch = budgetMonthBranch;
    }

    public RequestTypesProduct getRequestTypeProduct() {
        return requestTypeProduct;
    }

    public void setRequestTypeProduct(RequestTypesProduct requestTypeProduct) {
        this.requestTypeProduct = requestTypeProduct;
    }

    public CRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(CRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public List<PriceEstimations> getPriceEstimationsList() {
        return priceEstimationsList;
    }

    public void setPriceEstimationsList(List<PriceEstimations> priceEstimationsList) {
        this.priceEstimationsList = priceEstimationsList;
    }

    public List<RequestProducts> getRequestProductsList() {
        return requestProductsList;
    }

    public void setRequestProductsList(List<RequestProducts> requestProductsList) {
        this.requestProductsList = requestProductsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequest != null ? idRequest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requests)) {
            return false;
        }
        Requests other = (Requests) object;
        if ((this.idRequest == null && other.idRequest != null) || (this.idRequest != null && !this.idRequest.equals(other.idRequest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Requests[ idRequest=" + idRequest + " ]";
    }
    
}
