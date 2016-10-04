package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by gerardo8 on 30/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_CHECK_STATUS")
public class CCheckStatus implements Serializable {

    public static final CCheckStatus PENDIENTE = new CCheckStatus(1);
    public static final CCheckStatus VENCIDA = new CCheckStatus(2);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHECK_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idCheckStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private String status;

    public CCheckStatus() {
    }

    public CCheckStatus(Integer idCheckStatus) {
        this.idCheckStatus = idCheckStatus;
    }

    public Integer getIdCheckStatus() {
        return idCheckStatus;
    }

    public void setIdCheckStatus(Integer idCheckStatus) {
        this.idCheckStatus = idCheckStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CCheckStatus that = (CCheckStatus) o;

        return idCheckStatus.equals(that.idCheckStatus);

    }

    @Override
    public int hashCode() {
        return idCheckStatus.hashCode();
    }

    @Override
    public String toString() {
        return "CCheckStatus{" +
                "idCheckStatus=" + idCheckStatus +
                ", status='" + status + '\'' +
                '}';
    }
}
