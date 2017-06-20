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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author desarrollador
 */
@Entity
@DynamicUpdate
@Table(name = "AUTHORIZATION_AMOUNT_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AuthorizationAmountRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AUTHORIZATION_AMOUNT_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idAuthorizationAmountRole;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @Column(name = "LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer level;

    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Size(max = 100)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @OneToOne(fetch=FetchType.EAGER, optional = true)
    @JoinColumn(name="ID_ROLE")
    @JsonView(JsonViews.Embedded.class)
    private CRoles rol;

    public AuthorizationAmountRole() {
    }

    public AuthorizationAmountRole(Integer idAuthorizationAmountRole) {
        this.idAuthorizationAmountRole = idAuthorizationAmountRole;
    }

    public AuthorizationAmountRole(Integer idAuthorizationAmountRole, LocalDateTime creationDate) {
        this.idAuthorizationAmountRole = idAuthorizationAmountRole;
        this.creationDate = creationDate;
    }

    public Integer getIdAuthorizationAmountRole() {
        return idAuthorizationAmountRole;
    }

    public void setIdAuthorizationAmountRole(Integer idAuthorizationAmountRole) {
        this.idAuthorizationAmountRole = idAuthorizationAmountRole;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public CRoles getRol() {
        return rol;
    }

    public void setRol(CRoles rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthorizationAmountRole != null ? idAuthorizationAmountRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthorizationAmountRole)) {
            return false;
        }
        AuthorizationAmountRole other = (AuthorizationAmountRole) object;
        if ((this.idAuthorizationAmountRole == null && other.idAuthorizationAmountRole != null) || (this.idAuthorizationAmountRole != null && !this.idAuthorizationAmountRole.equals(other.idAuthorizationAmountRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AuthorizationAmountRole[ idAuthorizationAmountRole=" + idAuthorizationAmountRole + " ]";
    }
    
}
