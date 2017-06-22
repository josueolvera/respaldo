package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by jcesar on 12/06/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "REQUESTS_DATES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestsDates implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUESTS_DATES")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestsDates;

    @Basic
    @Column(name = "ID_REQUEST", insertable=false, updatable=false)
    @NotNull
    @JsonView(JsonViews.Root.class)
    private int idRequest;

    @Column(name = "PAYDAY_LIMIT")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime paydayLimit;

    @Column(name = "SCHEDULED_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime scheduledDate;

    @Basic(optional = true)
    @Column(name = "COUNT_UPDATE")
    @JsonView(JsonViews.Root.class)
    private Integer countUpdate;

    @Basic
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String userName;

    @Basic
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @OneToOne
    @JsonView(JsonViews.Embedded.class)
    private Requests requests;

    public RequestsDates() {
    }

    public Integer getIdRequestsDates() {
        return idRequestsDates;
    }

    public void setIdRequestsDates(Integer idRequestsDates) {
        this.idRequestsDates = idRequestsDates;
    }

    public int getIdRequests() {
        return idRequest;
    }

    public void setIdRequests(int idRequests) {
        this.idRequest = idRequests;
    }

    public LocalDateTime getPaydayLimit() {
        return paydayLimit;
    }

    public void setPaydayLimit(LocalDateTime paydayLimit) {
        this.paydayLimit = paydayLimit;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public DateFormatsPojo getPaydayLimitFormats() {
        if (paydayLimit == null) {
            return null;
        }
        return new DateFormatsPojo(paydayLimit);
    }

    public DateFormatsPojo getScheduledDateFormats() {
        if (scheduledDate == null) {
            return null;
        }
        return new DateFormatsPojo(scheduledDate);
    }

    public Integer getCountUpdate() {
        return countUpdate;
    }

    public void setCountUpdate(Integer countUpdate) {
        this.countUpdate = countUpdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestsDates)) return false;

        RequestsDates that = (RequestsDates) o;

        if (getIdRequests() != that.getIdRequests()) return false;
        if (getIdRequestsDates() != null ? !getIdRequestsDates().equals(that.getIdRequestsDates()) : that.getIdRequestsDates() != null)
            return false;
        if (getPaydayLimit() != null ? !getPaydayLimit().equals(that.getPaydayLimit()) : that.getPaydayLimit() != null)
            return false;
        if (getScheduledDate() != null ? !getScheduledDate().equals(that.getScheduledDate()) : that.getScheduledDate() != null)
            return false;
        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        if (getCreationDate() != null ? !getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() != null)
            return false;
        return getRequests() != null ? getRequests().equals(that.getRequests()) : that.getRequests() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdRequestsDates() != null ? getIdRequestsDates().hashCode() : 0;
        result = 31 * result + getIdRequests();
        result = 31 * result + (getPaydayLimit() != null ? getPaydayLimit().hashCode() : 0);
        result = 31 * result + (getScheduledDate() != null ? getScheduledDate().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getRequests() != null ? getRequests().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestsDates{" +
                "idRequestsDates=" + idRequestsDates +
                ", idRequest=" + idRequest +
                ", paydayLimit=" + paydayLimit +
                ", scheduiedDate=" + scheduledDate +
                ", userName='" + userName + '\'' +
                ", creationDate=" + creationDate +
                ", requests=" + requests +
                '}';
    }
}
