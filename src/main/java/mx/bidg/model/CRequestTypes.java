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

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_REQUEST_TYPES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CRequestTypes implements Serializable {
    
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestType;

    @Size(max = 100)
    @Column(name = "REQUEST_TYPE")
    @JsonView(JsonViews.Root.class)
    private String requestType;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRequestType")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestTypesBudgets> requestTypesBudgetsList;

    public CRequestTypes() {
    }

    public CRequestTypes(Integer idRequestType) {
        this.idRequestType = idRequestType;
    }

    public Integer getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(Integer idRequestType) {
        this.idRequestType = idRequestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }
    
    public List<RequestTypesBudgets> getRequestTypesBudgetsList() {
        return requestTypesBudgetsList;
    }

    public void setRequestTypesBudgetsList(List<RequestTypesBudgets> requestTypesBudgetsList) {
        this.requestTypesBudgetsList = requestTypesBudgetsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestType != null ? idRequestType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CRequestTypes)) {
            return false;
        }
        CRequestTypes other = (CRequestTypes) object;
        if ((this.idRequestType == null && other.idRequestType != null) || (this.idRequestType != null && !this.idRequestType.equals(other.idRequestType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CRequestTypes[ idRequestType=" + idRequestType + " ]";
    }
    
}
