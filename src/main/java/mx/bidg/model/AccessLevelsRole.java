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
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "ACCESS_LEVELS_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AccessLevelsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCESS_LEVEL_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevelRole;

    @Column(name = "ID_ACCESS_LEVEL", insertable = false, updatable = false)
    private Integer idAccessLevel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_ACCESS_LEVEL", referencedColumnName = "ID_ACCESS_LEVEL")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AccessLevel accessLevel;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public AccessLevelsRole() {
    }

    public AccessLevelsRole(Integer idAccessLevelRole) {
        this.idAccessLevelRole = idAccessLevelRole;
    }

    public AccessLevelsRole(Integer idAccessLevelRole, LocalDateTime creationDate) {
        this.idAccessLevelRole = idAccessLevelRole;
        this.creationDate = creationDate;
    }

    public Integer getIdAccessLevelRole() {
        return idAccessLevelRole;
    }

    public void setIdAccessLevelRole(Integer idAccessLevelRole) {
        this.idAccessLevelRole = idAccessLevelRole;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel idAccessLevel) {
        this.accessLevel = idAccessLevel;
    }

    public SystemRoles getIdSystemRole() {
        return idSystemRole;
    }

    public void setIdSystemRole(SystemRoles idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccessLevelRole != null ? idAccessLevelRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessLevelsRole)) {
            return false;
        }
        AccessLevelsRole other = (AccessLevelsRole) object;
        if ((this.idAccessLevelRole == null && other.idAccessLevelRole != null) || (this.idAccessLevelRole != null && !this.idAccessLevelRole.equals(other.idAccessLevelRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AccessLevelsRole[ idAccessLevelRole=" + idAccessLevelRole + " ]";
    }
    
}
