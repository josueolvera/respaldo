/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "ACCESS_LEVELS_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLevelsRole.findAll", query = "SELECT a FROM AccessLevelsRole a")})
public class AccessLevelsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCESS_LEVEL_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevelRole;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @JoinColumn(name = "ID_ACCESS_LEVEL", referencedColumnName = "ID_ACCESS_LEVEL")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AccessLevel idAccessLevel;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public AccessLevelsRole() {
    }

    public AccessLevelsRole(Integer idAccessLevelRole) {
        this.idAccessLevelRole = idAccessLevelRole;
    }

    public AccessLevelsRole(Integer idAccessLevelRole, Date creationDate) {
        this.idAccessLevelRole = idAccessLevelRole;
        this.creationDate = creationDate;
    }

    public Integer getIdAccessLevelRole() {
        return idAccessLevelRole;
    }

    public void setIdAccessLevelRole(Integer idAccessLevelRole) {
        this.idAccessLevelRole = idAccessLevelRole;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public AccessLevel getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(AccessLevel idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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
