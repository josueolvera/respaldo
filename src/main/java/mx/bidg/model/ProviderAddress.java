/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVIDER_ADDRESS")
    @JsonView(JsonViews.Root.class)
    private Integer idProviderAddress;

    @Size(max = 50)
    @Column(name = "STREET")
    @JsonView(JsonViews.Root.class)
    private String street;

    @Column(name = "NUM_EXT")
    @JsonView(JsonViews.Root.class)
    private String numExt;

    @Column(name = "NUM_INT")
    @JsonView(JsonViews.Root.class)
    private String numInt;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Column(name="ID_PROVIDER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;
    
    @Column(name="ID_ASENTAMIENTO", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAsentamiento;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Providers provider;
    
    @JoinColumn(name = "ID_ASENTAMIENTO", referencedColumnName = "ID_ASENTAMIENTO")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    @JsonProperty("asentamiento")
    private CAsentamientos asentamientos;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumExt() {
        return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getNumInt() {
        return numInt;
    }

    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }
    
    public Integer getIdAsentamiento() {
        return idAsentamiento;
    }

    public void setIdAsentamiento(Integer idAsentamiento) {
        this.idAsentamiento = idAsentamiento;
    }

    public CAsentamientos getAsentamiento() {
        return asentamientos;
    }

    public void setAsentamiento(CAsentamientos asentamiento) {
        this.asentamientos = asentamiento;
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
