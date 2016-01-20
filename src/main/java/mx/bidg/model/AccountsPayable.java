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
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "ACCOUNTS_PAYABLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AccountsPayable implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT_PAYABLE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayable;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAID_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal paidAmount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAY_NUM")
    @JsonView(JsonViews.Root.class)
    private int payNum;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_PAYMENTS")
    @JsonView(JsonViews.Root.class)
    private int totalPayments;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "DUE_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime dueDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @JoinColumn(name = "ID_ACCOUNT_PAYABLE_STATUS", referencedColumnName = "ID_ACCOUNT_PAYABLE_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CAccountsPayableStatus idAccountPayableStatus;
    
    @JoinColumn(name = "ID_OPERATION_TYPE", referencedColumnName = "ID_OPERATION_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private COperationTypes idOperationType;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies idCurrency;

    public AccountsPayable() {
    }

    public AccountsPayable(Integer idAccountPayable) {
        this.idAccountPayable = idAccountPayable;
    }

    public AccountsPayable(Integer idAccountPayable, String folio, BigDecimal amount, BigDecimal paidAmount, int payNum, int totalPayments, LocalDateTime creationDate, int idAccessLevel) {
        this.idAccountPayable = idAccountPayable;
        this.folio = folio;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.payNum = payNum;
        this.totalPayments = totalPayments;
        this.creationDate = creationDate;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdAccountPayable() {
        return idAccountPayable;
    }

    public void setIdAccountPayable(Integer idAccountPayable) {
        this.idAccountPayable = idAccountPayable;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }

    public int getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(int totalPayments) {
        this.totalPayments = totalPayments;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CAccountsPayableStatus getIdAccountPayableStatus() {
        return idAccountPayableStatus;
    }

    public void setIdAccountPayableStatus(CAccountsPayableStatus idAccountPayableStatus) {
        this.idAccountPayableStatus = idAccountPayableStatus;
    }

    public COperationTypes getIdOperationType() {
        return idOperationType;
    }

    public void setIdOperationType(COperationTypes idOperationType) {
        this.idOperationType = idOperationType;
    }

    public CCurrencies getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(CCurrencies idCurrency) {
        this.idCurrency = idCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountPayable != null ? idAccountPayable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountsPayable)) {
            return false;
        }
        AccountsPayable other = (AccountsPayable) object;
        if ((this.idAccountPayable == null && other.idAccountPayable != null) || (this.idAccountPayable != null && !this.idAccountPayable.equals(other.idAccountPayable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AccountsPayable[ idAccountPayable=" + idAccountPayable + " ]";
    }
    
}
