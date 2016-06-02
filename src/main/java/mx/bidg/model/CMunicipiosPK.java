/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rubens
 */
@Embeddable
public class CMunicipiosPK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    @JsonView(JsonViews.Root.class)
    private int idEstado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MUNICIPIO")
    @JsonView(JsonViews.Root.class)
    private int idMunicipio;

    public CMunicipiosPK() {
    }

    public CMunicipiosPK(int idEstado, int idMunicipio) {
        this.idEstado = idEstado;
        this.idMunicipio = idMunicipio;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
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
        hash += (int) idEstado;
        hash += (int) idMunicipio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CMunicipiosPK)) {
            return false;
        }
        CMunicipiosPK other = (CMunicipiosPK) object;
        if (this.idEstado != other.idEstado) {
            return false;
        }
        if (this.idMunicipio != other.idMunicipio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CMunicipiosPK[ idEstado=" + idEstado + ", idMunicipio=" + idMunicipio + " ]";
    }
    
}
