/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_SUBCATEGORIES")

public class CBudgetSubcategories implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final CBudgetSubcategories NACIONALES = new CBudgetSubcategories(214);
    public static final CBudgetSubcategories INTERNACIONALES = new CBudgetSubcategories(215);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BUDGET_SUBCATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BUDGET_SUBCATEGORY")
    @JsonView(JsonViews.Root.class)
    private String budgetSubcategory;

    @JsonIgnore
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Column(name = "USERNAME", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "SECOND_LEVEL", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String secondLevel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "BUDGET_SUBCATEGORY_PRODUCT",
            joinColumns = @JoinColumn(name = "ID_BUDGET_SUBCATEGORY"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRODUCT")
    )
    private Set<CProducts> products;

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

    public Set<CProducts> getProducts() {
        return products;
    }

    public void setProducts(Set<CProducts> products) {
        this.products = products;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
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
