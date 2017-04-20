/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author SOPORTE
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_SUB_SUBCATEGORIES")
public class CBudgetSubSubcategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_BUDGET_SUB_SUBCATEGORY")
    private Integer idBudgetSubSubcategories;

    @Size(max = 100)
    @Column(name = "BUDGET_SUB_SUBCATEGORY")
    @JsonView(JsonViews.Root.class)
    private String budgetSubSubcategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Size(max = 30)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "THIRD_LEVEL", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String thirdLevel;

    public CBudgetSubSubcategories() {
    }

    public CBudgetSubSubcategories(Integer idBudgetSubSubcategories){
        this.idBudgetSubSubcategories = idBudgetSubSubcategories;
    }

    public CBudgetSubSubcategories(String budgetSubSubcategory, LocalDateTime creationDate) {
        this.budgetSubSubcategory = budgetSubSubcategory;
        this.creationDate = creationDate;
    }

    public Integer getIdBudgetSubSubcategories() {
        return idBudgetSubSubcategories;
    }

    public void setIdBudgetSubSubcategories(Integer idBudgetSubSubcategories) {
        this.idBudgetSubSubcategories = idBudgetSubSubcategories;
    }

    public String getBudgetSubSubcategory() {
        return budgetSubSubcategory;
    }

    public void setBudgetSubSubcategory(String budgetSubSubcategory) {
        this.budgetSubSubcategory = budgetSubSubcategory;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(String thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CBudgetSubSubcategories that = (CBudgetSubSubcategories) o;

        if (idBudgetSubSubcategories != null ? !idBudgetSubSubcategories.equals(that.idBudgetSubSubcategories) : that.idBudgetSubSubcategories != null)
            return false;
        if (budgetSubSubcategory != null ? !budgetSubSubcategory.equals(that.budgetSubSubcategory) : that.budgetSubSubcategory != null)
            return false;
        return thirdLevel != null ? thirdLevel.equals(that.thirdLevel) : that.thirdLevel == null;
    }

    @Override
    public int hashCode() {
        int result = idBudgetSubSubcategories != null ? idBudgetSubSubcategories.hashCode() : 0;
        result = 31 * result + (budgetSubSubcategory != null ? budgetSubSubcategory.hashCode() : 0);
        result = 31 * result + (thirdLevel != null ? thirdLevel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CBudgetSubSubcategories{" +
                "idBudgetSubSubcategories=" + idBudgetSubSubcategories +
                ", budgetSubSubcategory='" + budgetSubSubcategory + '\'' +
                ", creationDate=" + creationDate +
                ", username='" + username + '\'' +
                ", thirdLevel='" + thirdLevel + '\'' +
                '}';
    }
}
