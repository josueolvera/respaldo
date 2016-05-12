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
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(
        name = "ACCOUNTS_PAYABLE",
        indexes = {@Index(name = "REQUEST_FOLIO_INDEX", columnList = "FOLIO")}
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AccountsPayable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCOUNT_PAYABLE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayable;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

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
    @Column(name = "CREATION_DATE", updatable = false)
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

    @Column(name = "ID_ACCOUNT_PAYABLE_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayableStatus;

    @Column(name = "ID_OPERATION_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idOperationType;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RATE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rate;

    @JoinColumn(name = "ID_ACCOUNT_PAYABLE_STATUS", referencedColumnName = "ID_ACCOUNT_PAYABLE_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CAccountsPayableStatus accountPayableStatus;

    @JoinColumn(name = "ID_OPERATION_TYPE", referencedColumnName = "ID_OPERATION_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private COperationTypes operationType;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;

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

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public DateFormatsPojo getDueDateFormats() {
        return (dueDate == null) ? null : new DateFormatsPojo(dueDate);
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

    public Integer getIdAccountPayableStatus() {
        return idAccountPayableStatus;
    }

    public void setIdAccountPayableStatus(Integer idAccountPayableStatus) {
        this.idAccountPayableStatus = idAccountPayableStatus;
    }

    public Integer getIdOperationType() {
        return idOperationType;
    }

    public void setIdOperationType(Integer idOperationType) {
        this.idOperationType = idOperationType;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public CAccountsPayableStatus getAccountPayableStatus() {
        return accountPayableStatus;
    }

    public void setAccountPayableStatus(CAccountsPayableStatus accountPayableStatus) {
        this.accountPayableStatus = accountPayableStatus;
    }

    public COperationTypes getOperationType() {
        return operationType;
    }

    public void setOperationType(COperationTypes operationType) {
        this.operationType = operationType;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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
