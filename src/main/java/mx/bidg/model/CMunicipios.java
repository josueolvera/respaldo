/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author rubens
 */
@Entity
@Table(name = "C_MUNICIPIOS")

public class CMunicipios implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected CMunicipiosPK cMunicipiosPK;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_MUNICIPIOS")
    private String nombreMunicipios;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CEstados cEstados;

    public CMunicipios() {
    }

    public CMunicipios(CMunicipiosPK cMunicipiosPK) {
        this.cMunicipiosPK = cMunicipiosPK;
    }

    public CMunicipios(CMunicipiosPK cMunicipiosPK, String nombreMunicipios, int idAccessLevel) {
        this.cMunicipiosPK = cMunicipiosPK;
        this.nombreMunicipios = nombreMunicipios;
        this.idAccessLevel = idAccessLevel;
    }

    public CMunicipios(int idEstado, int idMunicipio) {
        this.cMunicipiosPK = new CMunicipiosPK(idEstado, idMunicipio);
    }

    public CMunicipiosPK getCMunicipiosPK() {
        return cMunicipiosPK;
    }

    public void setCMunicipiosPK(CMunicipiosPK cMunicipiosPK) {
        this.cMunicipiosPK = cMunicipiosPK;
    }

    public String getNombreMunicipios() {
        return nombreMunicipios;
    }

    public void setNombreMunicipios(String nombreMunicipios) {
        this.nombreMunicipios = nombreMunicipios;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CEstados getCEstados() {
        return cEstados;
    }

    public void setCEstados(CEstados cEstados) {
        this.cEstados = cEstados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cMunicipiosPK != null ? cMunicipiosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CMunicipios)) {
            return false;
        }
        CMunicipios other = (CMunicipios) object;
        if ((this.cMunicipiosPK == null && other.cMunicipiosPK != null) || (this.cMunicipiosPK != null && !this.cMunicipiosPK.equals(other.cMunicipiosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CMunicipios[ cMunicipiosPK=" + cMunicipiosPK + " ]";
    }
    
}
