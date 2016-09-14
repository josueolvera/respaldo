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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "PRICE_ESTIMATIONS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PriceEstimations implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ESTIMATION")
    @JsonView(JsonViews.Root.class)
    private Integer idEstimation;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Size(max = 100)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Private.class)
    private String filePath;
    
    @Size(max = 45)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String fileName;
    
    @Size(max = 45)
    @Column(name = "SKU")
    @JsonView(JsonViews.Root.class)
    private String sku;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "OUT_OF_BUDGET")
    @JsonView(JsonViews.Root.class)
    private int outOfBudget;
    
    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;
    
    @Column(name = "ID_ESTIMATION_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEstimationStatus;
    
    @Column(name = "ID_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccount;
    
    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idCurrency;
    
    @Column(name = "USER_AUTHORIZATION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUserAuthorization;
    
    @Column(name = "USER_ESTIMATION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUserEstimation;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RATE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rate;
    
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Requests request;
    
    @JoinColumn(name = "ID_ESTIMATION_STATUS", referencedColumnName = "ID_ESTIMATION_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CEstimationStatus estimationStatus;
    
    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Accounts account;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "AUTHORIZATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime authorizationDate;
    
    @JoinColumn(name = "USER_AUTHORIZATION", referencedColumnName = "ID_USER")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Users userAuthorization;
    
    @JoinColumn(name = "USER_ESTIMATION", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users userEstimation;

    public PriceEstimations() {
    }

    public PriceEstimations(Integer idEstimation) {
        this.idEstimation = idEstimation;
    }

    public PriceEstimations(Integer idEstimation, BigDecimal amount, int idAccessLevel) {
        this.idEstimation = idEstimation;
        this.amount = amount;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdEstimation() {
        return idEstimation;
    }

    public void setIdEstimation(Integer idEstimation) {
        this.idEstimation = idEstimation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public int getOutOfBudget() {
        return outOfBudget;
    }

    public void setOutOfBudget(int outOfBudget) {
        this.outOfBudget = outOfBudget;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(Integer idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(int idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Requests getRequest() {
        return request;
    }

    public void setRequest(Requests request) {
        this.request = request;
    }

    public CEstimationStatus getEstimationStatus() {
        return estimationStatus;
    }

    public void setEstimationStatus(CEstimationStatus estimationStatus) {
        this.estimationStatus = estimationStatus;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(LocalDateTime authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public DateFormatsPojo getAuthorizationDateFormats() {
        return (authorizationDate != null) ? new DateFormatsPojo(authorizationDate) : null;
    }

    public Users getUserAuthorization() {
        return userAuthorization;
    }

    public void setUserAuthorization(Users userAuthorization) {
        this.userAuthorization = userAuthorization;
    }

    public Users getUserEstimation() {
        return userEstimation;
    }

    public void setUserEstimation(Users userEstimation) {
        this.userEstimation = userEstimation;
    }

    public Integer getIdUserAuthorization() {
        return idUserAuthorization;
    }

    public void setIdUserAuthorization(Integer idUserAuthorization) {
        this.idUserAuthorization = idUserAuthorization;
    }

    public Integer getIdUserEstimation() {
        return idUserEstimation;
    }

    public void setIdUserEstimation(Integer idUserEstimation) {
        this.idUserEstimation = idUserEstimation;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmountMXN() {
        if (amount == null) return null;
        if (rate == null || rate.compareTo(BigDecimal.ZERO) == 0) return null;
        if (rate.compareTo(BigDecimal.ONE) == 0) return null;
        return amount.multiply(rate).setScale(2);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstimation != null ? idEstimation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriceEstimations)) {
            return false;
        }
        PriceEstimations other = (PriceEstimations) object;
        if ((this.idEstimation == null && other.idEstimation != null) || (this.idEstimation != null && !this.idEstimation.equals(other.idEstimation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.config.PriceEstimations[ idEstimation=" + idEstimation + " ]";
    }
    
}
