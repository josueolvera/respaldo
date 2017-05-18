package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "C_ESTIMATION_STATUS", schema = "BIDG_DBA", catalog = "")
public class CEstimationStatus {
    private int idEstimationStatus;
    private String estimationStatusName;
    private Integer idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Collection<PriceEstimations> priceEstimationsByIdEstimationStatus;

    @Id
    @Column(name = "ID_ESTIMATION_STATUS", nullable = false)
    public int getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(int idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    @Basic
    @Column(name = "ESTIMATION_STATUS_NAME", nullable = false, length = 100)
    public String getEstimationStatusName() {
        return estimationStatusName;
    }

    public void setEstimationStatusName(String estimationStatusName) {
        this.estimationStatusName = estimationStatusName;
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
    @Column(name = "CREATION_DATE", nullable = true)
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

        CEstimationStatus that = (CEstimationStatus) o;

        if (idEstimationStatus != that.idEstimationStatus) return false;
        if (estimationStatusName != null ? !estimationStatusName.equals(that.estimationStatusName) : that.estimationStatusName != null)
            return false;
        if (idAccessLevel != null ? !idAccessLevel.equals(that.idAccessLevel) : that.idAccessLevel != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEstimationStatus;
        result = 31 * result + (estimationStatusName != null ? estimationStatusName.hashCode() : 0);
        result = 31 * result + (idAccessLevel != null ? idAccessLevel.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cEstimationStatusByIdEstimationStatus")
    public Collection<PriceEstimations> getPriceEstimationsByIdEstimationStatus() {
        return priceEstimationsByIdEstimationStatus;
    }

    public void setPriceEstimationsByIdEstimationStatus(Collection<PriceEstimations> priceEstimationsByIdEstimationStatus) {
        this.priceEstimationsByIdEstimationStatus = priceEstimationsByIdEstimationStatus;
    }
}
