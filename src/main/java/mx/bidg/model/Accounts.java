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
import org.hibernate.envers.Audited;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "ACCOUNTS")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Accounts implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idAccount;
    
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
    private int idAccessLevel;
    
    @Column(name = "ID_BANK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBank;
    
    @Column(name = "ID_ACCOUNT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountType;
    
    @JoinColumn(name = "ID_BANK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBanks bank;
    
    @JoinColumn(name = "ID_ACCOUNT_TYPE", referencedColumnName = "ID_ACCOUNT_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CAccountsTypes accountType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    @JsonView(JsonViews.Embedded.class)
    private List<EmployeesAccounts> employeesAccountsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    @JsonView(JsonViews.Embedded.class)
    private List<ProvidersAccounts> providersAccountsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    @JsonView(JsonViews.Embedded.class)
    private List<PriceEstimations> priceEstimationsList;

    public Accounts() {
    }

    public Accounts(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
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

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBank() {
        return idBank;
    }

    public void setIdBank(Integer idBank) {
        this.idBank = idBank;
    }

    public Integer getIdAccountType() {
        return idAccountType;
    }

    public void setIdAccountType(Integer idAccountType) {
        this.idAccountType = idAccountType;
    }

    public CBanks getBank() {
        return bank;
    }

    public void setBank(CBanks bank) {
        this.bank = bank;
    }

    public CAccountsTypes getAccountType() {
        return accountType;
    }

    public void setAccountType(CAccountsTypes accountType) {
        this.accountType = accountType;
    }

    public List<EmployeesAccounts> getEmployeesAccountsList() {
        return employeesAccountsList;
    }

    public void setEmployeesAccountsList(List<EmployeesAccounts> employeesAccountsList) {
        this.employeesAccountsList = employeesAccountsList;
    }

    public List<ProvidersAccounts> getProvidersAccountsList() {
        return providersAccountsList;
    }

    public void setProvidersAccountsList(List<ProvidersAccounts> providersAccountsList) {
        this.providersAccountsList = providersAccountsList;
    }

    public List<PriceEstimations> getPriceEstimationsList() {
        return priceEstimationsList;
    }

    public void setPriceEstimationsList(List<PriceEstimations> priceEstimationsList) {
        this.priceEstimationsList = priceEstimationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccount != null ? idAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.idAccount == null && other.idAccount != null) || (this.idAccount != null && !this.idAccount.equals(other.idAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Accounts[ idAccount=" + idAccount + " ]";
    }
    
}
