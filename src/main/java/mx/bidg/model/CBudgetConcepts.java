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
    @Size(min = 1, max = 50)
    @Column(name = "BUDGET_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private String budgetConcept;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Column(name = "ID_REQUEST_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategory;

    @JoinColumn(name = "ID_REQUEST_CATEGORY", referencedColumnName = "ID_REQUEST_CATEGORY")
    @ManyToOne
    @JsonView(JsonViews.EmbeddedRequestCategory.class)
    private CRequestsCategories requestsCategory;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetConcept")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetYearConcept> budgetMonthConceptsList;

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

    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    public CRequestsCategories getRequestsCategory() {
        return requestsCategory;
    }

    public void setRequestsCategory(CRequestsCategories requestsCategory) {
        this.requestsCategory = requestsCategory;
    }

    public List<BudgetYearConcept> getBudgetMonthConceptsList() {
        return budgetMonthConceptsList;
    }

    public void setBudgetMonthConceptsList(List<BudgetYearConcept> budgetMonthConceptsList) {
        this.budgetMonthConceptsList = budgetMonthConceptsList;
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
