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
    @Basic(optional = false)
    @Column(name = "ID_VIEW_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idViewRole;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
