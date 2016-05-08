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
@Table(name = "C_SETTLEMENT")

public class CSettlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SETTLEMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idSettlement;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SETTLEMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String settlementName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSettlement")
    @JsonView(JsonViews.Embedded.class)
    private List<ProviderAddress> providerAddressList;

    public CSettlement() {
    }

    public CSettlement(Integer idSettlement) {
        this.idSettlement = idSettlement;
    }

    public CSettlement(Integer idSettlement, String settlementName, int idAccessLevel) {
        this.idSettlement = idSettlement;
        this.settlementName = settlementName;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdSettlement() {
        return idSettlement;
    }

    public void setIdSettlement(Integer idSettlement) {
        this.idSettlement = idSettlement;
    }

    public String getSettlementName() {
        return settlementName;
    }

    public void setSettlementName(String settlementName) {
        this.settlementName = settlementName;
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
        hash += (idSettlement != null ? idSettlement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSettlement)) {
            return false;
        }
        CSettlement other = (CSettlement) object;
        if ((this.idSettlement == null && other.idSettlement != null) || (this.idSettlement != null && !this.idSettlement.equals(other.idSettlement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CSettlement[ idSettlement=" + idSettlement + " ]";
    }
    
}
