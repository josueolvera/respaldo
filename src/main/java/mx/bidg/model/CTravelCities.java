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
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "C_TRAVEL_CITIES")
public class CTravelCities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRAVEL_CITY")
    @JsonView(JsonViews.Root.class)
    private Integer idTravelCity;

    @Size(max = 80)
    @Column(name = "CITY_NAME")
    @JsonView(JsonViews.Root.class)
    private String cityName;

    @Column(name = "ID_ESTADO", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEstado;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @JsonView(JsonViews.Root.class)
    private CEstados estado;

    public CTravelCities() {
    }

    public CTravelCities(Integer idTravelCity) {
        this.idTravelCity = idTravelCity;
    }

    public Integer getIdTravelCity() {
        return idTravelCity;
    }

    public void setIdTravelCity(Integer idTravelCity) {
        this.idTravelCity = idTravelCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public CEstados getEstado() {
        return estado;
    }

    public void setEstado(CEstados estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTravelCity != null ? idTravelCity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTravelCities)) {
            return false;
        }
        CTravelCities other = (CTravelCities) object;
        if ((this.idTravelCity == null && other.idTravelCity != null) || (this.idTravelCity != null && !this.idTravelCity.equals(other.idTravelCity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTravelCities[ idTravelCity=" + idTravelCity + " ]";
    }
    
}
