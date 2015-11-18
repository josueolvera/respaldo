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
import javax.validation.constraints.Size;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_BUDGET_TYPES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
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

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetType")
    @JsonView(JsonViews.Embedded.class)
    private List<Budgets> budgetsList;

    @JoinColumn(name = "ID_TABLE", referencedColumnName = "ID_TABLE")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(optional = false)
    private CTables idTable;

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

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<Budgets> getBudgetsList() {
        return budgetsList;
    }

    public void setBudgetsList(List<Budgets> budgetsList) {
        this.budgetsList = budgetsList;
    }

    public CTables getIdTable() {
        return idTable;
    }

    public void setIdTable(CTables idTable) {
        this.idTable = idTable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetType != null ? idBudgetType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetTypes)) {
            return false;
        }
        CBudgetTypes other = (CBudgetTypes) object;
        if ((this.idBudgetType == null && other.idBudgetType != null) || (this.idBudgetType != null && !this.idBudgetType.equals(other.idBudgetType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetTypes[ idBudgetType=" + idBudgetType + " ]";
    }
    
}
