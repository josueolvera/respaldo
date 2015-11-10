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
@Table(name = "C_SYSTEMS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CSystems implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SYSTEM")
    @JsonView(JsonViews.Root.class)
    private Integer idSystem;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SYSTEM_NAME")
    @JsonView(JsonViews.Root.class)
    private String systemName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "DESCRIPTION_SYSTEM")
    @JsonView(JsonViews.Root.class)
    private String descriptionSystem;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystem")
    @JsonView(JsonViews.Embedded.class)
    private List<CModules> cModulesList;

    public CSystems() {
    }

    public CSystems(Integer idSystem) {
        this.idSystem = idSystem;
    }

    public CSystems(Integer idSystem, String systemName, String descriptionSystem, Date creationDate) {
        this.idSystem = idSystem;
        this.systemName = systemName;
        this.descriptionSystem = descriptionSystem;
        this.creationDate = creationDate;
    }

    public Integer getIdSystem() {
        return idSystem;
    }

    public void setIdSystem(Integer idSystem) {
        this.idSystem = idSystem;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDescriptionSystem() {
        return descriptionSystem;
    }

    public void setDescriptionSystem(String descriptionSystem) {
        this.descriptionSystem = descriptionSystem;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<CModules> getCModulesList() {
        return cModulesList;
    }

    public void setCModulesList(List<CModules> cModulesList) {
        this.cModulesList = cModulesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSystem != null ? idSystem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSystems)) {
            return false;
        }
        CSystems other = (CSystems) object;
        if ((this.idSystem == null && other.idSystem != null) || (this.idSystem != null && !this.idSystem.equals(other.idSystem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CSystems[ idSystem=" + idSystem + " ]";
    }
    
}
