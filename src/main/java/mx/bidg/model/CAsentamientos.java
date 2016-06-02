/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

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
 * @author rubens
 */
@Entity
@Table(name = "C_ASENTAMIENTOS")

public class CAsentamientos implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASENTAMIENTO")
    private Integer idAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_ASENTAMIENTO")
    private String nombreAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_ASENTAMIENTO")
    private int idTipoAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    private int idEstado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MUNICIPIO")
    private int idMunicipio;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_POSTAL")
    private int codigoPostal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAsentamiento")
    private List<ProviderAddress> providerAddressList;

    public CAsentamientos() {
    }

    public CAsentamientos(Integer idAsentamiento) {
        this.idAsentamiento = idAsentamiento;
    }

    public CAsentamientos(Integer idAsentamiento, String nombreAsentamiento, int idAccessLevel, int idTipoAsentamiento, int idEstado, int idMunicipio, int codigoPostal) {
        this.idAsentamiento = idAsentamiento;
        this.nombreAsentamiento = nombreAsentamiento;
        this.idAccessLevel = idAccessLevel;
        this.idTipoAsentamiento = idTipoAsentamiento;
        this.idEstado = idEstado;
        this.idMunicipio = idMunicipio;
        this.codigoPostal = codigoPostal;
    }

    public Integer getIdAsentamiento() {
        return idAsentamiento;
    }

    public void setIdAsentamiento(Integer idAsentamiento) {
        this.idAsentamiento = idAsentamiento;
    }

    public String getNombreAsentamiento() {
        return nombreAsentamiento;
    }

    public void setNombreAsentamiento(String nombreAsentamiento) {
        this.nombreAsentamiento = nombreAsentamiento;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public int getIdTipoAsentamiento() {
        return idTipoAsentamiento;
    }

    public void setIdTipoAsentamiento(int idTipoAsentamiento) {
        this.idTipoAsentamiento = idTipoAsentamiento;
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

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
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
        hash += (idAsentamiento != null ? idAsentamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAsentamientos)) {
            return false;
        }
        CAsentamientos other = (CAsentamientos) object;
        if ((this.idAsentamiento == null && other.idAsentamiento != null) || (this.idAsentamiento != null && !this.idAsentamiento.equals(other.idAsentamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAsentamientos[ idAsentamiento=" + idAsentamiento + " ]";
    }
    
}
