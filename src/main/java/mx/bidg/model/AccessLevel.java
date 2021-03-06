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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "ACCESS_LEVEL")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AccessLevel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACCESS_NAME")
    @JsonView(JsonViews.Root.class)
    private String accessName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessLevel")
    @JsonView(JsonViews.Embedded.class)
    private List<AccessLevelsRole> accessLevelsRoleList;

    public AccessLevel() {
    }

    public AccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public AccessLevel(Integer idAccessLevel, String accessName, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
