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
import org.hibernate.annotations.Type;


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
    @Column(name = "ID_PRICE_ESTIMATION")
    @JsonView(JsonViews.Root.class)
    private Integer idPriceEstimation;

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccount;

    @Column(name = "ID_ESTIMATION_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEstimationStatus;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idCurrency;

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

    @Column(name = "AUTHORIZATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime authorizationDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Requests request;

    @JoinColumn(name = "ID_ESTIMATION_STATUS", referencedColumnName = "ID_ESTIMATION_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CEstimationStatus cEstimationStatus;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;

    public PriceEstimations() {
    }

    public Integer getIdPriceEstimation() {
        return idPriceEstimation;
    }

    public void setIdPriceEstimation(Integer idPriceEstimations) {
        this.idPriceEstimation = idPriceEstimations;
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


    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CEstimationStatus getcEstimationStatus() {
        return cEstimationStatus;
    }

    public void setcEstimationStatus(CEstimationStatus cEstimationStatus) {
        this.cEstimationStatus = cEstimationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceEstimations)) return false;

        PriceEstimations that = (PriceEstimations) o;

        if (getIdAccount() != that.getIdAccount()) return false;
        if (getIdCurrency() != that.getIdCurrency()) return false;
        if (getIdAccessLevel() != that.getIdAccessLevel()) return false;
        if (!getIdPriceEstimation().equals(that.getIdPriceEstimation())) return false;
        if (!getIdRequest().equals(that.getIdRequest())) return false;
        if (!getIdEstimationStatus().equals(that.getIdEstimationStatus())) return false;
        if (!getAmount().equals(that.getAmount())) return false;
        if (!getFilePath().equals(that.getFilePath())) return false;
        if (!getFileName().equals(that.getFileName())) return false;
        if (!getAuthorizationDate().equals(that.getAuthorizationDate())) return false;
        if (!getUsername().equals(that.getUsername())) return false;
        if (!getCreationDate().equals(that.getCreationDate())) return false;
        if (!getRequest().equals(that.getRequest())) return false;
        if (!getcEstimationStatus().equals(that.getcEstimationStatus())) return false;
        return getCurrency().equals(that.getCurrency());
    }

    @Override
    public int hashCode() {
        int result = getIdPriceEstimation().hashCode();
        result = 31 * result + getIdRequest().hashCode();
        result = 31 * result + getIdAccount();
        result = 31 * result + getIdEstimationStatus().hashCode();
        result = 31 * result + getIdCurrency();
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + getFilePath().hashCode();
        result = 31 * result + getFileName().hashCode();
        result = 31 * result + getAuthorizationDate().hashCode();
        result = 31 * result + getIdAccessLevel();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getCreationDate().hashCode();
        result = 31 * result + getRequest().hashCode();
        result = 31 * result + getcEstimationStatus().hashCode();
        result = 31 * result + getCurrency().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "mx.bidg.config.PriceEstimations[ idEstimation=" + idPriceEstimation + " ]";
    }
    
}
