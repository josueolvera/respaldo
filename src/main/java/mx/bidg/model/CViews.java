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
@Table(name = "C_VIEWS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CViews.findAll", query = "SELECT c FROM CViews c")})
public class CViews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VIEW")
    private Integer idView;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "VIEW_NAME")
    private String viewName;
    @Size(max = 100)
    @Column(name = "VIEW")
    private String view;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idView")
    private List<ViewsRole> viewsRoleList;
    @JoinColumn(name = "ID_MODULE", referencedColumnName = "ID_MODULE")
    @ManyToOne(optional = false)
    private CModules idModule;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idView")
    private List<ViewsComponent> viewsComponentList;

    public CViews() {
    }

    public CViews(Integer idView) {
        this.idView = idView;
    }

    public CViews(Integer idView, String viewName, Date creationDate) {
        this.idView = idView;
        this.viewName = viewName;
        this.creationDate = creationDate;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<ViewsRole> getViewsRoleList() {
        return viewsRoleList;
    }

    public void setViewsRoleList(List<ViewsRole> viewsRoleList) {
        this.viewsRoleList = viewsRoleList;
    }

    public CModules getIdModule() {
        return idModule;
    }

    public void setIdModule(CModules idModule) {
        this.idModule = idModule;
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
        hash += (idView != null ? idView.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CViews)) {
            return false;
        }
        CViews other = (CViews) object;
        if ((this.idView == null && other.idView != null) || (this.idView != null && !this.idView.equals(other.idView))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CViews[ idView=" + idView + " ]";
    }
    
}
