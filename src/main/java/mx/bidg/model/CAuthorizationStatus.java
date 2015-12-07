/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "C_AUTHORIZATION_STATUS")
public class CAuthorizationStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUTHORIZATION_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idAuthorizationStatus;

    @Size(max = 100)
    @Column(name = "AUTHORIZATION_STATUS")
    @JsonView(JsonViews.Root.class)
    private String authorizationStatus;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    public CAuthorizationStatus() {
    }

    public CAuthorizationStatus(Integer idAuthorizationStatus) {
        this.idAuthorizationStatus = idAuthorizationStatus;
    }

    public Integer getIdAuthorizationStatus() {
        return idAuthorizationStatus;
    }

    public void setIdAuthorizationStatus(Integer idAuthorizationStatus) {
        this.idAuthorizationStatus = idAuthorizationStatus;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthorizationStatus != null ? idAuthorizationStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAuthorizationStatus)) {
            return false;
        }
        CAuthorizationStatus other = (CAuthorizationStatus) object;
        if ((this.idAuthorizationStatus == null && other.idAuthorizationStatus != null) || (this.idAuthorizationStatus != null && !this.idAuthorizationStatus.equals(other.idAuthorizationStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAuthorizationStatus[ idAuthorizationStatus=" + idAuthorizationStatus + " ]";
    }
    
}
