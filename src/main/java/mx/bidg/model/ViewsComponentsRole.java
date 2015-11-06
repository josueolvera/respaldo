/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
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

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "VIEWS_COMPONENTS_ROLE")
public class ViewsComponentsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VIEW_COMPONENT_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idViewComponentRole;

    @JoinColumn(name = "ID_VIEW_COMPONENT", referencedColumnName = "ID_VIEW_COMPONENT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private ViewsComponent idViewComponent;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public ViewsComponentsRole() {
    }

    public ViewsComponentsRole(Integer idViewComponentRole) {
        this.idViewComponentRole = idViewComponentRole;
    }

    public Integer getIdViewComponentRole() {
        return idViewComponentRole;
    }

    public void setIdViewComponentRole(Integer idViewComponentRole) {
        this.idViewComponentRole = idViewComponentRole;
    }

    public ViewsComponent getIdViewComponent() {
        return idViewComponent;
    }

    public void setIdViewComponent(ViewsComponent idViewComponent) {
        this.idViewComponent = idViewComponent;
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
        hash += (idViewComponentRole != null ? idViewComponentRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewsComponentsRole)) {
            return false;
        }
        ViewsComponentsRole other = (ViewsComponentsRole) object;
        if ((this.idViewComponentRole == null && other.idViewComponentRole != null) || (this.idViewComponentRole != null && !this.idViewComponentRole.equals(other.idViewComponentRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ViewsComponentsRole[ idViewComponentRole=" + idViewComponentRole + " ]";
    }
    
}
