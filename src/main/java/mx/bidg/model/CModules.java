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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "C_MODULES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CModules.findAll", query = "SELECT c FROM CModules c")})
public class CModules implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MODULE")
    @JsonView(JsonViews.Root.class)
    private Integer idModule;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MODULE_NAME")
    @JsonView(JsonViews.Root.class)
    private String moduleName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @JoinColumn(name = "ID_SYSTEM", referencedColumnName = "ID_SYSTEM")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CSystems idSystem;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModule")
    @JsonView(JsonViews.Embedded.class)
    private List<CViews> cViewsList;

    public CModules() {
    }

    public CModules(Integer idModule) {
        this.idModule = idModule;
    }

    public CModules(Integer idModule, String moduleName, Date creationDate) {
        this.idModule = idModule;
        this.moduleName = moduleName;
        this.creationDate = creationDate;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CSystems getIdSystem() {
        return idSystem;
    }

    public void setIdSystem(CSystems idSystem) {
        this.idSystem = idSystem;
    }

    @XmlTransient
    public List<CViews> getCViewsList() {
        return cViewsList;
    }

    public void setCViewsList(List<CViews> cViewsList) {
        this.cViewsList = cViewsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModule != null ? idModule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CModules)) {
            return false;
        }
        CModules other = (CModules) object;
        if ((this.idModule == null && other.idModule != null) || (this.idModule != null && !this.idModule.equals(other.idModule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CModules[ idModule=" + idModule + " ]";
    }
    
}
