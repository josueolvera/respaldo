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
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_BUDGET_SUBCATEGORIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CBudgetSubcategories implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_SUBCATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "BUDGET_SUBCATEGORY")
    @JsonView(JsonViews.Root.class)
    private String budgetSubcategory;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetSubcategory")
    @JsonView(JsonViews.Embedded.class)
    private List<Budgets> budgetsList;

    public CBudgetSubcategories() {
    }

    public CBudgetSubcategories(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public CBudgetSubcategories(Integer idBudgetSubcategory, String budgetSubcategory, LocalDateTime creationDate, int idAccessLevel) {
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.budgetSubcategory = budgetSubcategory;
        this.creationDate = creationDate;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public String getBudgetSubcategory() {
        return budgetSubcategory;
    }

    public void setBudgetSubcategory(String budgetSubcategory) {
        this.budgetSubcategory = budgetSubcategory;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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
        hash += (idBudgetSubcategory != null ? idBudgetSubcategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetSubcategories)) {
            return false;
        }
        CBudgetSubcategories other = (CBudgetSubcategories) object;
        if ((this.idBudgetSubcategory == null && other.idBudgetSubcategory != null) || (this.idBudgetSubcategory != null && !this.idBudgetSubcategory.equals(other.idBudgetSubcategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetSubcategories[ idBudgetSubcategory=" + idBudgetSubcategory + " ]";
    }
    
}
