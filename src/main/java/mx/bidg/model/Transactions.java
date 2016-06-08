package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "TRANSACTIONS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACTION")
    @JsonView(JsonViews.Root.class)
    private Integer idTransaction;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Size(max = 45)
    @Column(name = "TRANSACTION_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String transactionNumber;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    
    @Column(name="ID_ACCOUNT_PAYABLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountsPayable;
    
    @Column(name = "ID_TRANSACTION_STATUS",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTransactionsStatus;
    
    @Column(name ="ID_BALANCE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBalance;
    
    @Column(name="ID_OPERATION_TYPE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idOperationTypes;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currencies;
    
    @JoinColumn(name = "ID_ACCOUNT_PAYABLE", referencedColumnName = "ID_ACCOUNT_PAYABLE")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private AccountsPayable accountsPayable;
    
    @JoinColumn(name = "ID_TRANSACTION_STATUS", referencedColumnName = "ID_TRANSACTION_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CTransactionsStatus transactionsStatus;
    
    @JoinColumn(name = "ID_BALANCE", referencedColumnName = "ID_BALANCE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Balances balances;
    
    @JoinColumn(name = "ID_OPERATION_TYPE", referencedColumnName = "ID_OPERATION_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private COperationTypes operationTypes;

    public Transactions() {
    }

    public Transactions(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Transactions(Integer idTransaction, BigDecimal amount, LocalDateTime creationDate, int idAccessLevel) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.creationDate = creationDate;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public CCurrencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(CCurrencies cCurrencies) {
        this.currencies = cCurrencies;
    }

    public AccountsPayable getAccountsPayable() {
        return accountsPayable;
    }

    public void setAccountsPayable(AccountsPayable accountsPayable) {
        this.accountsPayable = accountsPayable;
    }

    public CTransactionsStatus getTransactionsStatus() {
        return transactionsStatus;
    }

    public void setTransactionsStatus(CTransactionsStatus cTransactionsStatus) {
        this.transactionsStatus = cTransactionsStatus;
    }

    public Balances getBalances() {
        return balances;
    }

    public void setBalances(Balances balances) {
        this.balances = balances;
    }

    public COperationTypes getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(COperationTypes cOperationTypes) {
        this.operationTypes = cOperationTypes;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdAccountsPayable() {
        return idAccountsPayable;
    }

    public void setIdAccountsPayable(Integer idAccountsPayable) {
        this.idAccountsPayable = idAccountsPayable;
    }

    public Integer getIdTransactionsStatus() {
        return idTransactionsStatus;
    }

    public void setIdTransactionsStatus(Integer idTransactionsStatus) {
        this.idTransactionsStatus = idTransactionsStatus;
    }

    public Integer getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(Integer idBalance) {
        this.idBalance = idBalance;
    }

    public Integer getIdOperationTypes() {
        return idOperationTypes;
    }

    public void setIdOperationTypes(Integer idOperationTypes) {
        this.idOperationTypes = idOperationTypes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaction != null ? idTransaction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.idTransaction == null && other.idTransaction != null) || (this.idTransaction != null && !this.idTransaction.equals(other.idTransaction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Transactions[ idTransaction=" + idTransaction + " ]";
    }
    
}
