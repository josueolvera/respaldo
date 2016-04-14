package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "ADMON_ACCOUNTS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AdmonAccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADMON_ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idAdmonAccount;
    
    @Size(max = 45)
    @Column(name = "ACCOUNT_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String accountNumber;
    
    @Size(max = 45)
    @Column(name = "ACCOUNT_CLABE")
    @JsonView(JsonViews.Root.class)
    private String accountClabe;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Column(name="ID_DISTRIBUTOR",insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;
    
    @Column(name ="ID_BANK",insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBank;
    
    @Column(name = "ID_CURRENCY",insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    
    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributors;
    
    @JoinColumn(name = "ID_BANK", referencedColumnName = "ID_BANK")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBanks banks;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currencies;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admonAccounts")
    @JsonView(JsonViews.Embedded.class)
    private List<Balances> balancesList;

    public AdmonAccounts() {
    }

    public AdmonAccounts(Integer idAdmonAccount) {
        this.idAdmonAccount = idAdmonAccount;
    }

    public Integer getIdAdmonAccount() {
        return idAdmonAccount;
    }

    public void setIdAdmonAccount(Integer idAdmonAccount) {
        this.idAdmonAccount = idAdmonAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountClabe() {
        return accountClabe;
    }

    public void setAccountClabe(String accountClabe) {
        this.accountClabe = accountClabe;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CDistributors getDistributors() {
        return distributors;
    }

    public void setDistributors(CDistributors cDistributors) {
        this.distributors = cDistributors;
    }

    public CBanks getBanks() {
        return banks;
    }

    public void setBanks(CBanks cBanks) {
        this.banks = cBanks;
    }

    public CCurrencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(CCurrencies cCurrencies) {
        this.currencies = cCurrencies;
    }

    public List<Balances> getBalancesList() {
        return balancesList;
    }

    public void setBalancesList(List<Balances> balancesList) {
        this.balancesList = balancesList;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdBank() {
        return idBank;
    }

    public void setIdBank(Integer idBank) {
        this.idBank = idBank;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdmonAccount != null ? idAdmonAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmonAccounts)) {
            return false;
        }
        AdmonAccounts other = (AdmonAccounts) object;
        if ((this.idAdmonAccount == null && other.idAdmonAccount != null) || (this.idAdmonAccount != null && !this.idAdmonAccount.equals(other.idAdmonAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AdmonAccounts[ idAdmonAccount=" + idAdmonAccount + " ]";
    }
    
}
