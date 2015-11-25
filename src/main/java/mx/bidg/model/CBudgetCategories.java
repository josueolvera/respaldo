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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_BUDGET_CATEGORIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CBudgetCategories implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetCategory;
    
    @Size(max = 100)
    @Column(name = "BUDGET_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private String budgetCategory;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @OneToMany(mappedBy = "idBudgetCategory")
    @JsonView(JsonViews.Embedded.class)
    private List<Budgets> budgetsList;

    public CBudgetCategories() {
    }

    public CBudgetCategories(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public CBudgetCategories(Integer idBudgetCategory, int idAccessLevel) {
        this.idBudgetCategory = idBudgetCategory;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public String getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(String budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<Budgets> getBudgetsList() {
        return budgetsList;
    }

    public void setBudgetsList(List<Budgets> budgetsList) {
        this.budgetsList = budgetsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetCategory != null ? idBudgetCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetCategories)) {
            return false;
        }
        CBudgetCategories other = (CBudgetCategories) object;
        if ((this.idBudgetCategory == null && other.idBudgetCategory != null) || (this.idBudgetCategory != null && !this.idBudgetCategory.equals(other.idBudgetCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetCategories[ idBudgetCategory=" + idBudgetCategory + " ]";
    }
    
}
