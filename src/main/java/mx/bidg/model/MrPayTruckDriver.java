/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.TimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "MR_PAY_TRUCK_DRIVER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class MrPayTruckDriver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PAY_TRUCK_DRIVER")
    @JsonView(JsonViews.Root.class)
    private Integer idPayTruckDriver;

    @Size(max = 100)
    @Column(name = "PAYMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private String paymentType;

    @Column(name = "PAYMENT_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate paymentDate;

    @Column(name = "PAYMENT_HOUR")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = TimeConverter.class)
    private LocalTime paymentHour;

    @Size(max = 100)
    @Column(name = "REFERENCE")
    @JsonView(JsonViews.Root.class)
    private String refrence;

    @Size(max = 100)
    @Column(name = "DETAILS")
    @JsonView(JsonViews.Root.class)
    private String details;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT_COST")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountCost;

    @Column(name = "COMMISSION_TRANSACTION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commissionTransaction;

    @Column(name = "IVA")
    @JsonView(JsonViews.Root.class)
    private BigDecimal Iva;

    @Size(max = 30)
    @Column(name = "AUTHORIZATION_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String authorizationNumber;

    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    public MrPayTruckDriver() {
    }

    public MrPayTruckDriver(Integer idPayTruckDriver) {
        this.idPayTruckDriver = idPayTruckDriver;
    }

    public MrPayTruckDriver(Integer idPayTruckDriver, LocalDate paymentDate, LocalTime paymentHour) {
        this.idPayTruckDriver = idPayTruckDriver;
        this.paymentDate = paymentDate;
        this.paymentHour = paymentHour;
    }

    public Integer getIdPayTruckDriver() {
        return idPayTruckDriver;
    }

    public void setIdPayTruckDriver(Integer idPayTruckDriver) {
        this.idPayTruckDriver = idPayTruckDriver;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public BigDecimal getAmountCost() {
        return amountCost;
    }

    public void setAmountCost(BigDecimal ammountCost) {
        this.amountCost = ammountCost;
    }

    public BigDecimal getCommissionTransaction() {
        return commissionTransaction;
    }

    public void setCommissionTransaction(BigDecimal commissionTransaction) {
        this.commissionTransaction = commissionTransaction;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalTime getPaymentHour() {
        return paymentHour;
    }

    public void setPaymentHour(LocalTime paymentHour) {
        this.paymentHour = paymentHour;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getIva() {
        return Iva;
    }

    public void setIva(BigDecimal iva) {
        Iva = iva;
    }

    public DateFormatsPojo getPaymentDateFormats() {
        if (paymentDate == null) {
            return null;
        }
        return new DateFormatsPojo(paymentDate);
    }

    public DateFormatsPojo getPaymentHourFormats() {
        if (paymentHour == null) {
            return null;
        }
        return new DateFormatsPojo(paymentHour);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPayTruckDriver != null ? idPayTruckDriver.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MrPayTruckDriver)) {
            return false;
        }
        MrPayTruckDriver other = (MrPayTruckDriver) object;
        if ((this.idPayTruckDriver == null && other.idPayTruckDriver != null) || (this.idPayTruckDriver != null && !this.idPayTruckDriver.equals(other.idPayTruckDriver))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.MrPayTruckDriver[ idPayTruckDriver=" + idPayTruckDriver + " ]";
    }
    
}
