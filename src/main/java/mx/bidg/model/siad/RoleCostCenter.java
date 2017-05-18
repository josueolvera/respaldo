package mx.bidg.model.siad;

import javax.persistence.*;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "ROLE_COST_CENTER", schema = "BIDG_DBA", catalog = "")
public class RoleCostCenter {
    private int idRoleCostCenter;
    private Integer idCostCenter;
    private Integer idRole;

    @Id
    @Column(name = "ID_ROLE_COST_CENTER", nullable = false)
    public int getIdRoleCostCenter() {
        return idRoleCostCenter;
    }

    public void setIdRoleCostCenter(int idRoleCostCenter) {
        this.idRoleCostCenter = idRoleCostCenter;
    }

    @Basic
    @Column(name = "ID_COST_CENTER", nullable = true)
    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    @Basic
    @Column(name = "ID_ROLE", nullable = true)
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleCostCenter that = (RoleCostCenter) o;

        if (idRoleCostCenter != that.idRoleCostCenter) return false;
        if (idCostCenter != null ? !idCostCenter.equals(that.idCostCenter) : that.idCostCenter != null) return false;
        if (idRole != null ? !idRole.equals(that.idRole) : that.idRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRoleCostCenter;
        result = 31 * result + (idCostCenter != null ? idCostCenter.hashCode() : 0);
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
        return result;
    }
}
