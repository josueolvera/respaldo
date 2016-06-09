package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "BALANCES")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Balances implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BALANCE")
    @JsonView(JsonViews.Root.class)
    private Integer idBalance;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "INITIAL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal initialAmount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "INITIAL_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime initialDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CURRENT_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal currentAmount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODIFICATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime modificationDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Column(name="ID_CURRENCY", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @Column(name="ID_ADMON_ACCOUNT", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idAdmonAccount;

    @Column(name="ID_BALANCE_STATUS", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idBalanceStatus;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currencies;
    
    @JoinColumn(name = "ID_ADMON_ACCOUNT", referencedColumnName = "ID_ADMON_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AdmonAccounts admonAccounts;
    
    @JoinColumn(name = "ID_BALANCE_STATUS", referencedColumnName = "ID_BALANCE_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBalanceStatus balanceStatus;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "balances")
    @JsonView(JsonViews.Embedded.class)
    private List<Transactions> transactionsList;

    public Balances() {
    }

    public Balances(Integer idBalance) {
        this.idBalance = idBalance;
    }

    public Balances(Integer idBalance, BigDecimal initialAmount, LocalDateTime initialDate, BigDecimal currentAmount, LocalDateTime modificationDate, int idAccessLevel) {
        this.idBalance = idBalance;
        this.initialAmount = initialAmount;
        this.initialDate = initialDate;
        this.currentAmount = currentAmount;
        this.modificationDate = modificationDate;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(Integer idBalance) {
        this.idBalance = idBalance;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
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

    public AdmonAccounts getAdmonAccounts() {
        return admonAccounts;
    }

    public void setAdmonAccounts(AdmonAccounts admonAccounts) {
        this.admonAccounts = admonAccounts;
    }

    public CBalanceStatus getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(CBalanceStatus cBalanceStatus) {
        this.balanceStatus = cBalanceStatus;
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdBalanceStatus() {
        return idBalanceStatus;
    }

    public void setIdBalanceStatus(Integer idBalanceStatus) {
        this.idBalanceStatus = idBalanceStatus;
    }

    public Integer getIdAdmonAccount() {
        return idAdmonAccount;
    }

    public void setIdAdmonAccount(Integer idAdmonAccount) {
        this.idAdmonAccount = idAdmonAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBalance != null ? idBalance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Balances)) {
            return false;
        }
        Balances other = (Balances) object;
        if ((this.idBalance == null && other.idBalance != null) || (this.idBalance != null && !this.idBalance.equals(other.idBalance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Balances[ idBalance=" + idBalance + " ]";
    }
    
}
