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
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author josue
 */
@Entity
@DynamicUpdate
@Table(name = "AUTHORIZATION_REPORTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AuthorizationReports implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AUTHORIZATION_REPORTS")
    @JsonView(JsonViews.Root.class)
    private Integer idAuthorizationReports;

    @Column(name = "AUTHORIZATION")
    @JsonView(JsonViews.Root.class)
    private Integer authorization;

    @Column(name = "ID_QUERY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idQuery;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRoles cRoles;

    @JoinColumn(name = "ID_QUERY", referencedColumnName = "ID_QUERY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private SqlQueries sqlQueries;

    public AuthorizationReports() {
    }

    public AuthorizationReports(Integer idAuthorizationReports) {
        this.idAuthorizationReports = idAuthorizationReports;
    }

    public Integer getIdAuthorizationReports() {
        return idAuthorizationReports;
    }

    public void setIdAuthorizationReports(Integer idAuthorizationReports) {
        this.idAuthorizationReports = idAuthorizationReports;
    }

    public Integer getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Integer authorization) {
        this.authorization = authorization;
    }

    public Integer getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(Integer idQuery) {
        this.idQuery = idQuery;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public CRoles getcRoles() {
        return cRoles;
    }

    public void setcRoles(CRoles cRoles) {
        this.cRoles = cRoles;
    }

    public SqlQueries getSqlQueries() {
        return sqlQueries;
    }

    public void setSqlQueries(SqlQueries sqlQueries) {
        this.sqlQueries = sqlQueries;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthorizationReports != null ? idAuthorizationReports.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthorizationReports)) {
            return false;
        }
        AuthorizationReports other = (AuthorizationReports) object;
        if ((this.idAuthorizationReports == null && other.idAuthorizationReports != null) || (this.idAuthorizationReports != null && !this.idAuthorizationReports.equals(other.idAuthorizationReports))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AuthorizationReports[ idAuthorizationReports=" + idAuthorizationReports + " ]";
    }
    
}
