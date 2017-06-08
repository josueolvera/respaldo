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
    @Basic(optional = false)
    @Column(name = "ID_PRICE_ESTIMATION")
    @JsonView(JsonViews.Root.class)
    private Integer idPriceEstimation;

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_ESTIMATION_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEstimationStatus;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

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

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

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
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Requests request;

    @JoinColumn(name = "ID_ESTIMATION_STATUS", referencedColumnName = "ID_ESTIMATION_STATUS")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CEstimationStatus cEstimationStatus;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;


    public PriceEstimations() {
    }

    public Integer getIdPriceEstimation() {
        return idPriceEstimation;
    }

    public void setIdPriceEstimation(Integer idPriceEstimation) {
        this.idPriceEstimation = idPriceEstimation;
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

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public Integer getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(Integer idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
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

        if (idPriceEstimation != null ? !idPriceEstimation.equals(that.idPriceEstimation) : that.idPriceEstimation != null)
            return false;
        if (idRequest != null ? !idRequest.equals(that.idRequest) : that.idRequest != null) return false;
        if (idEstimationStatus != null ? !idEstimationStatus.equals(that.idEstimationStatus) : that.idEstimationStatus != null)
            return false;
        return idCurrency != null ? idCurrency.equals(that.idCurrency) : that.idCurrency == null;
    }

    @Override
    public int hashCode() {
        int result = idPriceEstimation != null ? idPriceEstimation.hashCode() : 0;
        result = 31 * result + (idRequest != null ? idRequest.hashCode() : 0);
        result = 31 * result + (idEstimationStatus != null ? idEstimationStatus.hashCode() : 0);
        result = 31 * result + (idCurrency != null ? idCurrency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PriceEstimations{" +
                "idPriceEstimation=" + idPriceEstimation +
                ", idRequest=" + idRequest +
                ", idEstimationStatus=" + idEstimationStatus +
                ", idCurrency=" + idCurrency +
                ", amount=" + amount +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", idAccessLevel=" + idAccessLevel +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", request=" + request +
                ", cEstimationStatus=" + cEstimationStatus +
                ", currency=" + currency +
                '}';
    }
}
