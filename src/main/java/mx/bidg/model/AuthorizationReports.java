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
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

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

    @Column(name = "AUTHORIZATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime authorizationDate;

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

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private CalculationReport calculationReport;

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

    public LocalDateTime getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(LocalDateTime authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public DateFormatsPojo getAuthorizationDateFormats() {
        if (authorizationDate == null) {
            return null;
        }
        return new DateFormatsPojo(authorizationDate);
    }

    public CalculationReport getCalculationReport() {
        return calculationReport;
    }

    public void setCalculationReport(CalculationReport calculationReport) {
        this.calculationReport = calculationReport;
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
