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
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "GROUP_AGREEMENTS_GOAL_PERCENTAGE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class GroupAgreementsGoalPercentage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GROUP_AGREEMENT_GOAL_PERCENTAGE")
    @JsonView(JsonViews.Root.class)
    private Integer idGroupAgreementGoalPercentage;

    @Column(name = "PERCENTAGE")
    @JsonView(JsonViews.Root.class)
    private Long percentage;

    @Column(name = "ID_OPERATION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idOperation;

    @Column(name = "ID_GROUP_AGREEMENT_GOAL", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idGroupAgreementGoal;

    @JoinColumn(name = "ID_OPERATION", referencedColumnName = "ID_OPERATION")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private COperations operation;

    @JoinColumn(name = "ID_GROUP_AGREEMENT_GOAL", referencedColumnName = "ID_GROUP_AGREEMENT_GOAL")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private GroupsAgreementsGoal groupAgreementGoal;

    public GroupAgreementsGoalPercentage() {
    }

    public Integer getIdGroupAgreementGoalPercentage() {
        return idGroupAgreementGoalPercentage;
    }

    public void setIdGroupAgreementGoalPercentage(Integer idGroupAgreementGoalPercentage) {
        this.idGroupAgreementGoalPercentage = idGroupAgreementGoalPercentage;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    public Integer getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Integer idOperation) {
        this.idOperation = idOperation;
    }

    public Integer getIdGroupAgreementGoal() {
        return idGroupAgreementGoal;
    }

    public void setIdGroupAgreementGoal(Integer idGroupAgreementGoal) {
        this.idGroupAgreementGoal = idGroupAgreementGoal;
    }

    public COperations getOperation() {
        return operation;
    }

    public void setOperation(COperations operation) {
        this.operation = operation;
    }

    public GroupsAgreementsGoal getGroupAgreementGoal() {
        return groupAgreementGoal;
    }

    public void setGroupAgreementGoal(GroupsAgreementsGoal groupAgreementGoal) {
        this.groupAgreementGoal = groupAgreementGoal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGroupAgreementGoalPercentage != null ? idGroupAgreementGoalPercentage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupAgreementsGoalPercentage)) {
            return false;
        }
        GroupAgreementsGoalPercentage other = (GroupAgreementsGoalPercentage) object;
        if ((this.idGroupAgreementGoalPercentage == null && other.idGroupAgreementGoalPercentage != null) || (this.idGroupAgreementGoalPercentage != null && !this.idGroupAgreementGoalPercentage.equals(other.idGroupAgreementGoalPercentage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.GroupAgreementsGoalPercentage[ idGroupAgreementGoalPercentage=" + idGroupAgreementGoalPercentage + " ]";
    }
    
}
