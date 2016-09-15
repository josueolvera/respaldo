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
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGETS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Budgets implements Serializable {
        
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;
    
    @Column(name = "ID_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;
    
    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Column(name = "ID_BUDGET_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetType;

    @Column(name = "ID_BUDGET_NATURE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetNature;

    @JoinColumn(name = "ID_COST_CENTER", referencedColumnName = "ID_COST_CENTER")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CCostCenter costCenter;
    
    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private AccountingAccounts accountingAccount;

    @JoinColumn(name = "ID_BUDGET_TYPE", referencedColumnName = "ID_BUDGET_TYPE")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBudgetTypes budgetType;

    @JoinColumn(name = "ID_BUDGET_NATURE", referencedColumnName = "ID_BUDGET_NATURE")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBudgetNature budgetNature;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetYear> budgetYearList;

    public Budgets() {
    }

    public Budgets(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Budgets(CCostCenter costCenter, AccountingAccounts accountingAccount, CBudgetTypes budgetType, CBudgetNature budgetNature) {
        this.costCenter = costCenter;
        this.accountingAccount = accountingAccount;
        this.budgetType = budgetType;
        this.budgetNature = budgetNature;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public Integer getIdBudgetType() {
        return idBudgetType;
    }

    public void setIdBudgetType(Integer idBudgetType) {
        this.idBudgetType = idBudgetType;
    }

    public Integer getIdBudgetNature() {
        return idBudgetNature;
    }

    public void setIdBudgetNature(Integer idBudgetNature) {
        this.idBudgetNature = idBudgetNature;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CCostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public AccountingAccounts getAccountingAccount() {
        return accountingAccount;
    }

    public void setAccountingAccount(AccountingAccounts accountingAccount) {
        this.accountingAccount = accountingAccount;
    }

    public CBudgetTypes getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(CBudgetTypes budgetType) {
        this.budgetType = budgetType;
    }

    public CBudgetNature getBudgetNature() {
        return budgetNature;
    }

    public void setBudgetNature(CBudgetNature budgetNature) {
        this.budgetNature = budgetNature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budgets budgets1 = (Budgets) o;

        if (idCostCenter != null ? !idCostCenter.equals(budgets1.idCostCenter) : budgets1.idCostCenter != null)
            return false;
        if (idAccountingAccount != null ? !idAccountingAccount.equals(budgets1.idAccountingAccount) : budgets1.idAccountingAccount != null)
            return false;
        if (idBudgetType != null ? !idBudgetType.equals(budgets1.idBudgetType) : budgets1.idBudgetType != null)
            return false;
        return idBudgetNature != null ? idBudgetNature.equals(budgets1.idBudgetNature) : budgets1.idBudgetNature == null;

    }

    @Override
    public int hashCode() {
        int result = idCostCenter != null ? idCostCenter.hashCode() : 0;
        result = 31 * result + (idAccountingAccount != null ? idAccountingAccount.hashCode() : 0);
        result = 31 * result + (idBudgetType != null ? idBudgetType.hashCode() : 0);
        result = 31 * result + (idBudgetNature != null ? idBudgetNature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Budgets{" +
                "idBudget=" + idBudget +
                ", idCostCenter=" + idCostCenter +
                ", idAccountingAccount=" + idAccountingAccount +
                ", idBudgetType=" + idBudgetType +
                ", idBudgetNature=" + idBudgetNature +
                ", costCenter=" + costCenter +
                ", accountingAccount=" + accountingAccount +
                ", budgetType=" + budgetType +
                ", budgetNature=" + budgetNature +
                ", budgetYearList=" + budgetYearList +
                '}';
    }
}
