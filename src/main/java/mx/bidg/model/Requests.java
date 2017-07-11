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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;


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
    @Basic(optional = false)
    @Column(name = "ID_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_DISTRIBUTOR_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorCostCenter;

    @Column(name = "ID_REQUEST_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestStatus;

    @Column(name = "ID_REQUEST_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategory;

    @Column(name = "ID_REQUEST_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestType;

    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Basic
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Size(min = 1, max = 200)
    @Column(name = "REASON_RESPOSINBLE")
    @JsonView(JsonViews.Root.class)
    private String reasonResponsible;

    @Size(min = 1, max = 200)
    @Column(name = "REASON")
    @JsonView(JsonViews.Root.class)
    private String reason;

    @Column(name = "TOTAL_EXPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalExpended;

    @Basic
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Basic
    @NotNull
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String userName;

    @Basic
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_REQUEST_STATUS", referencedColumnName = "ID_REQUEST_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRequestStatus requestStatus;

    @JoinColumn(name = "ID_REQUEST_CATEGORY", referencedColumnName = "ID_REQUEST_CATEGORY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRequestsCategories requestCategory;

    @JoinColumn(name = "ID_REQUEST_TYPE", referencedColumnName = "ID_REQUEST_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRequestTypes requestType;

    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Employees employees;

    @JoinColumn(name = "ID_DISTRIBUTOR_COST_CENTER", referencedColumnName = "ID_DISTRIBUTOR_COST_CENTER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private DistributorCostCenter distributorCostCenter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<PriceEstimations> priceEstimationsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestProducts> requestProductsList;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DwEmployees dwEmployees;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private PurchaseInvoices purchaseInvoices;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private RequestsDates requestsDates;

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

    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    public Integer getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(Integer idRequestType) {
        this.idRequestType = idRequestType;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public BigDecimal getTotalExpended() {
        return totalExpended;
    }

    public void setTotalExpended(BigDecimal totalExpended) {
        this.totalExpended = totalExpended;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(CRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public CRequestsCategories getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(CRequestsCategories requestCategory) {
        this.requestCategory = requestCategory;
    }

    public CRequestTypes getRequestType() {
        return requestType;
    }

    public void setRequestType(CRequestTypes requestType) {
        this.requestType = requestType;
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

    public Integer getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
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

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public DistributorCostCenter getDistributorCostCenter() {
        return distributorCostCenter;
    }

    public void setDistributorCostCenter(DistributorCostCenter distributorCostCenter) {
        this.distributorCostCenter = distributorCostCenter;
    }

    public DwEmployees getDwEmployees() {
        return dwEmployees;
    }

    public void setDwEmployees(DwEmployees dwEmployees) {
        this.dwEmployees = dwEmployees;
    }

    public PurchaseInvoices getPurchaseInvoices() {
        return purchaseInvoices;
    }

    public void setPurchaseInvoices(PurchaseInvoices purchaseInvoices) {
        this.purchaseInvoices = purchaseInvoices;
    }

    public RequestsDates getRequestsDates() {
        return requestsDates;
    }

    public void setRequestsDates(RequestsDates requestsDates) {
        this.requestsDates = requestsDates;
    }

    public String getReasonResponsible() {
        return reasonResponsible;
    }

    public void setReasonResponsible(String reasonResponsible) {
        this.reasonResponsible = reasonResponsible;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Requests)) return false;

        Requests requests = (Requests) o;

        if (idRequest != null ? !idRequest.equals(requests.idRequest) : requests.idRequest != null) return false;
        if (idDistributorCostCenter != null ? !idDistributorCostCenter.equals(requests.idDistributorCostCenter) : requests.idDistributorCostCenter != null)
            return false;
        if (idRequestStatus != null ? !idRequestStatus.equals(requests.idRequestStatus) : requests.idRequestStatus != null)
            return false;
        if (idRequestCategory != null ? !idRequestCategory.equals(requests.idRequestCategory) : requests.idRequestCategory != null)
            return false;
        if (idRequestType != null ? !idRequestType.equals(requests.idRequestType) : requests.idRequestType != null)
            return false;
        return folio != null ? folio.equals(requests.folio) : requests.folio == null;
    }

    @Override
    public int hashCode() {
        int result = idRequest != null ? idRequest.hashCode() : 0;
        result = 31 * result + (idDistributorCostCenter != null ? idDistributorCostCenter.hashCode() : 0);
        result = 31 * result + (idRequestStatus != null ? idRequestStatus.hashCode() : 0);
        result = 31 * result + (idRequestCategory != null ? idRequestCategory.hashCode() : 0);
        result = 31 * result + (idRequestType != null ? idRequestType.hashCode() : 0);
        result = 31 * result + (folio != null ? folio.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Requests{" +
                "idRequest=" + idRequest +
                ", folio='" + folio + '\'' +
                ", creationDate=" + creationDate +
                ", idAccessLevel=" + idAccessLevel +
                ", idRequestStatus=" + idRequestStatus +
                ", priceEstimationsList=" + priceEstimationsList +
                ", requestProductsList=" + requestProductsList +
                '}';
    }
}
