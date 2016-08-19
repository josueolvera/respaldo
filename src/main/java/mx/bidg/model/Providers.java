/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "PROVIDERS")
public class Providers implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVIDER")
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PROVIDER_NAME")
    @JsonView(JsonViews.Root.class)
    private String providerName;

    @Column(name = "BUSINESS_NAME")
    @Size(max = 1024)
    @JsonView(JsonViews.Root.class)
    private String businessName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RFC")
    @JsonView(JsonViews.Root.class)
    private String rfc;

    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingAccount;

    @Column(name = "SUPPLIER_LOW")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime supplierLow;

    @Column(name = "CREDIT_DAYS")
    @NotNull
    @JsonView(JsonViews.Root.class)
    private int creditDays;

    @Column(name = "CUTTING_DATE")
    @NotNull
    @JsonView(JsonViews.Root.class)
    private int cuttingDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provider")
    @JsonView(JsonViews.Embedded.class)
    private List<ProvidersAccounts> providersAccountsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provider")
    @JsonView(JsonViews.Embedded.class)
    private List<ProvidersContact> providersContactList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provider")
    @JsonView(JsonViews.Embedded.class)
    private List<ProviderAddress> providerAddressList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provider")
    @JsonView(JsonViews.Embedded.class)
    private List<ProvidersBudgetSubcategories> providersBudgetSubcategories;

    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AccountingAccounts accountingAccounts;


    public Providers() {
    }

    public Providers(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Providers(Integer idProvider, String providerName, String rfc) {
        this.idProvider = idProvider;
        this.providerName = providerName;
        this.rfc = rfc;
    }

    public int getCuttingDate() {
        return cuttingDate;
    }

    public void setCuttingDate(int cuttingDate) {
        this.cuttingDate = cuttingDate;
    }

    public int getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(int creditDays) {
        this.creditDays = creditDays;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public List<ProvidersAccounts> getProvidersAccountsList() {
        return providersAccountsList;
    }

    public void setProvidersAccountsList(List<ProvidersAccounts> providersAccountsList) {
        this.providersAccountsList = providersAccountsList;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public LocalDateTime getSupplierLow() {
        return supplierLow;
    }

    public void setSupplierLow(LocalDateTime supplierLow) {
        this.supplierLow = supplierLow;
    }

    public List<ProviderAddress> getProviderAddressList() {
        return providerAddressList;
    }

    public void setProviderAddressList(List<ProviderAddress> providerAddressList) {
        this.providerAddressList = providerAddressList;
    }

    public int getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(int idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }


    public List<ProvidersContact> getProvidersContactList() {
        return providersContactList;
    }

    public void setProvidersContactList(List<ProvidersContact> providersContactList) {
        this.providersContactList = providersContactList;
    }

    @JsonManagedReference
    public List<ProvidersBudgetSubcategories> getProvidersBudgetSubcategories() {
        return providersBudgetSubcategories;
    }

    public void setProvidersBudgetSubcategories(List<ProvidersBudgetSubcategories> providersBudgetSubcategories) {
        this.providersBudgetSubcategories = providersBudgetSubcategories;
    }

    public AccountingAccounts getAccountingAccounts() {
        return accountingAccounts;
    }

    public void setAccountingAccounts(AccountingAccounts accountingAccounts) {
        this.accountingAccounts = accountingAccounts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProvider != null ? idProvider.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Providers)) {
            return false;
        }
        Providers other = (Providers) object;
        if ((this.idProvider == null && other.idProvider != null) || (this.idProvider != null && !this.idProvider.equals(other.idProvider))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Providers[ idProvider=" + idProvider + " ]";
    }

}
