package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_NOTIFICATIONS_STATUS")
public class CNotificationsStatus implements Serializable {

    public static final int PENDIENTE = 1;
    public static final int LEIDA = 2;
    public static final int ARCHIVADA = 3;
    public static final int POSPUESTA = 4;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICATION_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idNotificationStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private String status;

    public CNotificationsStatus() {
    }

    public CNotificationsStatus(Integer idNotificationStatus) {
        this.idNotificationStatus = idNotificationStatus;
    }

    public CNotificationsStatus(Integer idNotificationStatus, String status) {
        this.idNotificationStatus = idNotificationStatus;
        this.status = status;
    }

    public Integer getIdNotificationStatus() {
        return idNotificationStatus;
    }

    public void setIdNotificationStatus(Integer idNotificationStatus) {
        this.idNotificationStatus = idNotificationStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificationStatus != null ? idNotificationStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CNotificationsStatus)) {
            return false;
        }
        CNotificationsStatus other = (CNotificationsStatus) object;
        if ((this.idNotificationStatus == null && other.idNotificationStatus != null) || (this.idNotificationStatus != null && !this.idNotificationStatus.equals(other.idNotificationStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CNotificationsStatus[ idNotificationStatus=" + idNotificationStatus + " ]";
    }
    
}
