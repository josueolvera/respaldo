package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "DW_ENTERPRISES_C_BUDGETS")
public class DwEnterprisesCBudgets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DW_ENTERPRISE_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idDwEnterpriseBudget;

    @Column(name = "ID_DW_ENTERPRISE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDwEnterprise;

    @Column(name = "ID_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCBudget;

    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private DwEnterprises dwEnterprise;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBudgets cBudget;

    public DwEnterprisesCBudgets() {
    }

    public DwEnterprisesCBudgets(Integer idDwEnterpriseBudget) {
        this.idDwEnterpriseBudget = idDwEnterpriseBudget;
    }

    public DwEnterprisesCBudgets(DwEnterprises dwEnterprise, CBudgets cBudget) {
        this.dwEnterprise = dwEnterprise;
        this.cBudget = cBudget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DwEnterprisesCBudgets that = (DwEnterprisesCBudgets) o;

        if (idDwEnterprise != null ? !idDwEnterprise.equals(that.idDwEnterprise) : that.idDwEnterprise != null)
            return false;
        return idCBudget != null ? idCBudget.equals(that.idCBudget) : that.idCBudget == null;

    }

    @Override
    public int hashCode() {
        int result = idDwEnterprise != null ? idDwEnterprise.hashCode() : 0;
        result = 31 * result + (idCBudget != null ? idCBudget.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DwEnterprisesCBudgets{" +
                "idDwEnterpriseBudget=" + idDwEnterpriseBudget +
                ", idDwEnterprise=" + idDwEnterprise +
                ", idCBudget=" + idCBudget +
                ", dwEnterprise=" + dwEnterprise +
                ", cBudget=" + cBudget +
                '}';
    }
}
