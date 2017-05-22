/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_ESTIMATION_STATUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")

public class CEstimationStatus implements Serializable {

    public static final CEstimationStatus PENDIENTE = new CEstimationStatus(1);
    public static final CEstimationStatus APROBADA = new CEstimationStatus(2);
    public static final CEstimationStatus RECHAZADA = new CEstimationStatus(3);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTIMATION_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idEstimationStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ESTIMATION_STATUS_NAME")
    @JsonView(JsonViews.Root.class)
    private String estimationStatus;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String userName;

    @Basic
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public CEstimationStatus() {
    }

    public CEstimationStatus(Integer idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public CEstimationStatus(Integer idEstimationStatus, String estimationStatus, int idAccessLevel) {
        this.idEstimationStatus = idEstimationStatus;
        this.estimationStatus = estimationStatus;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(Integer idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public String getEstimationStatus() {
        return estimationStatus;
    }

    public void setEstimationStatus(String estimationStatus) {
        this.estimationStatus = estimationStatus;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CEstimationStatus)) return false;

        CEstimationStatus that = (CEstimationStatus) o;

        if (getIdAccessLevel() != that.getIdAccessLevel()) return false;
        if (!getIdEstimationStatus().equals(that.getIdEstimationStatus())) return false;
        if (getEstimationStatus() != null ? !getEstimationStatus().equals(that.getEstimationStatus()) : that.getEstimationStatus() != null)
            return false;
        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        return getCreationDate() != null ? getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdEstimationStatus().hashCode();
        result = 31 * result + (getEstimationStatus() != null ? getEstimationStatus().hashCode() : 0);
        result = 31 * result + getIdAccessLevel();
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CEstimationStatus[ idEstimationStatus=" + idEstimationStatus + " ]";
    }

}
