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
    
    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;
    
    @Column(name = "ID_AREA", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idArea;
    
    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISENTRY")
    @JsonView(JsonViews.Root.class)
    private int isentry;
        
    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CDistributors distributor;
    
    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CAreas area;
    
    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private AccountingAccounts accountingAccount;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthBranch> budgetMonthBranchList;

    public Budgets() {
    }

    public Budgets(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Budgets(Integer idBudget, int idAccessLevel) {
        this.idBudget = idBudget;
        this.idAccessLevel = idAccessLevel;
    }

    public Budgets(Integer idBudget, int idAccessLevel, List<BudgetMonthBranch> budgetMonthBranchList) {
        this.idBudget = idBudget;
        this.idAccessLevel = idAccessLevel;
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    public Budgets(Integer idDistributor, Integer idArea, Integer idAccountingAccount, int idAccessLevel, int isentry, CDistributors distributor, CAreas area, AccountingAccounts accountingAccount, List<BudgetMonthBranch> budgetMonthBranchList) {
        this.idDistributor = idDistributor;
        this.idArea = idArea;
        this.idAccountingAccount = idAccountingAccount;
        this.idAccessLevel = idAccessLevel;
        this.isentry = isentry;
        this.distributor = distributor;
        this.area = area;
        this.accountingAccount = accountingAccount;
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<BudgetMonthBranch> getBudgetMonthBranchList() {
        return budgetMonthBranchList;
    }

    public void setBudgetMonthBranchList(List<BudgetMonthBranch> budgetMonthBranchList) {
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public AccountingAccounts getAccountingAccount() {
        return accountingAccount;
    }

    public void setAccountingAccount(AccountingAccounts accountingAccount) {
        this.accountingAccount = accountingAccount;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    public CAreas getArea() {
        return area;
    }

    public void setArea(CAreas area) {
        this.area = area;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudget != null ? idBudget.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Budgets)) {
            return false;
        }
        Budgets other = (Budgets) object;
        if ((this.idBudget == null && other.idBudget != null) || (this.idBudget != null && !this.idBudget.equals(other.idBudget))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Budgets[ idBudget=" + idBudget + " ]";
    }

    public int getIsentry() {
        return isentry;
    }

    public void setIsentry(int isentry) {
        this.isentry = isentry;
    }
    
}
