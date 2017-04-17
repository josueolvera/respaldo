package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Desarrollador on 05/04/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "C_COST_CENTER_STATUS")
public class CCostCenterStatus implements Serializable {

    public static final CCostCenterStatus INICIAL = new CCostCenterStatus(1);
    public static final CCostCenterStatus NO_AUTORIZADA = new CCostCenterStatus(2);
    public static final CCostCenterStatus RECHAZADA = new CCostCenterStatus(3);
    public static final CCostCenterStatus AUTORIZADA = new CCostCenterStatus(4);
    public static final CCostCenterStatus SOLICITUD_MODIFICACION = new CCostCenterStatus(5);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COST_CENTER_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenterStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "COST_CENTER_STATUS")
    @JsonView(JsonViews.Root.class)
    private String costCenterStatus;

    public CCostCenterStatus() {
    }

    public CCostCenterStatus(Integer idCostCenterStatus) {
        this.idCostCenterStatus = idCostCenterStatus;
    }

    public CCostCenterStatus(String costCenterStatus) {
        this.costCenterStatus = costCenterStatus;
    }

    public Integer getIdCostCenterStatus() {
        return idCostCenterStatus;
    }

    public void setIdCostCenterStatus(Integer idCostCenterStatus) {
        this.idCostCenterStatus = idCostCenterStatus;
    }

    public String getCostCenterStatus() {
        return costCenterStatus;
    }

    public void setCostCenterStatus(String costCenterStatus) {
        this.costCenterStatus = costCenterStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CCostCenterStatus)) return false;

        CCostCenterStatus that = (CCostCenterStatus) o;

        if (idCostCenterStatus != null ? !idCostCenterStatus.equals(that.idCostCenterStatus) : that.idCostCenterStatus != null)
            return false;
        return costCenterStatus != null ? costCenterStatus.equals(that.costCenterStatus) : that.costCenterStatus == null;

    }

    @Override
    public int hashCode() {
        int result = idCostCenterStatus != null ? idCostCenterStatus.hashCode() : 0;
        result = 31 * result + (costCenterStatus != null ? costCenterStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CCostCenterStatus{" +
                "idCostCenterStatus=" + idCostCenterStatus +
                ", costCenterStatus='" + costCenterStatus + '\'' +
                '}';
    }
}
