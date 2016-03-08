/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "ICON_CLASS")
    @JsonView(JsonViews.Root.class)
    private String iconClass;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @JsonProperty("modules")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "system")
    @JsonView(JsonViews.Embedded.class)
    private List<CModules> cModulesList;

    public CSystems() {
    }

    public CSystems(Integer idSystem) {
        this.idSystem = idSystem;
    }

    public CSystems(Integer idSystem, String systemName, String descriptionSystem, LocalDateTime creationDate) {
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

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty("modules")
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
