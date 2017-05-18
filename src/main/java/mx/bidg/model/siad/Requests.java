package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
public class Requests {
    private int idRequest;
    private Integer idDistributorCostCenter;
    private Integer idRequestStatus;
    private Integer idRequestCategory;
    private Integer idRequestType;
    private Integer idEmployee;
    private String folio;
    private String reason;
    private int totalExpended;
    private int idAccessLevel;
    private int username;
    private Timestamp creationDate;
    private Collection<PriceEstimations> priceEstimationsByIdRequest;
    private CRequestStatus cRequestStatusByIdRequestStatus;
    private CRequestsCategories cRequestsCategoriesByIdRequestCategory;
    private CRequestTypes cRequestTypesByIdRequestType;
    private Collection<RequestProducts> requestProductsByIdRequest;

    @Id
    @Column(name = "ID_REQUEST", nullable = false)
    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    @Basic
    @Column(name = "ID_DISTRIBUTOR_COST_CENTER", nullable = true)
    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    @Basic
    @Column(name = "ID_REQUEST_STATUS", nullable = true)
    public Integer getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    @Basic
    @Column(name = "ID_REQUEST_CATEGORY", nullable = true)
    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    @Basic
    @Column(name = "ID_REQUEST_TYPE", nullable = true)
    public Integer getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(Integer idRequestType) {
        this.idRequestType = idRequestType;
    }

    @Basic
    @Column(name = "ID_EMPLOYEE", nullable = true)
    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Basic
    @Column(name = "FOLIO", nullable = false, length = 40)
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    @Basic
    @Column(name = "REASON", nullable = true, length = 200)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "TOTAL_EXPENDED", nullable = false)
    public int getTotalExpended() {
        return totalExpended;
    }

    public void setTotalExpended(int totalExpended) {
        this.totalExpended = totalExpended;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = false)
    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Basic
    @Column(name = "USERNAME", nullable = false)
    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    @Basic
    @Column(name = "CREATION_DATE", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requests requests = (Requests) o;

        if (idRequest != requests.idRequest) return false;
        if (totalExpended != requests.totalExpended) return false;
        if (idAccessLevel != requests.idAccessLevel) return false;
        if (username != requests.username) return false;
        if (idDistributorCostCenter != null ? !idDistributorCostCenter.equals(requests.idDistributorCostCenter) : requests.idDistributorCostCenter != null)
            return false;
        if (idRequestStatus != null ? !idRequestStatus.equals(requests.idRequestStatus) : requests.idRequestStatus != null)
            return false;
        if (idRequestCategory != null ? !idRequestCategory.equals(requests.idRequestCategory) : requests.idRequestCategory != null)
            return false;
        if (idRequestType != null ? !idRequestType.equals(requests.idRequestType) : requests.idRequestType != null)
            return false;
        if (idEmployee != null ? !idEmployee.equals(requests.idEmployee) : requests.idEmployee != null) return false;
        if (folio != null ? !folio.equals(requests.folio) : requests.folio != null) return false;
        if (reason != null ? !reason.equals(requests.reason) : requests.reason != null) return false;
        if (creationDate != null ? !creationDate.equals(requests.creationDate) : requests.creationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRequest;
        result = 31 * result + (idDistributorCostCenter != null ? idDistributorCostCenter.hashCode() : 0);
        result = 31 * result + (idRequestStatus != null ? idRequestStatus.hashCode() : 0);
        result = 31 * result + (idRequestCategory != null ? idRequestCategory.hashCode() : 0);
        result = 31 * result + (idRequestType != null ? idRequestType.hashCode() : 0);
        result = 31 * result + (idEmployee != null ? idEmployee.hashCode() : 0);
        result = 31 * result + (folio != null ? folio.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + totalExpended;
        result = 31 * result + idAccessLevel;
        result = 31 * result + username;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "requestsByIdRequest")
    public Collection<PriceEstimations> getPriceEstimationsByIdRequest() {
        return priceEstimationsByIdRequest;
    }

    public void setPriceEstimationsByIdRequest(Collection<PriceEstimations> priceEstimationsByIdRequest) {
        this.priceEstimationsByIdRequest = priceEstimationsByIdRequest;
    }

    @ManyToOne
    @JoinColumn(name = "ID_REQUEST_STATUS", referencedColumnName = "ID_REQUEST_STATUS")
    public CRequestStatus getcRequestStatusByIdRequestStatus() {
        return cRequestStatusByIdRequestStatus;
    }

    public void setcRequestStatusByIdRequestStatus(CRequestStatus cRequestStatusByIdRequestStatus) {
        this.cRequestStatusByIdRequestStatus = cRequestStatusByIdRequestStatus;
    }

    @ManyToOne
    @JoinColumn(name = "ID_REQUEST_CATEGORY", referencedColumnName = "ID_REQUEST_CATEGORY")
    public CRequestsCategories getcRequestsCategoriesByIdRequestCategory() {
        return cRequestsCategoriesByIdRequestCategory;
    }

    public void setcRequestsCategoriesByIdRequestCategory(CRequestsCategories cRequestsCategoriesByIdRequestCategory) {
        this.cRequestsCategoriesByIdRequestCategory = cRequestsCategoriesByIdRequestCategory;
    }

    @ManyToOne
    @JoinColumn(name = "ID_REQUEST_TYPE", referencedColumnName = "ID_REQUEST_TYPE")
    public CRequestTypes getcRequestTypesByIdRequestType() {
        return cRequestTypesByIdRequestType;
    }

    public void setcRequestTypesByIdRequestType(CRequestTypes cRequestTypesByIdRequestType) {
        this.cRequestTypesByIdRequestType = cRequestTypesByIdRequestType;
    }

    @OneToMany(mappedBy = "requestsByIdRequest")
    public Collection<RequestProducts> getRequestProductsByIdRequest() {
        return requestProductsByIdRequest;
    }

    public void setRequestProductsByIdRequest(Collection<RequestProducts> requestProductsByIdRequest) {
        this.requestProductsByIdRequest = requestProductsByIdRequest;
    }
}
