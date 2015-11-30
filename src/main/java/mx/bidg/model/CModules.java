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
@Table(name = "C_MODULES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
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
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_SYSTEM", referencedColumnName = "ID_SYSTEM")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CSystems idSystem;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    @JsonView(JsonViews.Embedded.class)
    private List<CViews> cViewsList;

    public CModules() {
    }

    public CModules(Integer idModule) {
        this.idModule = idModule;
    }

    public CModules(Integer idModule, String moduleName, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
