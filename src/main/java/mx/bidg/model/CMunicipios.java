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
@DynamicUpdate
@Table(name = "C_MUNICIPIOS")

public class CMunicipios implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected CMunicipiosPK cMunicipiosPK;

    @Column(name = "ID_MUNICIPIO", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idMunicipio;


    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_MUNICIPIOS")
    @JsonView(JsonViews.Root.class)
    private String nombreMunicipios;



    @Column(name="ID_ESTADO", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idEstado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @JsonView(JsonViews.Embedded.class)
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

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
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

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
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
