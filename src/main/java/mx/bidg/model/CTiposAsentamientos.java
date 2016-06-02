/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "C_TIPOS_ASENTAMIENTOS")

public class CTiposAsentamientos implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TIPO_ASENTAMIENTO")
    private Integer idTipoAsentamiento;
    
    @Size(max = 50)
    @Column(name = "TIPO_ASENTAMIENTO")
    private String tipoAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;

    public CTiposAsentamientos() {
    }

    public CTiposAsentamientos(Integer idTipoAsentamiento) {
        this.idTipoAsentamiento = idTipoAsentamiento;
    }

    public CTiposAsentamientos(Integer idTipoAsentamiento, int idAccessLevel) {
        this.idTipoAsentamiento = idTipoAsentamiento;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdTipoAsentamiento() {
        return idTipoAsentamiento;
    }

    public void setIdTipoAsentamiento(Integer idTipoAsentamiento) {
        this.idTipoAsentamiento = idTipoAsentamiento;
    }

    public String getTipoAsentamiento() {
        return tipoAsentamiento;
    }

    public void setTipoAsentamiento(String tipoAsentamiento) {
        this.tipoAsentamiento = tipoAsentamiento;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoAsentamiento != null ? idTipoAsentamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTiposAsentamientos)) {
            return false;
        }
        CTiposAsentamientos other = (CTiposAsentamientos) object;
        if ((this.idTipoAsentamiento == null && other.idTipoAsentamiento != null) || (this.idTipoAsentamiento != null && !this.idTipoAsentamiento.equals(other.idTipoAsentamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTiposAsentamientos[ idTipoAsentamiento=" + idTipoAsentamiento + " ]";
    }
    
}
