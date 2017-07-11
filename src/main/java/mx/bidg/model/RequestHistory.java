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
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author Desarrollador
 */
@Entity
@DynamicUpdate
@Table(name = "REQUEST_HISTORY")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_HISTORY")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestHistory;

    @Column(name = "ID_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_DISTRIBUTOR_COST_CENTER")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorCostCenter;

    @Column(name = "ID_REQUEST_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestStatus;

    @Column(name = "ID_REQUEST_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategory;

    @Column(name = "ID_REQUEST_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestType;

    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "ID_PRICE_ESTIMATION")
    @JsonView(JsonViews.Root.class)
    private Integer idPriceEstimation;

    @Column(name = "ID_REQUEST_USER")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestUser;

    @Column(name = "ID_RESPONSIBLE_USER")
    @JsonView(JsonViews.Root.class)
    private Integer idResponsibleUser;

    @Basic
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Size(min = 1, max = 200)
    @Column(name = "REASON_REQUEST")
    @JsonView(JsonViews.Root.class)
    private String reasonRequest;

    @Size(min = 1, max = 200)
    @Column(name = "REASON_RESPONSIBLE")
    @JsonView(JsonViews.Root.class)
    private String reasonResponsible;

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
    private String username;

    @Basic
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Basic
    @NotNull
    @Column(name = "HIGTH_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime higthDate;

    @Column(name = "R_STATUS")
    @JsonView(JsonViews.Root.class)
    private int rStatus;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private Requests request;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DistributorCostCenter distributorCostCenter;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private CRequestStatus cRequestStatus;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private CRequestsCategories cRequestCategory;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private CRequestTypes cRequestType;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private PriceEstimations priceEstimations;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private Users userRequest;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private Users userResponsible;


    public RequestHistory() {
    }

    public RequestHistory(Integer idRequestHistory) {
        this.idRequestHistory = idRequestHistory;
    }

    public Integer getIdRequestHistory() {
        return idRequestHistory;
    }

    public void setIdRequestHistory(Integer idRequestHistory) {
        this.idRequestHistory = idRequestHistory;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public Integer getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
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

    public Integer getIdPriceEstimation() {
        return idPriceEstimation;
    }

    public void setIdPriceEstimation(Integer idPriceEstimation) {
        this.idPriceEstimation = idPriceEstimation;
    }

    public Integer getIdRequestUser() {
        return idRequestUser;
    }

    public void setIdRequestUser(Integer idRequestUser) {
        this.idRequestUser = idRequestUser;
    }

    public Integer getIdResponsibleUser() {
        return idResponsibleUser;
    }

    public void setIdResponsibleUser(Integer idResponsibleUser) {
        this.idResponsibleUser = idResponsibleUser;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getReasonRequest() {
        return reasonRequest;
    }

    public void setReasonRequest(String reasonRequest) {
        this.reasonRequest = reasonRequest;
    }

    public String getReasonResponsible() {
        return reasonResponsible;
    }

    public void setReasonResponsible(String reasonResponsible) {
        this.reasonResponsible = reasonResponsible;
    }

    public BigDecimal getTotalExpended() {
        return totalExpended;
    }

    public void setTotalExpended(BigDecimal totalExpended) {
        this.totalExpended = totalExpended;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getHigthDate() {
        return higthDate;
    }

    public void setHigthDate(LocalDateTime higthDate) {
        this.higthDate = higthDate;
    }

    public int getrStatus() {
        return rStatus;
    }

    public void setrStatus(int rStatus) {
        this.rStatus = rStatus;
    }

    public Requests getRequest() {
        return request;
    }

    public void setRequest(Requests request) {
        this.request = request;
    }

    public DistributorCostCenter getDistributorCostCenter() {
        return distributorCostCenter;
    }

    public void setDistributorCostCenter(DistributorCostCenter distributorCostCenter) {
        this.distributorCostCenter = distributorCostCenter;
    }

    public CRequestStatus getcRequestStatus() {
        return cRequestStatus;
    }

    public void setcRequestStatus(CRequestStatus cRequestStatus) {
        this.cRequestStatus = cRequestStatus;
    }

    public CRequestsCategories getcRequestCategory() {
        return cRequestCategory;
    }

    public void setcRequestCategory(CRequestsCategories cRequestCategory) {
        this.cRequestCategory = cRequestCategory;
    }

    public CRequestTypes getcRequestType() {
        return cRequestType;
    }

    public void setcRequestType(CRequestTypes cRequestType) {
        this.cRequestType = cRequestType;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public PriceEstimations getPriceEstimations() {
        return priceEstimations;
    }

    public void setPriceEstimations(PriceEstimations priceEstimations) {
        this.priceEstimations = priceEstimations;
    }

    public Users getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(Users userRequest) {
        this.userRequest = userRequest;
    }

    public Users getUserResponsible() {
        return userResponsible;
    }

    public void setUserResponsible(Users userResponsible) {
        this.userResponsible = userResponsible;
    }

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public DateFormatsPojo getHigthDateFormats() {
        return (higthDate == null) ? null : new DateFormatsPojo(higthDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestHistory)) return false;

        RequestHistory that = (RequestHistory) o;

        if (idRequestHistory != null ? !idRequestHistory.equals(that.idRequestHistory) : that.idRequestHistory != null)
            return false;
        if (idRequest != null ? !idRequest.equals(that.idRequest) : that.idRequest != null) return false;
        if (idDistributorCostCenter != null ? !idDistributorCostCenter.equals(that.idDistributorCostCenter) : that.idDistributorCostCenter != null)
            return false;
        if (idRequestStatus != null ? !idRequestStatus.equals(that.idRequestStatus) : that.idRequestStatus != null)
            return false;
        if (idRequestCategory != null ? !idRequestCategory.equals(that.idRequestCategory) : that.idRequestCategory != null)
            return false;
        if (idRequestType != null ? !idRequestType.equals(that.idRequestType) : that.idRequestType != null)
            return false;
        if (idEmployee != null ? !idEmployee.equals(that.idEmployee) : that.idEmployee != null) return false;
        if (idPriceEstimation != null ? !idPriceEstimation.equals(that.idPriceEstimation) : that.idPriceEstimation != null)
            return false;
        if (idRequestUser != null ? !idRequestUser.equals(that.idRequestUser) : that.idRequestUser != null)
            return false;
        return idResponsibleUser != null ? idResponsibleUser.equals(that.idResponsibleUser) : that.idResponsibleUser == null;
    }

    @Override
    public int hashCode() {
        int result = idRequestHistory != null ? idRequestHistory.hashCode() : 0;
        result = 31 * result + (idRequest != null ? idRequest.hashCode() : 0);
        result = 31 * result + (idDistributorCostCenter != null ? idDistributorCostCenter.hashCode() : 0);
        result = 31 * result + (idRequestStatus != null ? idRequestStatus.hashCode() : 0);
        result = 31 * result + (idRequestCategory != null ? idRequestCategory.hashCode() : 0);
        result = 31 * result + (idRequestType != null ? idRequestType.hashCode() : 0);
        result = 31 * result + (idEmployee != null ? idEmployee.hashCode() : 0);
        result = 31 * result + (idPriceEstimation != null ? idPriceEstimation.hashCode() : 0);
        result = 31 * result + (idRequestUser != null ? idRequestUser.hashCode() : 0);
        result = 31 * result + (idResponsibleUser != null ? idResponsibleUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestHistory{" +
                "idRequestHistory=" + idRequestHistory +
                ", idRequest=" + idRequest +
                ", idDistributorCostCenter=" + idDistributorCostCenter +
                ", idRequestStatus=" + idRequestStatus +
                ", idRequestCategory=" + idRequestCategory +
                ", idRequestType=" + idRequestType +
                ", idEmployee=" + idEmployee +
                ", idPriceEstimation=" + idPriceEstimation +
                ", idRequestUser=" + idRequestUser +
                ", idResponsibleUser=" + idResponsibleUser +
                ", folio='" + folio + '\'' +
                ", totalExpended=" + totalExpended +
                ", idAccessLevel=" + idAccessLevel +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", higthDate=" + higthDate +
                ", rStatus=" + rStatus +
                '}';
    }
}
