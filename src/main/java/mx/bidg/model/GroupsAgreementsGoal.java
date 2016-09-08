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
import java.math.BigInteger;
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
@Table(name = "GROUPS_AGREEMENTS_GOAL")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class GroupsAgreementsGoal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GROUP_AGREEMENT_GOAL")
    @JsonView(JsonViews.Root.class)
    private Integer idGroupAgreementGoal;

    @Column(name = "GOAL")
    @JsonView(JsonViews.Root.class)
    private BigInteger goal;

    @Column(name = "ID_AG", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAg;

    @JoinColumn(name = "ID_AG", referencedColumnName = "ID_AG")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAgreementsGroups aG;

    public GroupsAgreementsGoal() {
    }

    public Integer getIdGroupAgreementGoal() {
        return idGroupAgreementGoal;
    }

    public void setIdGroupAgreementGoal(Integer idGroupAgreementGoal) {
        this.idGroupAgreementGoal = idGroupAgreementGoal;
    }

    public BigInteger getGoal() {
        return goal;
    }

    public void setGoal(BigInteger goal) {
        this.goal = goal;
    }

    public Integer getIdAg() {
        return idAg;
    }

    public void setIdAg(Integer idAg) {
        this.idAg = idAg;
    }

    public CAgreementsGroups getaG() {
        return aG;
    }

    public void setaG(CAgreementsGroups aG) {
        this.aG = aG;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGroupAgreementGoal != null ? idGroupAgreementGoal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsAgreementsGoal)) {
            return false;
        }
        GroupsAgreementsGoal other = (GroupsAgreementsGoal) object;
        if ((this.idGroupAgreementGoal == null && other.idGroupAgreementGoal != null) || (this.idGroupAgreementGoal != null && !this.idGroupAgreementGoal.equals(other.idGroupAgreementGoal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.GroupsAgreementsGoal[ idGroupAgreementGoal=" + idGroupAgreementGoal + " ]";
    }
    
}
