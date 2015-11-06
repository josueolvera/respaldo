/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "ACCESS_LEVEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a")})
public class AccessLevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACCESS_NAME")
    private String accessName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccessLevel")
    private List<AccessLevelsRole> accessLevelsRoleList;

    public AccessLevel() {
    }

    public AccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public AccessLevel(Integer idAccessLevel, String accessName, Date creationDate) {
        this.idAccessLevel = idAccessLevel;
        this.accessName = accessName;
        this.creationDate = creationDate;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<AccessLevelsRole> getAccessLevelsRoleList() {
        return accessLevelsRoleList;
    }

    public void setAccessLevelsRoleList(List<AccessLevelsRole> accessLevelsRoleList) {
        this.accessLevelsRoleList = accessLevelsRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccessLevel != null ? idAccessLevel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessLevel)) {
            return false;
        }
        AccessLevel other = (AccessLevel) object;
        if ((this.idAccessLevel == null && other.idAccessLevel != null) || (this.idAccessLevel != null && !this.idAccessLevel.equals(other.idAccessLevel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AccessLevel[ idAccessLevel=" + idAccessLevel + " ]";
    }
    
}
