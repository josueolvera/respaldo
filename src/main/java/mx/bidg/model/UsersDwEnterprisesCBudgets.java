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
@Table(name = "USERS_DW_ENTERPRISES_C_BUDGETS")
public class UsersDwEnterprisesCBudgets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @JsonView(JsonViews.Root.class)
    private Integer id;

    @Column(name = "ID_DW_ENTERPRISE_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDwEnterpriseBudget;

    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @JoinColumn(name = "ID_DW_ENTERPRISE_BUDGET", referencedColumnName = "ID_DW_ENTERPRISE_BUDGET")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private DwEnterprisesCBudgets dwEnterpriseCBudget;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users user;

    public UsersDwEnterprisesCBudgets() {
    }

    public UsersDwEnterprisesCBudgets(DwEnterprisesCBudgets dwEnterpriseCBudget, Users user) {
        this.dwEnterpriseCBudget = dwEnterpriseCBudget;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersDwEnterprisesCBudgets that = (UsersDwEnterprisesCBudgets) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idDwEnterpriseBudget != null ? !idDwEnterpriseBudget.equals(that.idDwEnterpriseBudget) : that.idDwEnterpriseBudget != null)
            return false;
        return idUser != null ? idUser.equals(that.idUser) : that.idUser == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idDwEnterpriseBudget != null ? idDwEnterpriseBudget.hashCode() : 0);
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UsersDwEnterprisesCBudgets{" +
                "id=" + id +
                ", idDwEnterpriseBudget=" + idDwEnterpriseBudget +
                ", idUser=" + idUser +
                ", dwEnterpriseCBudget=" + dwEnterpriseCBudget +
                ", user=" + user +
                '}';
    }
}
