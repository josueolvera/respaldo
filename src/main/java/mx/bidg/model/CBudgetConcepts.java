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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_BUDGET_CONCEPTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CBudgetConcepts implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUDGET_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private int budgetConcept;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetConcept")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthConcepts> budgetMonthConceptsList;

    public CBudgetConcepts() {
    }

    public CBudgetConcepts(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public CBudgetConcepts(Integer idBudgetConcept, int budgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
        this.budgetConcept = budgetConcept;
    }

    public Integer getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public int getBudgetConcept() {
        return budgetConcept;
    }

    public void setBudgetConcept(int budgetConcept) {
        this.budgetConcept = budgetConcept;
    }

    public List<BudgetMonthConcepts> getBudgetMonthConceptsList() {
        return budgetMonthConceptsList;
    }

    public void setBudgetMonthConceptsList(List<BudgetMonthConcepts> budgetMonthConceptsList) {
        this.budgetMonthConceptsList = budgetMonthConceptsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetConcept != null ? idBudgetConcept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetConcepts)) {
            return false;
        }
        CBudgetConcepts other = (CBudgetConcepts) object;
        if ((this.idBudgetConcept == null && other.idBudgetConcept != null) || (this.idBudgetConcept != null && !this.idBudgetConcept.equals(other.idBudgetConcept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetConcepts[ idBudgetConcept=" + idBudgetConcept + " ]";
    }
    
}
