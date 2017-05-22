/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@DynamicUpdate
@Table(name = "C_REQUEST_STATUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CRequestStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final CRequestStatus PENDIENTE = new CRequestStatus(1);
    public static final CRequestStatus COTIZADA = new CRequestStatus(2);
    public static final CRequestStatus RECHAZADA = new CRequestStatus(3);
    public static final CRequestStatus APROBADA = new CRequestStatus(4);
    public static final CRequestStatus SIN_CONFIRMACION = new CRequestStatus(5);
    public static final CRequestStatus CONFIRMADA = new CRequestStatus(6);


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "REQUEST_STATUS_NAME")
    @JsonView(JsonViews.Root.class)
    private String requestStatus;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    public CRequestStatus() {
    }

    public CRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    public CRequestStatus(Integer idRequestStatus, String requestStatus, int idAccessLevel) {
        this.idRequestStatus = idRequestStatus;
        this.requestStatus = requestStatus;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {

        this.idAccessLevel = idAccessLevel;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CRequestStatus)) return false;

        CRequestStatus that = (CRequestStatus) o;

        if (getIdAccessLevel() != that.getIdAccessLevel()) return false;
        if (getIdRequestStatus() != null ? !getIdRequestStatus().equals(that.getIdRequestStatus()) : that.getIdRequestStatus() != null)
            return false;
        if (getRequestStatus() != null ? !getRequestStatus().equals(that.getRequestStatus()) : that.getRequestStatus() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        return getCreationDate() != null ? getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdRequestStatus() != null ? getIdRequestStatus().hashCode() : 0;
        result = 31 * result + (getRequestStatus() != null ? getRequestStatus().hashCode() : 0);
        result = 31 * result + getIdAccessLevel();
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CRequestStatus[ idRequestStatus=" + idRequestStatus + " ]";
    }

}
