package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "DISTRIBUTOR_COST_CENTER", schema = "BIDG_DBA", catalog = "")
public class DistributorCostCenter {
    private int idDistributorCostCenter;
    private int idBusinessLine;
    private int idDistributor;
    private int idCostCenter;
    private int idAccountingAccount;
    private Integer idModuleStatus;
    private String username;
    private Timestamp creationDate;
    private CModuleStatus cModuleStatusByIdModuleStatus;
    private Collection<RoleProductRequest> roleProductRequestsByIdDistributorCostCenter;

    @Id
    @Column(name = "ID_DISTRIBUTOR_COST_CENTER", nullable = false)
    public int getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(int idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    @Basic
    @Column(name = "ID_BUSINESS_LINE", nullable = false)
    public int getIdBusinessLine() {
        return idBusinessLine;
    }

    public void setIdBusinessLine(int idBusinessLine) {
        this.idBusinessLine = idBusinessLine;
    }

    @Basic
    @Column(name = "ID_DISTRIBUTOR", nullable = false)
    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
        this.idDistributor = idDistributor;
    }

    @Basic
    @Column(name = "ID_COST_CENTER", nullable = false)
    public int getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(int idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    @Basic
    @Column(name = "ID_ACCOUNTING_ACCOUNT", nullable = false)
    public int getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(int idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    @Basic
    @Column(name = "ID_MODULE_STATUS", nullable = true)
    public Integer getIdModuleStatus() {
        return idModuleStatus;
    }

    public void setIdModuleStatus(Integer idModuleStatus) {
        this.idModuleStatus = idModuleStatus;
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

        DistributorCostCenter that = (DistributorCostCenter) o;

        if (idDistributorCostCenter != that.idDistributorCostCenter) return false;
        if (idBusinessLine != that.idBusinessLine) return false;
        if (idDistributor != that.idDistributor) return false;
        if (idCostCenter != that.idCostCenter) return false;
        if (idAccountingAccount != that.idAccountingAccount) return false;
        if (idModuleStatus != null ? !idModuleStatus.equals(that.idModuleStatus) : that.idModuleStatus != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDistributorCostCenter;
        result = 31 * result + idBusinessLine;
        result = 31 * result + idDistributor;
        result = 31 * result + idCostCenter;
        result = 31 * result + idAccountingAccount;
        result = 31 * result + (idModuleStatus != null ? idModuleStatus.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_MODULE_STATUS", referencedColumnName = "ID_MODULE_STATUS")
    public CModuleStatus getcModuleStatusByIdModuleStatus() {
        return cModuleStatusByIdModuleStatus;
    }

    public void setcModuleStatusByIdModuleStatus(CModuleStatus cModuleStatusByIdModuleStatus) {
        this.cModuleStatusByIdModuleStatus = cModuleStatusByIdModuleStatus;
    }

    @OneToMany(mappedBy = "distributorCostCenterByIdDistributorCostCenter")
    public Collection<RoleProductRequest> getRoleProductRequestsByIdDistributorCostCenter() {
        return roleProductRequestsByIdDistributorCostCenter;
    }

    public void setRoleProductRequestsByIdDistributorCostCenter(Collection<RoleProductRequest> roleProductRequestsByIdDistributorCostCenter) {
        this.roleProductRequestsByIdDistributorCostCenter = roleProductRequestsByIdDistributorCostCenter;
    }
}
