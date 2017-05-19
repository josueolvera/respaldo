package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "C_MODULE_STATUS", schema = "BIDG_DBA", catalog = "")
public class CModuleStatus {
    private int idModuleStatus;
    private String moduleStatusName;
    private Integer idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Collection<DistributorCostCenter> distributorCostCentersByIdModuleStatus;

    @Id
    @Column(name = "ID_MODULE_STATUS", nullable = false)
    public int getIdModuleStatus() {
        return idModuleStatus;
    }

    public void setIdModuleStatus(int idModuleStatus) {
        this.idModuleStatus = idModuleStatus;
    }

    @Basic
    @Column(name = "MODULE_STATUS_NAME", nullable = true, length = 100)
    public String getModuleStatusName() {
        return moduleStatusName;
    }

    public void setModuleStatusName(String moduleStatusName) {
        this.moduleStatusName = moduleStatusName;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = true)
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "CREATION_DATE", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CModuleStatus that = (CModuleStatus) o;

        if (idModuleStatus != that.idModuleStatus) return false;
        if (moduleStatusName != null ? !moduleStatusName.equals(that.moduleStatusName) : that.moduleStatusName != null)
            return false;
        if (idAccessLevel != null ? !idAccessLevel.equals(that.idAccessLevel) : that.idAccessLevel != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idModuleStatus;
        result = 31 * result + (moduleStatusName != null ? moduleStatusName.hashCode() : 0);
        result = 31 * result + (idAccessLevel != null ? idAccessLevel.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cModuleStatusByIdModuleStatus")
    public Collection<DistributorCostCenter> getDistributorCostCentersByIdModuleStatus() {
        return distributorCostCentersByIdModuleStatus;
    }

    public void setDistributorCostCentersByIdModuleStatus(Collection<DistributorCostCenter> distributorCostCentersByIdModuleStatus) {
        this.distributorCostCentersByIdModuleStatus = distributorCostCentersByIdModuleStatus;
    }
}
