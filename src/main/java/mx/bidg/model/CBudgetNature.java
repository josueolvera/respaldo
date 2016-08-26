package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by gerardo8 on 26/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_NATURE")
public class CBudgetNature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_NATURE")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetNature;

    @Size(max = 100)
    @Column(name = "BUDGET_NATURE")
    @JsonView(JsonViews.Root.class)
    private String budgetNature;

    public CBudgetNature() {
    }

    public CBudgetNature(Integer idBudgetNature) {
        this.idBudgetNature = idBudgetNature;
    }

    public CBudgetNature(String budgetNature) {
        this.budgetNature = budgetNature;
    }

    public Integer getIdBudgetNature() {
        return idBudgetNature;
    }

    public void setIdBudgetNature(Integer idBudgetNature) {
        this.idBudgetNature = idBudgetNature;
    }

    public String getBudgetNature() {
        return budgetNature;
    }

    public void setBudgetNature(String budgetNature) {
        this.budgetNature = budgetNature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CBudgetNature that = (CBudgetNature) o;

        if (idBudgetNature != null ? !idBudgetNature.equals(that.idBudgetNature) : that.idBudgetNature != null)
            return false;
        return budgetNature != null ? budgetNature.equals(that.budgetNature) : that.budgetNature == null;

    }

    @Override
    public int hashCode() {
        int result = idBudgetNature != null ? idBudgetNature.hashCode() : 0;
        result = 31 * result + (budgetNature != null ? budgetNature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CBudgetNature{" +
                "idBudgetNature=" + idBudgetNature +
                ", budgetNature='" + budgetNature + '\'' +
                '}';
    }
}
