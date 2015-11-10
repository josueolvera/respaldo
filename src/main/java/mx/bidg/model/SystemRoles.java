/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "SYSTEM_ROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SystemRoles.findAll", query = "SELECT s FROM SystemRoles s")})
public class SystemRoles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SYSTEM_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idSystemRole;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SYSTEM_ROLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String systemRoleName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @Column(name = "PARENT_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer parentRole;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
//    @JsonView(JsonViews.Other.class)
    @JsonIgnore
    private List<ViewsRole> viewsRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
//    @JsonView(JsonViews.Other.class)
    @JsonIgnore
    private List<UsersRole> usersRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
//    @JsonView(JsonViews.Other.class)
    @JsonIgnore
    private List<TasksRole> tasksRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
//    @JsonView(JsonViews.Other.class)
    @JsonIgnore
    private List<AccessLevelsRole> accessLevelsRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
//    @JsonView(JsonViews.Other.class)
    @JsonIgnore
    private List<ViewsComponentsRole> viewsComponentsRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
//    @JsonView(JsonViews.Other.class)
    @JsonIgnore
    private List<TablesFieldsRole> tablesFieldsRoleList;

    public SystemRoles() {
    }

    public SystemRoles(Integer idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    public SystemRoles(Integer idSystemRole, String systemRoleName, Date creationDate) {
        this.idSystemRole = idSystemRole;
        this.systemRoleName = systemRoleName;
        this.creationDate = creationDate;
    }

    public Integer getIdSystemRole() {
        return idSystemRole;
    }

    public void setIdSystemRole(Integer idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    public String getSystemRoleName() {
        return systemRoleName;
    }

    public void setSystemRoleName(String systemRoleName) {
        this.systemRoleName = systemRoleName;
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

    @XmlTransient
    public List<UsersRole> getUsersRoleList() {
        return usersRoleList;
    }

    public void setUsersRoleList(List<UsersRole> usersRoleList) {
        this.usersRoleList = usersRoleList;
    }

    @XmlTransient
    public List<TasksRole> getTasksRoleList() {
        return tasksRoleList;
    }

    public void setTasksRoleList(List<TasksRole> tasksRoleList) {
        this.tasksRoleList = tasksRoleList;
    }

    @XmlTransient
    public List<AccessLevelsRole> getAccessLevelsRoleList() {
        return accessLevelsRoleList;
    }

    public void setAccessLevelsRoleList(List<AccessLevelsRole> accessLevelsRoleList) {
        this.accessLevelsRoleList = accessLevelsRoleList;
    }

    @XmlTransient
    public List<ViewsComponentsRole> getViewsComponentsRoleList() {
        return viewsComponentsRoleList;
    }

    public void setViewsComponentsRoleList(List<ViewsComponentsRole> viewsComponentsRoleList) {
        this.viewsComponentsRoleList = viewsComponentsRoleList;
    }

    @XmlTransient
    public List<TablesFieldsRole> getTablesFieldsRoleList() {
        return tablesFieldsRoleList;
    }

    public void setTablesFieldsRoleList(List<TablesFieldsRole> tablesFieldsRoleList) {
        this.tablesFieldsRoleList = tablesFieldsRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSystemRole != null ? idSystemRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemRoles)) {
            return false;
        }
        SystemRoles other = (SystemRoles) object;
        if ((this.idSystemRole == null && other.idSystemRole != null) || (this.idSystemRole != null && !this.idSystemRole.equals(other.idSystemRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.SystemRoles[ idSystemRole=" + idSystemRole + " ]";
    }

    public Integer getParentRole() {
        return parentRole;
    }

    public void setParentRole(Integer parentRole) {
        this.parentRole = parentRole;
    }
    
}
