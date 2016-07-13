/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "FLIGHTS")
public class Flights implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_FLIGHT")
    @JsonView(JsonViews.Root.class)
    private Integer idFlight;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DEPARTURE_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime departureDate;

    @Column(name = "ID_PLANE_TICKET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneTicket;

    @Column(name = "ID_CITY_ORIGIN", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCityOrigin;

    @Column(name = "ID_CITY_DESTINATION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCityDestination;

    @JoinColumn(name = "ID_PLANE_TICKET", referencedColumnName = "ID_PLANE_TICKET")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private PlaneTickets planeTicket;

    @JoinColumn(name = "ID_CITY_ORIGIN", referencedColumnName = "ID_TRAVEL_CITY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelCities cityOrigin;

    @JoinColumn(name = "ID_CITY_DESTINATION", referencedColumnName = "ID_TRAVEL_CITY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelCities cityDestination;

    public Flights() {
    }

    public Flights(Integer idFlight) {
        this.idFlight = idFlight;
    }

    public Flights(Integer idFlight, LocalDateTime departureDate) {
        this.idFlight = idFlight;
        this.departureDate = departureDate;
    }

    public Integer getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Integer idFlight) {
        this.idFlight = idFlight;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getIdPlaneTicket() {
        return idPlaneTicket;
    }

    public void setIdPlaneTicket(Integer idPlaneTicket) {
        this.idPlaneTicket = idPlaneTicket;
    }

    public Integer getIdCityOrigin() {
        return idCityOrigin;
    }

    public void setIdCityOrigin(Integer idCityOrigin) {
        this.idCityOrigin = idCityOrigin;
    }

    public Integer getIdCityDestination() {
        return idCityDestination;
    }

    public void setIdCityDestination(Integer idCityDestination) {
        this.idCityDestination = idCityDestination;
    }

    public PlaneTickets getPlaneTicket() {
        return planeTicket;
    }

    public void setPlaneTicket(PlaneTickets planeTicket) {
        this.planeTicket = planeTicket;
    }

    public CTravelCities getCityOrigin() {
        return cityOrigin;
    }

    public void setCityOrigin(CTravelCities cityOrigin) {
        this.cityOrigin = cityOrigin;
    }

    public CTravelCities getCityDestination() {
        return cityDestination;
    }

    public void setCityDestination(CTravelCities cityDestination) {
        this.cityDestination = cityDestination;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFlight != null ? idFlight.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Flights)) {
            return false;
        }
        Flights other = (Flights) object;
        if ((this.idFlight == null && other.idFlight != null) || (this.idFlight != null && !this.idFlight.equals(other.idFlight))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Flights[ idFlight=" + idFlight + " ]";
    }
    
}
