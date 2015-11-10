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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "VIEWS_COMPONENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ViewsComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VIEW_COMPONENT")
    @JsonView(JsonViews.Root.class)
    private Integer idViewComponent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idViewComponent")
    @JsonView(JsonViews.Embedded.class)
    private List<ViewsComponentsRole> viewsComponentsRoleList;

    @JoinColumn(name = "ID_VIEW", referencedColumnName = "ID_VIEW")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(optional = false)
    private CViews idView;

    @JoinColumn(name = "ID_COMPONENT", referencedColumnName = "ID_COMPONENT")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(optional = false)
    private CComponents idComponent;

    public ViewsComponent() {
    }

    public ViewsComponent(Integer idViewComponent) {
        this.idViewComponent = idViewComponent;
    }

    public ViewsComponent(Integer idViewComponent, Date creationDate) {
        this.idViewComponent = idViewComponent;
        this.creationDate = creationDate;
    }

    public Integer getIdViewComponent() {
        return idViewComponent;
    }

    public void setIdViewComponent(Integer idViewComponent) {
        this.idViewComponent = idViewComponent;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<ViewsComponentsRole> getViewsComponentsRoleList() {
        return viewsComponentsRoleList;
    }

    public void setViewsComponentsRoleList(List<ViewsComponentsRole> viewsComponentsRoleList) {
        this.viewsComponentsRoleList = viewsComponentsRoleList;
    }

    public CViews getIdView() {
        return idView;
    }

    public void setIdView(CViews idView) {
        this.idView = idView;
    }

    public CComponents getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(CComponents idComponent) {
        this.idComponent = idComponent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idViewComponent != null ? idViewComponent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewsComponent)) {
            return false;
        }
        ViewsComponent other = (ViewsComponent) object;
        if ((this.idViewComponent == null && other.idViewComponent != null) || (this.idViewComponent != null && !this.idViewComponent.equals(other.idViewComponent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ViewsComponent[ idViewComponent=" + idViewComponent + " ]";
    }
    
}
