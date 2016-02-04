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
import org.hibernate.envers.Audited;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGETS")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Budgets implements Serializable {
        
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;
    
    @Column(name = "ID_GROUP", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idGroup;
    
    @Column(name = "ID_AREA", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idArea;
    
    @Column(name = "ID_BUDGET_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetCategory;
    
    @Column(name = "ID_BUDGET_SUBCATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @JoinColumn(name = "ID_GROUP", referencedColumnName = "ID_GROUP")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CGroups group;
    
    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CAreas area;
    
    @JoinColumn(name = "ID_BUDGET_CATEGORY", referencedColumnName = "ID_BUDGET_CATEGORY")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBudgetCategories budgetCategory;
    
    @JoinColumn(name = "ID_BUDGET_SUBCATEGORY", referencedColumnName = "ID_BUDGET_SUBCATEGORY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class})
    private CBudgetSubcategories budgetSubcategory;
    
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

    public Budgets(Integer idBudget, Integer idGroup, Integer idArea, Integer idBudgetCategory, Integer idBudgetSubcategory, int idAccessLevel, List<BudgetMonthBranch> budgetMonthBranchList) {
        this.idBudget = idBudget;
        this.idGroup = idGroup;
        this.idArea = idArea;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.idAccessLevel = idAccessLevel;
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    public Budgets(Integer idBudget, Integer idGroup, Integer idArea, Integer idBudgetCategory, Integer idBudgetSubcategory, int idAccessLevel) {
        this.idBudget = idBudget;
        this.idGroup = idGroup;
        this.idArea = idArea;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.idAccessLevel = idAccessLevel;
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

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public CGroups getGroup() {
        return group;
    }

    public void setGroup(CGroups group) {
        this.group = group;
    }

    public CAreas getArea() {
        return area;
    }

    public void setArea(CAreas area) {
        this.area = area;
    }

    public CBudgetCategories getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(CBudgetCategories budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public CBudgetSubcategories getBudgetSubcategory() {
        return budgetSubcategory;
    }

    public void setBudgetSubcategory(CBudgetSubcategories budgetSubcategory) {
        this.budgetSubcategory = budgetSubcategory;
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
