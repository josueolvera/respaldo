package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "NOTIFICATIONS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Notifications implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICATION")
    @JsonView(JsonViews.Root.class)
    private Integer idNotification;

    @Size(max = 200)
    @Column(name = "TITLE")
    @JsonView(JsonViews.Root.class)
    private String title;

    @Size(max = 200)
    @Column(name = "SUBTITLE")
    @JsonView(JsonViews.Root.class)
    private String subtitle;

    @Size(max = 200)
    @Column(name = "TEXT")
    @JsonView(JsonViews.Root.class)
    private String text;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RESOURCE")
    @JsonView(JsonViews.Root.class)
    private int idResource;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DUE_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime dueDate;

    @Column(name = "ID_NOTIFICATION_STATUS", updatable = false, insertable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idNotificationStatus;

    @Column(name = "ID_NOTIFICATION_TYPE", updatable = false, insertable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idNotificationTypes;

    @Column(name = "ID_RESOURCE_TASK", updatable = false, insertable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idResourcesTasks;

    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @JoinColumn(name = "ID_NOTIFICATION_STATUS", referencedColumnName = "ID_NOTIFICATION_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CNotificationsStatus notificationsStatus;

    @JoinColumn(name = "ID_NOTIFICATION_TYPE", referencedColumnName = "ID_NOTIFICATION_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CNotificationTypes notificationTypes;

    @JoinColumn(name = "ID_RESOURCE_TASK", referencedColumnName = "ID_RESOURCE_TASK")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private ResourcesTasks resourcesTasks;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Users user;

    public Notifications() {}

    public Notifications(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public Notifications(Integer idNotification, int idResource, LocalDateTime creationDate, LocalDateTime dueDate) {
        this.idNotification = idNotification;
        this.idResource = idResource;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

    public DateFormatsPojo getCreationDate() {
        return new DateFormatsPojo(creationDate);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateFormatsPojo getDueDate() {
        return new DateFormatsPojo(dueDate);
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getIdNotificationStatus() {
        return idNotificationStatus;
    }

    public void setIdNotificationStatus(Integer idNotificationStatus) {
        this.idNotificationStatus = idNotificationStatus;
    }

    public Integer getIdNotificationTypes() {
        return idNotificationTypes;
    }

    public void setIdNotificationTypes(Integer idNotificationTypes) {
        this.idNotificationTypes = idNotificationTypes;
    }

    public Integer getIdResourcesTasks() {
        return idResourcesTasks;
    }

    public void setIdResourcesTasks(Integer idResourcesTasks) {
        this.idResourcesTasks = idResourcesTasks;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public CNotificationsStatus getNotificationsStatus() {
        return notificationsStatus;
    }

    public void setNotificationsStatus(CNotificationsStatus cNotificationsStatus) {
        this.notificationsStatus = cNotificationsStatus;
    }

    public CNotificationTypes getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(CNotificationTypes cNotificationTypes) {
        this.notificationTypes = cNotificationTypes;
    }

    public ResourcesTasks getResourcesTasks() {
        return resourcesTasks;
    }

    public void setResourcesTasks(ResourcesTasks resourcesTasks) {
        this.resourcesTasks = resourcesTasks;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotification != null ? idNotification.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notifications)) {
            return false;
        }
        Notifications other = (Notifications) object;
        if ((this.idNotification == null && other.idNotification != null) || (this.idNotification != null && !this.idNotification.equals(other.idNotification))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Notifications[ idNotification=" + idNotification + " ]";
    }
    
}
