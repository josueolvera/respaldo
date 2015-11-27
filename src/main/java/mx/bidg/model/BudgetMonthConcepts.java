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
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
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
    
    @JoinColumn(name = "ID_BUDGET_MONTH", referencedColumnName = "ID_BUDGET_MONTH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private BudgetMonth idBudgetMonth;
    
    @JoinColumn(name = "ID_BUDGET_CONCEPT", referencedColumnName = "ID_BUDGET_CONCEPT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBudgetConcepts idBudgetConcept;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

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

    public BudgetMonth getIdBudgetMonth() {
        return idBudgetMonth;
    }

    public void setIdBudgetMonth(BudgetMonth idBudgetMonth) {
        this.idBudgetMonth = idBudgetMonth;
    }

    public CBudgetConcepts getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(CBudgetConcepts idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }
    
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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
