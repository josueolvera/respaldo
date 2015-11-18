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
@Table(name = "C_BUDGET_AREAS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CBudgetAreas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_AREA")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetArea;

    @Size(max = 100)
    @Column(name = "BUDGET_AREA")
    @JsonView(JsonViews.Root.class)
    private String budgetArea;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetArea")
    @JsonView(JsonViews.Embedded.class)
    private List<Budgets> budgetsList;

    public CBudgetAreas() {
    }

    public CBudgetAreas(Integer idBudgetArea) {
        this.idBudgetArea = idBudgetArea;
    }

    public CBudgetAreas(Integer idBudgetArea, int idAccessLevel) {
        this.idBudgetArea = idBudgetArea;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudgetArea() {
        return idBudgetArea;
    }

    public void setIdBudgetArea(Integer idBudgetArea) {
        this.idBudgetArea = idBudgetArea;
    }

    public String getBudgetArea() {
        return budgetArea;
    }

    public void setBudgetArea(String budgetArea) {
        this.budgetArea = budgetArea;
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
        hash += (idBudgetArea != null ? idBudgetArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetAreas)) {
            return false;
        }
        CBudgetAreas other = (CBudgetAreas) object;
        if ((this.idBudgetArea == null && other.idBudgetArea != null) || (this.idBudgetArea != null && !this.idBudgetArea.equals(other.idBudgetArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetAreas[ idBudgetArea=" + idBudgetArea + " ]";
    }
    
}
