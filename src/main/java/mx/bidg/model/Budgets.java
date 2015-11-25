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

/**
 *
 * @author sistemask
 */
@Entity
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
    
    @JoinColumn(name = "ID_GROUP", referencedColumnName = "ID_GROUP")
    @ManyToOne
    @JsonView(JsonViews.Root.class)
    private CGroups idGroup;
    
    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView(JsonViews.Root.class)
    private CDistributors idDistributor;
    
    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    @ManyToOne
    @JsonView(JsonViews.Root.class)
    private CAreas idArea;
    
    @JoinColumn(name = "ID_BUDGET_CATEGORY", referencedColumnName = "ID_BUDGET_CATEGORY")
    @ManyToOne
    @JsonView(JsonViews.Root.class)
    private CBudgetCategories idBudgetCategory;
    
    @JoinColumn(name = "ID_BUDGET_SUBCATEGORY", referencedColumnName = "ID_BUDGET_SUBCATEGORY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Root.class)
    private CBudgetSubcategories idBudgetSubcategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private int year;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudget")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonth> budgetMonthList;
    
    @OneToMany(mappedBy = "idBudget")
    @JsonView(JsonViews.Embedded.class)
    private List<CRequestTypes> cRequestTypesList;

    public Budgets() {
    }

    public Budgets(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Budgets(Integer idBudget, int year, int idAccessLevel) {
        this.idBudget = idBudget;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
    }

    public Budgets(Integer idBudget, int year, int idAccessLevel, List<BudgetMonth> budgetMonthList) {
        this.idBudget = idBudget;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
        this.budgetMonthList = budgetMonthList;
    }

    public Budgets(Integer idBudget, CGroups idGroup, CDistributors idDistributor, CAreas idArea, CBudgetCategories idBudgetCategory, CBudgetSubcategories idBudgetSubcategory, int year, int idAccessLevel, List<BudgetMonth> budgetMonthList, List<CRequestTypes> cRequestTypesList) {
        this.idBudget = idBudget;
        this.idGroup = idGroup;
        this.idDistributor = idDistributor;
        this.idArea = idArea;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
        this.budgetMonthList = budgetMonthList;
        this.cRequestTypesList = cRequestTypesList;
    }

    public Budgets(Integer idBudget, CGroups idGroup, CDistributors idDistributor, CAreas idArea, CBudgetCategories idBudgetCategory, CBudgetSubcategories idBudgetSubcategory, int year, int idAccessLevel) {
        this.idBudget = idBudget;
        this.idGroup = idGroup;
        this.idDistributor = idDistributor;
        this.idArea = idArea;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<BudgetMonth> getBudgetMonthList() {
        return budgetMonthList;
    }

    public void setBudgetMonthList(List<BudgetMonth> budgetMonthList) {
        this.budgetMonthList = budgetMonthList;
    }
    
    public List<CRequestTypes> getCRequestTypesList() {
        return cRequestTypesList;
    }

    public void setCRequestTypesList(List<CRequestTypes> cRequestTypesList) {
        this.cRequestTypesList = cRequestTypesList;
    }

    public CGroups getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(CGroups idGroup) {
        this.idGroup = idGroup;
    }

    public CBudgetSubcategories getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(CBudgetSubcategories idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public CBudgetCategories getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(CBudgetCategories idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public CDistributors getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(CDistributors idDistributor) {
        this.idDistributor = idDistributor;
    }

    public CAreas getIdArea() {
        return idArea;
    }

    public void setIdArea(CAreas idArea) {
        this.idArea = idArea;
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
    
}
