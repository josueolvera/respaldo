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
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "BUDGET_MONTH_BRANCH")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetMonthBranch implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_MONTH_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthBranch;
    
    @JoinColumn(name = "ID_BUDGET_MONTH", referencedColumnName = "ID_BUDGET_MONTH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Root.class)
    private BudgetMonth idBudgetMonth;
    
    @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Root.class)
    private CBranchs idBranch;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetMonthBranch")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthConcepts> budgetMonthConceptsList;

    public BudgetMonthBranch() {
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public Integer getIdBudgetMonthBranch() {
        return idBudgetMonthBranch;
    }

    public void setIdBudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public List<BudgetMonthConcepts> getBudgetMonthConceptsList() {
        return budgetMonthConceptsList;
    }

    public void setBudgetMonthConceptsList(List<BudgetMonthConcepts> budgetMonthConceptsList) {
        this.budgetMonthConceptsList = budgetMonthConceptsList;
    }

    public BudgetMonth getIdBudgetMonth() {
        return idBudgetMonth;
    }

    public void setIdBudgetMonth(BudgetMonth idBudgetMonth) {
        this.idBudgetMonth = idBudgetMonth;
    }

    public CBranchs getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(CBranchs idBranch) {
        this.idBranch = idBranch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetMonthBranch != null ? idBudgetMonthBranch.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetMonthBranch)) {
            return false;
        }
        BudgetMonthBranch other = (BudgetMonthBranch) object;
        if ((this.idBudgetMonthBranch == null && other.idBudgetMonthBranch != null) || (this.idBudgetMonthBranch != null && !this.idBudgetMonthBranch.equals(other.idBudgetMonthBranch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetMonthBranch[ idBudgetMonthBranch=" + idBudgetMonthBranch + " ]";
    }
    
}
