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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_STATES")

public class CStates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_STATE")
    @JsonView(JsonViews.Root.class)
    private Integer idState;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "STATE_NAME")
    @JsonView(JsonViews.Root.class)
    private String stateName;

    @Size(max = 20)
    @Column(name = "ABBREVIATION")
    @JsonView(JsonViews.Root.class)
    private String abbreviation;

    @Size(max = 20)
    @Column(name = "ABBREVIATION_2")
    @JsonView(JsonViews.Root.class)
    private String abbreviation2;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idState")
    @JsonView(JsonViews.Embedded.class)
    private List<ProviderAddress> providerAddressList;

    public CStates() {
    }

    public CStates(Integer idState) {
        this.idState = idState;
    }

    public CStates(Integer idState, String stateName, int idAccessLevel) {
        this.idState = idState;
        this.stateName = stateName;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdState() {
        return idState;
    }

    public void setIdState(Integer idState) {
        this.idState = idState;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation2() {
        return abbreviation2;
    }

    public void setAbbreviation2(String abbreviation2) {
        this.abbreviation2 = abbreviation2;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @XmlTransient
    public List<ProviderAddress> getProviderAddressList() {
        return providerAddressList;
    }

    public void setProviderAddressList(List<ProviderAddress> providerAddressList) {
        this.providerAddressList = providerAddressList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idState != null ? idState.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CStates)) {
            return false;
        }
        CStates other = (CStates) object;
        if ((this.idState == null && other.idState != null) || (this.idState != null && !this.idState.equals(other.idState))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CStates[ idState=" + idState + " ]";
    }
    
}
