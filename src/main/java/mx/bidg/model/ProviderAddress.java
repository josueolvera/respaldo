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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "PROVIDER_ADDRESS")

public class ProviderAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROVIDER_ADDRESS")
    @JsonView(JsonViews.Root.class)
    private Integer idProviderAddress;

    @Column(name = "CP")
    @JsonView(JsonViews.Root.class)
    private Integer cp;

    @Size(max = 50)
    @Column(name = "STREET")
    @JsonView(JsonViews.Root.class)
    private String street;

    @Column(name = "NUM_EXT")
    @JsonView(JsonViews.Root.class)
    private Integer numExt;

    @Column(name = "NUM_INT")
    @JsonView(JsonViews.Root.class)
    private Integer numInt;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Column(name="ID_PROVIDER", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idProviders;

    @Column(name="ID_STATES", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idStates;

    @Column(name="ID_SETTLEMENT", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idSettlements;

    @Column(name="ID_MUNICIPALITY", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idMunicipalitys;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Providers idProvider;

    @JoinColumn(name = "ID_STATE", referencedColumnName = "ID_STATE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CStates idState;

    @JoinColumn(name = "ID_SETTLEMENT", referencedColumnName = "ID_SETTLEMENT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CSettlement idSettlement;

    @JoinColumn(name = "ID_MUNICIPALITY", referencedColumnName = "ID_MUNICIPALITY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CMunicipalities idMunicipality;

    public ProviderAddress() {
    }

    public ProviderAddress(Integer idProviderAddress) {
        this.idProviderAddress = idProviderAddress;
    }

    public ProviderAddress(Integer idProviderAddress, int idAccessLevel) {
        this.idProviderAddress = idProviderAddress;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdProviderAddress() {
        return idProviderAddress;
    }

    public void setIdProviderAddress(Integer idProviderAddress) {
        this.idProviderAddress = idProviderAddress;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumExt() {
        return numExt;
    }

    public void setNumExt(Integer numExt) {
        this.numExt = numExt;
    }

    public Integer getNumInt() {
        return numInt;
    }

    public void setNumInt(Integer numInt) {
        this.numInt = numInt;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Providers getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Providers idProvider) {
        this.idProvider = idProvider;
    }

    public CStates getIdState() {
        return idState;
    }

    public void setIdState(CStates idState) {
        this.idState = idState;
    }

    public CSettlement getIdSettlement() {
        return idSettlement;
    }

    public void setIdSettlement(CSettlement idSettlement) {
        this.idSettlement = idSettlement;
    }

    public CMunicipalities getIdMunicipality() {
        return idMunicipality;
    }

    public void setIdMunicipality(CMunicipalities idMunicipality) {
        this.idMunicipality = idMunicipality;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProviderAddress != null ? idProviderAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProviderAddress)) {
            return false;
        }
        ProviderAddress other = (ProviderAddress) object;
        if ((this.idProviderAddress == null && other.idProviderAddress != null) || (this.idProviderAddress != null && !this.idProviderAddress.equals(other.idProviderAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ProviderAddress[ idProviderAddress=" + idProviderAddress + " ]";
    }
    
}
