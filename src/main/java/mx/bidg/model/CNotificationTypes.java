package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_NOTIFICATION_TYPES")
public class CNotificationTypes implements Serializable {

    public static final int S = 1;
    public static final int T = 2;
    public static final int I = 3;
    public static final int C = 4;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICATION_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idNotificationType;

    @Column(name = "NOTIFICATION_SYMBOL")
    @JsonView(JsonViews.Root.class)
    private String notificationSymbol;

    @Size(max = 12)
    @Column(name = "BACKGROUND")
    @JsonView(JsonViews.Root.class)
    private String background;

    @Size(max = 12)
    @Column(name = "FOREGROUND")
    @JsonView(JsonViews.Root.class)
    private String foreground;

    @Lob
    @Column(name = "DETAILS_TEMPLATE")
    @JsonView(JsonViews.Root.class)
    private String detailsTemplate;

    public CNotificationTypes() {
    }

    public CNotificationTypes(Integer idNotificationType) {
        this.idNotificationType = idNotificationType;
    }

    public Integer getIdNotificationType() {
        return idNotificationType;
    }

    public void setIdNotificationType(Integer idNotificationType) {
        this.idNotificationType = idNotificationType;
    }

    public String getNotificationSymbol() {
        return notificationSymbol;
    }

    public void setNotificationSymbol(String notificationSymbol) {
        this.notificationSymbol = notificationSymbol;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getForeground() {
        return foreground;
    }

    public void setForeground(String foreground) {
        this.foreground = foreground;
    }

    public String getDetailsTemplate() {
        return detailsTemplate;
    }

    public void setDetailsTemplate(String detailsTemplate) {
        this.detailsTemplate = detailsTemplate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificationType != null ? idNotificationType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CNotificationTypes)) {
            return false;
        }
        CNotificationTypes other = (CNotificationTypes) object;
        if ((this.idNotificationType == null && other.idNotificationType != null) || (this.idNotificationType != null && !this.idNotificationType.equals(other.idNotificationType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CNotificationTypes[ idNotificationType=" + idNotificationType + " ]";
    }
    
}
