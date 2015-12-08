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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "SYSTEM_ROLES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
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
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Column(name = "PARENT_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer parentRole;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemRole")
    @JsonView(JsonViews.Embedded.class)
    private List<ViewsRole> viewsRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
    @JsonView(JsonViews.Embedded.class)
    private List<UsersRole> usersRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
    @JsonView(JsonViews.Embedded.class)
    private List<TasksRole> tasksRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
    @JsonView(JsonViews.Embedded.class)
    private List<AccessLevelsRole> accessLevelsRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
    @JsonView(JsonViews.Embedded.class)
    private List<ViewsComponentsRole> viewsComponentsRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSystemRole")
    @JsonView(JsonViews.Embedded.class)
    private List<TablesFieldsRole> tablesFieldsRoleList;

    public SystemRoles() {
    }

    public SystemRoles(Integer idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    public SystemRoles(Integer idSystemRole, String systemRoleName, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<ViewsRole> getViewsRoleList() {
        return viewsRoleList;
    }

    public void setViewsRoleList(List<ViewsRole> viewsRoleList) {
        this.viewsRoleList = viewsRoleList;
    }

    public List<UsersRole> getUsersRoleList() {
        return usersRoleList;
    }

    public void setUsersRoleList(List<UsersRole> usersRoleList) {
        this.usersRoleList = usersRoleList;
    }

    public List<TasksRole> getTasksRoleList() {
        return tasksRoleList;
    }

    public void setTasksRoleList(List<TasksRole> tasksRoleList) {
        this.tasksRoleList = tasksRoleList;
    }

    public List<AccessLevelsRole> getAccessLevelsRoleList() {
        return accessLevelsRoleList;
    }

    public void setAccessLevelsRoleList(List<AccessLevelsRole> accessLevelsRoleList) {
        this.accessLevelsRoleList = accessLevelsRoleList;
    }

    public List<ViewsComponentsRole> getViewsComponentsRoleList() {
        return viewsComponentsRoleList;
    }

    public void setViewsComponentsRoleList(List<ViewsComponentsRole> viewsComponentsRoleList) {
        this.viewsComponentsRoleList = viewsComponentsRoleList;
    }

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
