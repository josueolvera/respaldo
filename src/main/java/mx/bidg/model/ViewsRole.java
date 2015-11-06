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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "VIEWS_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewsRole.findAll", query = "SELECT v FROM ViewsRole v")})
public class ViewsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VIEW_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idViewRole;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @JoinColumn(name = "ID_VIEW", referencedColumnName = "ID_VIEW")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CViews idView;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public ViewsRole() {
    }

    public ViewsRole(Integer idViewRole) {
        this.idViewRole = idViewRole;
    }

    public ViewsRole(Integer idViewRole, Date creationDate) {
        this.idViewRole = idViewRole;
        this.creationDate = creationDate;
    }

    public Integer getIdViewRole() {
        return idViewRole;
    }

    public void setIdViewRole(Integer idViewRole) {
        this.idViewRole = idViewRole;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CViews getIdView() {
        return idView;
    }

    public void setIdView(CViews idView) {
        this.idView = idView;
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
        hash += (idViewRole != null ? idViewRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewsRole)) {
            return false;
        }
        ViewsRole other = (ViewsRole) object;
        if ((this.idViewRole == null && other.idViewRole != null) || (this.idViewRole != null && !this.idViewRole.equals(other.idViewRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ViewsRole[ idViewRole=" + idViewRole + " ]";
    }
    
}
