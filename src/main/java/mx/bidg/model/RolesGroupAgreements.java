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
import org.hibernate.annotations.Type;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "ROLES_GROUP_AGREEMENTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RolesGroupAgreements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ROLE_GROUP_AGREEMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idRoleGroupAgreement;

    @Column(name = "ID_CALCULATION_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCalculationRole;

    @Column(name = "ID_AG", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAg;

    @Column(name = "HAS_GROUP", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean hasGroup;

    @JoinColumn(name = "ID_CALCULATION_ROLE", referencedColumnName = "ID_CALCULATION_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CalculationRoles calculationRole;

    @JoinColumn(name = "ID_AG", referencedColumnName = "ID_AG")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAgreementsGroups aG;

    public RolesGroupAgreements() {
    }

    public Integer getIdRoleGroupAgreement() {
        return idRoleGroupAgreement;
    }

    public void setIdRoleGroupAgreement(Integer idRoleGroupAgreement) {
        this.idRoleGroupAgreement = idRoleGroupAgreement;
    }

    public Integer getIdCalculationRole() {
        return idCalculationRole;
    }

    public void setIdCalculationRole(Integer idCalculationRole) {
        this.idCalculationRole = idCalculationRole;
    }

    public Integer getIdAg() {
        return idAg;
    }

    public void setIdAg(Integer idAg) {
        this.idAg = idAg;
    }

    public CalculationRoles getCalculationRole() {
        return calculationRole;
    }

    public void setCalculationRole(CalculationRoles calculationRole) {
        this.calculationRole = calculationRole;
    }

    public CAgreementsGroups getaG() {
        return aG;
    }

    public void setaG(CAgreementsGroups aG) {
        this.aG = aG;
    }

    public boolean isHasGroup() {
        return hasGroup;
    }

    public void setHasGroup(boolean hasGroup) {
        this.hasGroup = hasGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRoleGroupAgreement != null ? idRoleGroupAgreement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolesGroupAgreements)) {
            return false;
        }
        RolesGroupAgreements other = (RolesGroupAgreements) object;
        if ((this.idRoleGroupAgreement == null && other.idRoleGroupAgreement != null) || (this.idRoleGroupAgreement != null && !this.idRoleGroupAgreement.equals(other.idRoleGroupAgreement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RolesGroupAgreements[ idRoleGroupAgreement=" + idRoleGroupAgreement + " ]";
    }
    
}
