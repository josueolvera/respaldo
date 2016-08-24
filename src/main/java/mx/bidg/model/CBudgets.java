package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGETS")
public class CBudgets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idCBudget;

//    @Size(max = 100)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    public CBudgets() {
    }

    public CBudgets(String name) {
        this.name = name;
    }

    public Integer getIdCBudget() {
        return idCBudget;
    }

    public void setIdCBudget(Integer idCBudget) {
        this.idCBudget = idCBudget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CBudgets cBudgets = (CBudgets) o;

        if (idCBudget != null ? !idCBudget.equals(cBudgets.idCBudget) : cBudgets.idCBudget != null) return false;
        return name != null ? name.equals(cBudgets.name) : cBudgets.name == null;

    }

    @Override
    public int hashCode() {
        int result = idCBudget != null ? idCBudget.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CBudgets{" +
                "idCBudget=" + idCBudget +
                ", name='" + name + '\'' +
                '}';
    }
}
