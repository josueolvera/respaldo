/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "BRANCHS_GOALS")
public class BranchsGoals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;

    @Column(name = "GOAL_MONTH")
    @JsonView(JsonViews.Root.class)
    private Long goalMonth;

    @Column(name = "GOAL_DAY")
    @JsonView(JsonViews.Root.class)
    private Long goalDay;

    @Column(name = "GOAL_WEEK1")
    @JsonView(JsonViews.Root.class)
    private Long goalWeek1;

    @Column(name = "GOAL_WEEK2")
    @JsonView(JsonViews.Root.class)
    private Long goalWeek2;

    @Column(name = "GOAL_WEEK3")
    @JsonView(JsonViews.Root.class)
    private Long goalWeek3;

    @Column(name = "GOAL_WEEK4")
    @JsonView(JsonViews.Root.class)
    private Long goalWeek4;

    @Column(name = "GOAL_WEEK5")
    @JsonView(JsonViews.Root.class)
    private Long goalWeek5;

    public BranchsGoals() {
    }

    public BranchsGoals(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Long getGoalMonth() {
        return goalMonth;
    }

    public void setGoalMonth(Long goalMonth) {
        this.goalMonth = goalMonth;
    }

    public Long getGoalDay() {
        return goalDay;
    }

    public void setGoalDay(Long goalDay) {
        this.goalDay = goalDay;
    }

    public Long getGoalWeek1() {
        return goalWeek1;
    }

    public void setGoalWeek1(Long goalWeek1) {
        this.goalWeek1 = goalWeek1;
    }

    public Long getGoalWeek2() {
        return goalWeek2;
    }

    public void setGoalWeek2(Long goalWeek2) {
        this.goalWeek2 = goalWeek2;
    }

    public Long getGoalWeek3() {
        return goalWeek3;
    }

    public void setGoalWeek3(Long goalWeek3) {
        this.goalWeek3 = goalWeek3;
    }

    public Long getGoalWeek4() {
        return goalWeek4;
    }

    public void setGoalWeek4(Long goalWeek4) {
        this.goalWeek4 = goalWeek4;
    }

    public Long getGoalWeek5() {
        return goalWeek5;
    }

    public void setGoalWeek5(Long goalWeek5) {
        this.goalWeek5 = goalWeek5;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBranch != null ? idBranch.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BranchsGoals)) {
            return false;
        }
        BranchsGoals other = (BranchsGoals) object;
        if ((this.idBranch == null && other.idBranch != null) || (this.idBranch != null && !this.idBranch.equals(other.idBranch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BranchsGoals[ idBranch=" + idBranch + " ]";
    }
    
}
