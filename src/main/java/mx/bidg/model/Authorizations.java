/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "AUTHORIZATIONS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Authorizations implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUTHORIZATION")
    @JsonView(JsonViews.Root.class)
    private Integer idAuthorization;

    @Basic(optional = false)
    @Column(name = "AUTHORIZATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime authorizationDate;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Basic(optional = false)
    @Column(name = "DETAILS")
    @JsonView(JsonViews.Root.class)
    private String details;

    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Column(name = "AUTHORIZATION_ORDER")
    @JsonView(JsonViews.Root.class)
    private Integer authorizationOrder;
    
    @Column(name = "ID_AUTHORIZATION_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAuthorizationStatus;

    @JoinColumn(name = "ID_AUTHORIZATION_STATUS", referencedColumnName = "ID_AUTHORIZATION_STATUS")
    @ManyToOne
    @JsonProperty("authorizationStatus")
    @JsonView(JsonViews.Embedded.class)
    private CAuthorizationStatus cAuthorizationStatus;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Users users;

    public Authorizations() {
    }

    public Authorizations(Integer idAuthorization) {
        this.idAuthorization = idAuthorization;
    }

    public Authorizations(Integer idAuthorization, LocalDateTime authorizationDate) {
        this.idAuthorization = idAuthorization;
        this.authorizationDate = authorizationDate;
    }

    public Integer getIdAuthorization() {
        return idAuthorization;
    }

    public void setIdAuthorization(Integer idAuthorization) {
        this.idAuthorization = idAuthorization;
    }

    public LocalDateTime getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(LocalDateTime authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getAuthorizationOrder() {
        return authorizationOrder;
    }

    public void setAuthorizationOrder(Integer authorizationOrder) {
        this.authorizationOrder = authorizationOrder;
    }

    public Integer getIdAuthorizationStatus() {
        return idAuthorizationStatus;
    }

    public void setIdAuthorizationStatus(Integer idAuthorizationStatus) {
        this.idAuthorizationStatus = idAuthorizationStatus;
    }

    public CAuthorizationStatus getCAuthorizationStatus() {
        return cAuthorizationStatus;
    }

    @JsonProperty("authorizationStatus")
    public void setCAuthorizationStatus(CAuthorizationStatus cAuthorizationStatus) {
        this.cAuthorizationStatus = cAuthorizationStatus;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthorization != null ? idAuthorization.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authorizations)) {
            return false;
        }
        Authorizations other = (Authorizations) object;
        if ((this.idAuthorization == null && other.idAuthorization != null) || (this.idAuthorization != null && !this.idAuthorization.equals(other.idAuthorization))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Authorizations[ idAuthorization=" + idAuthorization + " ]";
    }
    
}
