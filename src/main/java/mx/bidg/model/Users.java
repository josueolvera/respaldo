/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.TimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "USERS")
@DynamicUpdate
@SelectBeforeUpdate
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_USER")
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "PASSWORD")
    @JsonView(JsonViews.Root.class)
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAIL")
    @JsonView(JsonViews.Root.class)
    private String mail;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVE_SESSION")
    @JsonView(JsonViews.Root.class)
    private int activeSession;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_SESSION")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = TimeConverter.class)
    private LocalTime timeSession;

    @Basic(optional = false)
    @NotNull
    @Column(name = "HIGH_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime highDate;

    @Column(name = "MODIFICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime modificationDate;

    @Column(name = "LOW_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime lowDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPANY")
    @JsonView(JsonViews.Embedded.class)
    private int idCompany;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REGION")
    @JsonView(JsonViews.Embedded.class)
    private int idRegion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Embedded.class)
    private int idBranch;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GROUP")
    @JsonView(JsonViews.Embedded.class)
    private int idGroup;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Embedded.class)
    private int idRole;

    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Embedded.class)
    private Integer idEmployee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    @JsonView(JsonViews.Embedded.class)
    private List<UsersRole> usersRoleList;

    public Users() {
    }

    public Users(Integer idUser) {
        this.idUser = idUser;
    }

    public Users(Integer idUser, int idCompany, int idRegion, int idBranch, int idGroup, int idRole, String username, String password, String mail, int status, int activeSession, LocalTime timeSession, LocalDateTime highDate) {
        this.idUser = idUser;
        this.idCompany = idCompany;
        this.idRegion = idRegion;
        this.idBranch = idBranch;
        this.idGroup = idGroup;
        this.idRole = idRole;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.status = status;
        this.activeSession = activeSession;
        this.timeSession = timeSession;
        this.highDate = highDate;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public int getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(int idBranch) {
        this.idBranch = idBranch;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(int activeSession) {
        this.activeSession = activeSession;
    }

    public LocalTime getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalTime timeSession) {
        this.timeSession = timeSession;
    }

    public LocalDateTime getHighDate() {
        return highDate;
    }

    public void setHighDate(LocalDateTime highDate) {
        this.highDate = highDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDateTime getLowDate() {
        return lowDate;
    }

    public void setLowDate(LocalDateTime lowDate) {
        this.lowDate = lowDate;
    }

    public List<UsersRole> getUsersRoleList() {
        return usersRoleList;
    }

    public void setUsersRoleList(List<UsersRole> usersRoleList) {
        this.usersRoleList = usersRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Users{" + "idUser=" + idUser + ", username=" + username + ", password=" + password + ", mail=" + mail + ", status=" + status + ", activeSession=" + activeSession + ", timeSession=" + timeSession + ", highDate=" + highDate + ", modificationDate=" + modificationDate + ", lowDate=" + lowDate + ", idCompany=" + idCompany + ", idRegion=" + idRegion + ", idBranch=" + idBranch + ", idGroup=" + idGroup + ", idRole=" + idRole + ", idEmployee=" + idEmployee + '}';
    }
    
}
