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
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "VIEWS_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ViewsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VIEW_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idViewRole;

    @Column(name = "ID_VIEW", insertable = false, updatable = false)
    private Integer idView;

    @Column(name = "ID_SYSTEM_ROLE", insertable = false, updatable = false)
    private Integer idSystemRole;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_VIEW", referencedColumnName = "ID_VIEW")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CViews view;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles systemRole;

    public ViewsRole() {
    }

    public ViewsRole(Integer idViewRole) {
        this.idViewRole = idViewRole;
    }

    public ViewsRole(Integer idViewRole, LocalDateTime creationDate) {
        this.idViewRole = idViewRole;
        this.creationDate = creationDate;
    }

    public Integer getIdViewRole() {
        return idViewRole;
    }

    public void setIdViewRole(Integer idViewRole) {
        this.idViewRole = idViewRole;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

    public Integer getIdSystemRole() {
        return idSystemRole;
    }

    public void setIdSystemRole(Integer idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public CViews getView() {
        return view;
    }

    public void setView(CViews idView) {
        this.view = idView;
    }

    public SystemRoles getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRoles idSystemRole) {
        this.systemRole = idSystemRole;
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
