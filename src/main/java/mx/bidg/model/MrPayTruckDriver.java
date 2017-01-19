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
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Size(max = 30)
    @Column(name = "AUTHORIZATION_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String authorizationNumber;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMMOUNT_COST")
    @JsonView(JsonViews.Root.class)
    private BigDecimal ammountCost;

    @Column(name = "COMMISSION_TRANSACTION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commissionTransaction;

    @Column(name = "ID_METHOD_PAY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idMethodPay;

    @Column(name = "PAYMENT_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime paymentDate;

    @Column(name = "PAYMENT_HOUR")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime paymentHour;

    @JoinColumn(name = "ID_METHOD_PAY", referencedColumnName = "ID_METHOD_PAY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CTypeMethodPay typeMethodPay;

    public MrPayTruckDriver() {
    }

    public MrPayTruckDriver(Integer idPayTruckDriver) {
        this.idPayTruckDriver = idPayTruckDriver;
    }

    public MrPayTruckDriver(Integer idPayTruckDriver, LocalDateTime paymentDate, LocalDateTime paymentHour) {
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

    public BigDecimal getAmmountCost() {
        return ammountCost;
    }

    public void setAmmountCost(BigDecimal ammountCost) {
        this.ammountCost = ammountCost;
    }

    public BigDecimal getCommissionTransaction() {
        return commissionTransaction;
    }

    public void setCommissionTransaction(BigDecimal commissionTransaction) {
        this.commissionTransaction = commissionTransaction;
    }

    public Integer getIdMethodPay() {
        return idMethodPay;
    }

    public void setIdMethodPay(Integer idMethodPay) {
        this.idMethodPay = idMethodPay;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDateTime getPaymentHour() {
        return paymentHour;
    }

    public void setPaymentHour(LocalDateTime paymentHour) {
        this.paymentHour = paymentHour;
    }

    public CTypeMethodPay getTypeMethodPay() {
        return typeMethodPay;
    }

    public void setTypeMethodPay(CTypeMethodPay typeMethodPay) {
        this.typeMethodPay = typeMethodPay;
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
