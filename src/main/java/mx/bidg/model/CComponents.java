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
@Table(name = "C_COMPONENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CComponents.findAll", query = "SELECT c FROM CComponents c")})
public class CComponents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMPONENT")
    private Integer idComponent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "COMPONENT_NAME")
    private String componentName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idComponent")
    private List<ViewsComponent> viewsComponentList;

    public CComponents() {
    }

    public CComponents(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public CComponents(Integer idComponent, String componentName, Date creationDate) {
        this.idComponent = idComponent;
        this.componentName = componentName;
        this.creationDate = creationDate;
    }

    public Integer getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<ViewsComponent> getViewsComponentList() {
        return viewsComponentList;
    }

    public void setViewsComponentList(List<ViewsComponent> viewsComponentList) {
        this.viewsComponentList = viewsComponentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponent != null ? idComponent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CComponents)) {
            return false;
        }
        CComponents other = (CComponents) object;
        if ((this.idComponent == null && other.idComponent != null) || (this.idComponent != null && !this.idComponent.equals(other.idComponent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CComponents[ idComponent=" + idComponent + " ]";
    }
    
}
