/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_REQUEST_STATUS")
public class CRequestStatus implements Serializable {

    public static final CRequestStatus PENDIENTE = new CRequestStatus(1);
    public static final CRequestStatus COTIZADA = new CRequestStatus(2);
    public static final CRequestStatus RECHAZADA = new CRequestStatus(3);
    public static final CRequestStatus APROBADA = new CRequestStatus(4);
    public static final CRequestStatus SIN_CONFIRMACION = new CRequestStatus(5);
    public static final CRequestStatus CONFIRMADA = new CRequestStatus(6);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "REQUEST_STATUS")
    @JsonView(JsonViews.Root.class)
    private String requestStatus;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestStatus != null ? idRequestStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CRequestStatus)) {
            return false;
        }
        CRequestStatus other = (CRequestStatus) object;
        if ((this.idRequestStatus == null && other.idRequestStatus != null) || (this.idRequestStatus != null && !this.idRequestStatus.equals(other.idRequestStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CRequestStatus[ idRequestStatus=" + idRequestStatus + " ]";
    }
    
}
