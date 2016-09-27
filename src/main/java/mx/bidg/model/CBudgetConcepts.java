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
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_CONCEPTS")
public class CBudgetConcepts implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BUDGET_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "BUDGET_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private String budgetConcept;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @ManyToMany(mappedBy = "budgetConceptList")
    @JsonView(JsonViews.Embedded.class)
    private Set<CRequestsCategories> requestsCategories;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetConcept")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetYearConcept> budgetYearConceptList;

    public CBudgetConcepts() {
    }

    public CBudgetConcepts(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public Integer getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public String getBudgetConcept() {
        return budgetConcept;
    }

    public void setBudgetConcept(String budgetConcept) {
        this.budgetConcept = budgetConcept;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Set<CRequestsCategories> getRequestsCategories() {
        return requestsCategories;
    }

    public void setRequestsCategories(Set<CRequestsCategories> requestsCategories) {
        this.requestsCategories = requestsCategories;
    }

    public List<BudgetYearConcept> getBudgetYearConceptList() {
        return budgetYearConceptList;
    }

    public void setBudgetYearConceptList(List<BudgetYearConcept> budgetYearConceptList) {
        this.budgetYearConceptList = budgetYearConceptList;
    }

    //No cambiar este metodo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CBudgetConcepts that = (CBudgetConcepts) o;

        return idBudgetConcept != null ? idBudgetConcept.equals(that.idBudgetConcept) : that.idBudgetConcept == null;

    }

    @Override
    public int hashCode() {
        return idBudgetConcept != null ? idBudgetConcept.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetConcepts[ idBudgetConcept=" + idBudgetConcept + " ]";
    }
    
}
