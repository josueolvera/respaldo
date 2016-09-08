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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "RULES_CALCULATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RulesCalculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_RULE_CALCULATION")
    @JsonView(JsonViews.Root.class)
    private Integer idRuleCalculation;

    @Size(max = 50)
    @Column(name = "DESCRIPTIION")
    @JsonView(JsonViews.Root.class)
    private String descriptiion;

    @Column(name = "ID_DIV_CALCULATION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDivCalculation;

    @Column(name = "ID_CALCULATION_ROLES", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCalculationRoles;

    @Column(name = "ID_GROUP_AGREEMENT_GOAL_PERCENTAGE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idGroupAgreementGoalPercentage;

    @JoinColumn(name = "ID_DIV_CALCULATION", referencedColumnName = "ID_DIV_CALCULATION")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CDivsCalculation divCalculation;

    @JoinColumn(name = "ID_CALCULATION_ROLES", referencedColumnName = "ID_CALCULATION_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CalculationRoles calculationRoles;

    @JoinColumn(name = "ID_GROUP_AGREEMENT_GOAL_PERCENTAGE", referencedColumnName = "ID_GROUP_AGREEMENT_GOAL_PERCENTAGE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private GroupAgreementsGoalPercentage groupAgreementGoalPercentage;

    public RulesCalculation() {
    }

    public RulesCalculation(Integer idRuleCalculation) {
        this.idRuleCalculation = idRuleCalculation;
    }

    public Integer getIdRuleCalculation() {
        return idRuleCalculation;
    }

    public void setIdRuleCalculation(Integer idRuleCalculation) {
        this.idRuleCalculation = idRuleCalculation;
    }

    public String getDescriptiion() {
        return descriptiion;
    }

    public void setDescriptiion(String descriptiion) {
        this.descriptiion = descriptiion;
    }

    public Integer getIdDivCalculation() {
        return idDivCalculation;
    }

    public void setIdDivCalculation(Integer idDivCalculation) {
        this.idDivCalculation = idDivCalculation;
    }

    public Integer getIdCalculationRoles() {
        return idCalculationRoles;
    }

    public void setIdCalculationRoles(Integer idCalculationRoles) {
        this.idCalculationRoles = idCalculationRoles;
    }

    public Integer getIdGroupAgreementGoalPercentage() {
        return idGroupAgreementGoalPercentage;
    }

    public void setIdGroupAgreementGoalPercentage(Integer idGroupAgreementGoalPercentage) {
        this.idGroupAgreementGoalPercentage = idGroupAgreementGoalPercentage;
    }

    public CDivsCalculation getDivCalculation() {
        return divCalculation;
    }

    public void setDivCalculation(CDivsCalculation divCalculation) {
        this.divCalculation = divCalculation;
    }

    public CalculationRoles getCalculationRoles() {
        return calculationRoles;
    }

    public void setCalculationRoles(CalculationRoles calculationRoles) {
        this.calculationRoles = calculationRoles;
    }

    public GroupAgreementsGoalPercentage getGroupAgreementGoalPercentage() {
        return groupAgreementGoalPercentage;
    }

    public void setGroupAgreementGoalPercentage(GroupAgreementsGoalPercentage groupAgreementGoalPercentage) {
        this.groupAgreementGoalPercentage = groupAgreementGoalPercentage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRuleCalculation != null ? idRuleCalculation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RulesCalculation)) {
            return false;
        }
        RulesCalculation other = (RulesCalculation) object;
        if ((this.idRuleCalculation == null && other.idRuleCalculation != null) || (this.idRuleCalculation != null && !this.idRuleCalculation.equals(other.idRuleCalculation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RulesCalculation[ idRuleCalculation=" + idRuleCalculation + " ]";
    }
    
}
