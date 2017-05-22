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

    @Basic
    @NotNull
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Requests)) return false;

        Requests requests = (Requests) o;

        if (getIdAccessLevel() != requests.getIdAccessLevel()) return false;
        if (getUserName() != requests.getUserName()) return false;
        if (!getIdRequest().equals(requests.getIdRequest())) return false;
        if (getIdDistributorCostCenter() != null ? !getIdDistributorCostCenter().equals(requests.getIdDistributorCostCenter()) : requests.getIdDistributorCostCenter() != null)
            return false;
        if (getIdRequestStatus() != null ? !getIdRequestStatus().equals(requests.getIdRequestStatus()) : requests.getIdRequestStatus() != null)
            return false;
        if (getIdRequestCategory() != null ? !getIdRequestCategory().equals(requests.getIdRequestCategory()) : requests.getIdRequestCategory() != null)
            return false;
        if (getIdRequestType() != null ? !getIdRequestType().equals(requests.getIdRequestType()) : requests.getIdRequestType() != null)
            return false;
        if (getIdEmployee() != null ? !getIdEmployee().equals(requests.getIdEmployee()) : requests.getIdEmployee() != null)
            return false;
        if (getFolio() != null ? !getFolio().equals(requests.getFolio()) : requests.getFolio() != null) return false;
        if (getReason() != null ? !getReason().equals(requests.getReason()) : requests.getReason() != null)
            return false;
        if (getTotalExpended() != null ? !getTotalExpended().equals(requests.getTotalExpended()) : requests.getTotalExpended() != null)
            return false;
        if (getCreationDate() != null ? !getCreationDate().equals(requests.getCreationDate()) : requests.getCreationDate() != null)
            return false;
        if (getPriceEstimationsList() != null ? !getPriceEstimationsList().equals(requests.getPriceEstimationsList()) : requests.getPriceEstimationsList() != null)
            return false;
        return getRequestProductsList() != null ? getRequestProductsList().equals(requests.getRequestProductsList()) : requests.getRequestProductsList() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdRequest().hashCode();
        result = 31 * result + (getIdDistributorCostCenter() != null ? getIdDistributorCostCenter().hashCode() : 0);
        result = 31 * result + (getIdRequestStatus() != null ? getIdRequestStatus().hashCode() : 0);
        result = 31 * result + (getIdRequestCategory() != null ? getIdRequestCategory().hashCode() : 0);
        result = 31 * result + (getIdRequestType() != null ? getIdRequestType().hashCode() : 0);
        result = 31 * result + (getIdEmployee() != null ? getIdEmployee().hashCode() : 0);
        result = 31 * result + (getFolio() != null ? getFolio().hashCode() : 0);
        result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
        result = 31 * result + (getTotalExpended() != null ? getTotalExpended().hashCode() : 0);
        result = 31 * result + getIdAccessLevel();
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getPriceEstimationsList() != null ? getPriceEstimationsList().hashCode() : 0);
        result = 31 * result + (getRequestProductsList() != null ? getRequestProductsList().hashCode() : 0);
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
