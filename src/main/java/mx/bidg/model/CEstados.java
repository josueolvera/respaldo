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
 * @author rubens
 */
@Entity
@DynamicUpdate
@Table(name = "C_ESTADOS")

public class CEstados implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    @JsonView(JsonViews.Root.class)
    private Integer idEstado;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_ESTADO")
    @JsonView(JsonViews.Root.class)
    private String nombreEstado;
    
    @Size(max = 20)
    @Column(name = "ABBREVIACION")
    @JsonView(JsonViews.Root.class)
    private String abbreviacion;
    
    @Size(max = 20)
    @Column(name = "ABBREVIACION_2")
    @JsonView(JsonViews.Root.class)
    private String abbreviacion2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estados")
    @JsonView(JsonViews.Embedded.class)
    private List<CMunicipios> cMunicipiosList;

    public CEstados() {
    }

    public CEstados(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public CEstados(Integer idEstado, String nombreEstado, int idAccessLevel) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getAbbreviacion() {
        return abbreviacion;
    }

    public void setAbbreviacion(String abbreviacion) {
        this.abbreviacion = abbreviacion;
    }

    public String getAbbreviacion2() {
        return abbreviacion2;
    }

    public void setAbbreviacion2(String abbreviacion2) {
        this.abbreviacion2 = abbreviacion2;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @XmlTransient
    public List<CMunicipios> getCMunicipiosList() {
        return cMunicipiosList;
    }

    public void setCMunicipiosList(List<CMunicipios> cMunicipiosList) {
        this.cMunicipiosList = cMunicipiosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CEstados)) {
            return false;
        }
        CEstados other = (CEstados) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CEstados[ idEstado=" + idEstado + " ]";
    }
    
}
