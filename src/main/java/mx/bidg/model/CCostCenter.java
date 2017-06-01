package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by gerardo8 on 26/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_COST_CENTER")
public class CCostCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COST_CENTER")
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ACRONYM")
    @JsonView(JsonViews.Root.class)
    private String acronym;

    public CCostCenter() {
    }

    public CCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public CCostCenter(String name, String acronym) {
        this.name = name;
        this.acronym = acronym;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CCostCenter that = (CCostCenter) o;

        if (idCostCenter != null ? !idCostCenter.equals(that.idCostCenter) : that.idCostCenter != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return acronym != null ? acronym.equals(that.acronym) : that.acronym == null;

    }

    @Override
    public int hashCode() {
        int result = idCostCenter != null ? idCostCenter.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (acronym != null ? acronym.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CCostCenter{" +
                "idCostCenter=" + idCostCenter +
                ", name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                '}';
    }
}
