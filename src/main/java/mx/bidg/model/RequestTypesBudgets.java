/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "REQUEST_TYPES_BUDGETS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestTypesBudgets implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_TYPE_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestTypeBudget;
    
    @JoinColumn(name = "ID_REQUEST_TYPE", referencedColumnName = "ID_REQUEST_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Root.class)
    private CRequestTypes idRequestType;
    
    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Root.class)
    private Budgets idBudget;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    public RequestTypesBudgets() {
    }

    public RequestTypesBudgets(Integer idRequestTypeBudget) {
        this.idRequestTypeBudget = idRequestTypeBudget;
    }

    public Integer getIdRequestTypeBudget() {
        return idRequestTypeBudget;
    }

    public void setIdRequestTypeBudget(Integer idRequestTypeBudget) {
        this.idRequestTypeBudget = idRequestTypeBudget;
    }

    public CRequestTypes getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(CRequestTypes idRequestType) {
        this.idRequestType = idRequestType;
    }

    public Budgets getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Budgets idBudget) {
        this.idBudget = idBudget;
    }
    
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestTypeBudget != null ? idRequestTypeBudget.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestTypesBudgets)) {
            return false;
        }
        RequestTypesBudgets other = (RequestTypesBudgets) object;
        if ((this.idRequestTypeBudget == null && other.idRequestTypeBudget != null) || (this.idRequestTypeBudget != null && !this.idRequestTypeBudget.equals(other.idRequestTypeBudget))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RequestTypesBudgets[ idRequestTypeBudget=" + idRequestTypeBudget + " ]";
    }
    
}
