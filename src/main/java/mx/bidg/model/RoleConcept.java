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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "ROLE_CONCEPT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RoleConcept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ROLE_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idRoleConcept;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTHORIZED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal authorizedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_TRAVEL_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelType;

    @Column(name = "ID_BUDGET_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;

    @JoinColumn(name = "ID_TRAVEL_TYPE", referencedColumnName = "ID_TRAVEL_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelTypes travelType;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRoles role;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo creationDateFormats;

    public RoleConcept() {
    }

    public RoleConcept(Integer idRoleConcept) {
        this.idRoleConcept = idRoleConcept;
    }

    public RoleConcept(Integer idRoleConcept, BigDecimal authorizedAmount, LocalDateTime creationDate) {
        this.idRoleConcept = idRoleConcept;
        this.authorizedAmount = authorizedAmount;
        this.creationDate = creationDate;
    }

    public Integer getIdRoleConcept() {
        return idRoleConcept;
    }

    public void setIdRoleConcept(Integer idRoleConcept) {
        this.idRoleConcept = idRoleConcept;
    }

    public BigDecimal getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(BigDecimal authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdTravelType() {
        return idTravelType;
    }

    public void setIdTravelType(Integer idTravelType) {
        this.idTravelType = idTravelType;
    }

    public CTravelTypes getTravelType() {
        return travelType;
    }

    public void setTravelType(CTravelTypes travelType) {
        this.travelType = travelType;
    }

    public Integer getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public CRoles getRole() {
        return role;
    }

    public void setRole(CRoles role) {
        this.role = role;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public DateFormatsPojo getCreationDateFormats() {
        this.creationDateFormats = (creationDate == null) ? null : new DateFormatsPojo(creationDate);
        return this.creationDateFormats;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRoleConcept != null ? idRoleConcept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleConcept)) {
            return false;
        }
        RoleConcept other = (RoleConcept) object;
        if ((this.idRoleConcept == null && other.idRoleConcept != null) || (this.idRoleConcept != null && !this.idRoleConcept.equals(other.idRoleConcept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RoleConcept[ idRoleConcept=" + idRoleConcept + " ]";
    }

}
