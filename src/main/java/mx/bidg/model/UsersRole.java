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
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "USERS_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class UsersRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_USER_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idUserRole;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users user;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public UsersRole() {
    }

    public UsersRole(Integer idUserRole) {
        this.idUserRole = idUserRole;
    }

    public UsersRole(Integer idUserRole, LocalDateTime creationDate) {
        this.idUserRole = idUserRole;
        this.creationDate = creationDate;
    }

    public Integer getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(Integer idUserRole) {
        this.idUserRole = idUserRole;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
        hash += (idUserRole != null ? idUserRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersRole)) {
            return false;
        }
        UsersRole other = (UsersRole) object;
        if ((this.idUserRole == null && other.idUserRole != null) || (this.idUserRole != null && !this.idUserRole.equals(other.idUserRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.UsersRole[ idUserRole=" + idUserRole + " ]";
    }
    
}
