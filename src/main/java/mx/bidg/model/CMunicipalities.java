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
@Table(name = "C_MUNICIPALITIES")

public class CMunicipalities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MUNICIPALITY")
    @JsonView(JsonViews.Root.class)
    private Integer idMunicipality;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MUNICIPALITY_NAME")
    @JsonView(JsonViews.Root.class)
    private String municipalityName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMunicipality")
    @JsonView(JsonViews.Embedded.class)
    private List<ProviderAddress> providerAddressList;

    public CMunicipalities() {
    }

    public CMunicipalities(Integer idMunicipality) {
        this.idMunicipality = idMunicipality;
    }

    public CMunicipalities(Integer idMunicipality, String municipalityName, int idAccessLevel) {
        this.idMunicipality = idMunicipality;
        this.municipalityName = municipalityName;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdMunicipality() {
        return idMunicipality;
    }

    public void setIdMunicipality(Integer idMunicipality) {
        this.idMunicipality = idMunicipality;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
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
        hash += (idMunicipality != null ? idMunicipality.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CMunicipalities)) {
            return false;
        }
        CMunicipalities other = (CMunicipalities) object;
        if ((this.idMunicipality == null && other.idMunicipality != null) || (this.idMunicipality != null && !this.idMunicipality.equals(other.idMunicipality))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CMunicipalities[ idMunicipality=" + idMunicipality + " ]";
    }
    
}
