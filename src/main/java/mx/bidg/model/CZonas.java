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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_ZONAS")
public class CZonas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ZONAS")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private Integer idZonas;

    @Size(max = 45)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    @Column(name = "SAEM_FLAG")
    @JsonView(JsonViews.Root.class)
    private int saemFlag;

    @OneToMany(mappedBy = "zona")
    @JsonView(JsonViews.Root.class)
    private List<DwEnterprises> dwEnterprisesList;

    public CZonas() {
    }

    public CZonas(Integer idZonas) {
        this.idZonas = idZonas;
    }

    public Integer getIdZonas() {
        return idZonas;
    }

    public void setIdZonas(Integer idZonas) {
        this.idZonas = idZonas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DwEnterprises> getDwEnterprisesList() {
        return dwEnterprisesList;
    }

    public void setDwEnterprisesList(List<DwEnterprises> dwEnterprisesList) {
        this.dwEnterprisesList = dwEnterprisesList;
    }

    public int getSaemFlag() {
        return saemFlag;
    }

    public void setSaemFlag(int saemFlag) {
        this.saemFlag = saemFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZonas != null ? idZonas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CZonas)) {
            return false;
        }
        CZonas other = (CZonas) object;
        if ((this.idZonas == null && other.idZonas != null) || (this.idZonas != null && !this.idZonas.equals(other.idZonas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CZonas[ idZonas=" + idZonas + " ]";
    }
    
}
