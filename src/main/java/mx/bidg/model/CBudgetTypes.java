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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_TYPES")
public class CBudgetTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetType;

    @Size(max = 100)
    @Column(name = "BUDGET_TYPE")
    @JsonView(JsonViews.Root.class)
    private String budgetType;

    public CBudgetTypes() {
    }

    public CBudgetTypes(Integer idBudgetType) {
        this.idBudgetType = idBudgetType;
    }

    public Integer getIdBudgetType() {
        return idBudgetType;
    }

    public void setIdBudgetType(Integer idBudgetType) {
        this.idBudgetType = idBudgetType;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CBudgetTypes that = (CBudgetTypes) o;

        if (idBudgetType != null ? !idBudgetType.equals(that.idBudgetType) : that.idBudgetType != null) return false;
        return budgetType != null ? budgetType.equals(that.budgetType) : that.budgetType == null;

    }

    @Override
    public int hashCode() {
        int result = idBudgetType != null ? idBudgetType.hashCode() : 0;
        result = 31 * result + (budgetType != null ? budgetType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CBudgetTypes{" +
                "idBudgetType=" + idBudgetType +
                ", budgetType='" + budgetType + '\'' +
                '}';
    }
}
