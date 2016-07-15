/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "ROLE_TRAVEL_TYPE")
public class RoleTravelType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ROLE_TRAVEL_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idRoleTravelType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_TRAVEL_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelType;

    @JoinColumn(name = "ID_TRAVEL_TYPE", referencedColumnName = "ID_TRAVEL_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelTypes travelType;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRoles role;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo creationDateFormats;

    public RoleTravelType() {
    }

    public RoleTravelType(Integer idRoleTravelType) {
        this.idRoleTravelType = idRoleTravelType;
    }

    public RoleTravelType(Integer idRoleTravelType, LocalDateTime creationDate) {
        this.idRoleTravelType = idRoleTravelType;
        this.creationDate = creationDate;
    }

    public Integer getIdRoleTravelType() {
        return idRoleTravelType;
    }

    public void setIdRoleTravelType(Integer idRoleTravelType) {
        this.idRoleTravelType = idRoleTravelType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdTravelType() {
        return idTravelType;
    }

    public void setIdTravelType(Integer idTravelType) {
        this.idTravelType = idTravelType;
    }

    public CTravelTypes getTravelType() {
        return travelType;
    }

    public void setTravelType(CTravelTypes travelType) {
        this.travelType = travelType;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public CRoles getRole() {
        return role;
    }

    public void setRole(CRoles role) {
        this.role = role;
    }

    public DateFormatsPojo getCreationDateFormats() {
        this.creationDateFormats = (creationDate == null) ? null : new DateFormatsPojo(creationDate);
        return this.creationDateFormats;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRoleTravelType != null ? idRoleTravelType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleTravelType)) {
            return false;
        }
        RoleTravelType other = (RoleTravelType) object;
        if ((this.idRoleTravelType == null && other.idRoleTravelType != null) || (this.idRoleTravelType != null && !this.idRoleTravelType.equals(other.idRoleTravelType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RoleTravelType[ idRoleTravelType=" + idRoleTravelType + " ]";
    }
    
}
