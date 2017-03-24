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
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_ACCOUNTING_ACCOUNTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetAccountingAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_BUDGET_ACCOUNTING_ACCOUNTS")
    private Integer idBudgetAccountingAccounts;

    @Column(name = "ID_CONCEPT_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idConceptBudget;

    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private AccountingAccounts accountingAccount;

    @JoinColumn(name = "ID_CONCEPT_BUDGET", referencedColumnName = "ID_CONCEPT_BUDGET")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CConceptBudget cConceptBudget;

    public BudgetAccountingAccounts() {
    }

    public BudgetAccountingAccounts(Integer idBudgetAccountingAccounts) {
        this.idBudgetAccountingAccounts = idBudgetAccountingAccounts;
    }

    public BudgetAccountingAccounts(Integer idBudgetAccountingAccounts, LocalDateTime creationDate) {
        this.idBudgetAccountingAccounts = idBudgetAccountingAccounts;
        this.creationDate = creationDate;
    }

    public Integer getIdBudgetAccountingAccounts() {
        return idBudgetAccountingAccounts;
    }

    public void setIdBudgetAccountingAccounts(Integer idBudgetAccountingAccounts) {
        this.idBudgetAccountingAccounts = idBudgetAccountingAccounts;
    }

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public AccountingAccounts getAccountingAccount() {
        return accountingAccount;
    }

    public void setAccountingAccount(AccountingAccounts accountingAccount) {
        this.accountingAccount = accountingAccount;
    }

    public Integer getIdConceptBudget() {
        return idConceptBudget;
    }

    public void setIdConceptBudget(Integer idConceptBudget) {
        this.idConceptBudget = idConceptBudget;
    }

    public CConceptBudget getcConceptBudget() {
        return cConceptBudget;
    }

    public void setcConceptBudget(CConceptBudget cConceptBudget) {
        this.cConceptBudget = cConceptBudget;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetAccountingAccounts != null ? idBudgetAccountingAccounts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetAccountingAccounts)) {
            return false;
        }
        BudgetAccountingAccounts other = (BudgetAccountingAccounts) object;
        if ((this.idBudgetAccountingAccounts == null && other.idBudgetAccountingAccounts != null) || (this.idBudgetAccountingAccounts != null && !this.idBudgetAccountingAccounts.equals(other.idBudgetAccountingAccounts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetAccountingAccounts[ idBudgetAccountingAccounts=" + idBudgetAccountingAccounts + " ]";
    }

}
