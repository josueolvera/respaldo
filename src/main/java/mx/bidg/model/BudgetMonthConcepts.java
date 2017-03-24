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
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_MONTH_CONCEPTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetMonthConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_MONTH_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthConcept;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Column(name = "ID_BUDGET_MONTH_BRANCH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthBranch;

//    @Column(name = "ID_BUDGET_CONCEPT", insertable = false, updatable = false)
//    @JsonView(JsonViews.Root.class)
//    private Integer idBudgetConcept;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

//    @JoinColumn(name = "ID_BUDGET_MONTH_BRANCH", referencedColumnName = "ID_BUDGET_MONTH_BRANCH")
//    @ManyToOne(optional = false)
//    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
//    private RealBudgetSpending budgetYearConcept;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;

    public BudgetMonthConcepts() {
    }

    public BudgetMonthConcepts(Integer idBudgetMonthConcept) {
        this.idBudgetMonthConcept = idBudgetMonthConcept;
    }

    public Integer getIdBudgetMonthConcept() {
        return idBudgetMonthConcept;
    }

    public void setIdBudgetMonthConcept(Integer idBudgetMonthConcept) {
        this.idBudgetMonthConcept = idBudgetMonthConcept;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudgetMonthBranch() {
        return idBudgetMonthBranch;
    }

    public void setIdBudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

//    public Integer getIdBudgetConcept() {
//        return idBudgetConcept;
//    }

//    public void setIdBudgetConcept(Integer idBudgetConcept) {
//        this.idBudgetConcept = idBudgetConcept;
//    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

//    public RealBudgetSpending getBudgetYearConcept() {
//        return budgetYearConcept;
//    }

//    public void setBudgetYearConcept(RealBudgetSpending budgetYearConcept) {
//        this.budgetYearConcept = budgetYearConcept;
//    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetMonthConcept != null ? idBudgetMonthConcept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetMonthConcepts)) {
            return false;
        }
        BudgetMonthConcepts other = (BudgetMonthConcepts) object;
        if ((this.idBudgetMonthConcept == null && other.idBudgetMonthConcept != null) || (this.idBudgetMonthConcept != null && !this.idBudgetMonthConcept.equals(other.idBudgetMonthConcept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetMonthConcepts[ idBudgetMonthConcept=" + idBudgetMonthConcept + " ]";
    }

}
