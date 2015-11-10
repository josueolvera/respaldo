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
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
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
    @Temporal(TemporalType.TIME)
    private Date timeSession;

    @Basic(optional = false)
    @NotNull
    @Column(name = "HIGH_DATE")
    @JsonView(JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date highDate;

    @Column(name = "MODIFICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @Column(name = "LOW_DATE")
    @JsonView(JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lowDate;

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

    public Users(Integer idUser, int idCompany, int idRegion, int idBranch, int idGroup, int idRole, String username, String password, String mail, int status, int activeSession, Date timeSession, Date highDate) {
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

    public Date getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(Date timeSession) {
        this.timeSession = timeSession;
    }

    public Date getHighDate() {
        return highDate;
    }

    public void setHighDate(Date highDate) {
        this.highDate = highDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Date getLowDate() {
        return lowDate;
    }

    public void setLowDate(Date lowDate) {
        this.lowDate = lowDate;
    }

    @XmlTransient
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
        return "mx.bidg.model.Users[ idUser=" + idUser + " ]";
    }
    
}
