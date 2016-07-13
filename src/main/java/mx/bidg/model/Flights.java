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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date departureDate;

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

    @JoinColumn(name = "ID_CITY_ORIGIN", referencedColumnName = "ID_CITY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CCities cityOrigin;

    @JoinColumn(name = "ID_CITY_DESTINATION", referencedColumnName = "ID_CITY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CCities cityDestination;

    public Flights() {
    }

    public Flights(Integer idFlight) {
        this.idFlight = idFlight;
    }

    public Flights(Integer idFlight, Date departureDate) {
        this.idFlight = idFlight;
        this.departureDate = departureDate;
    }

    public Integer getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Integer idFlight) {
        this.idFlight = idFlight;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
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

    public CCities getCityOrigin() {
        return cityOrigin;
    }

    public void setCityOrigin(CCities cityOrigin) {
        this.cityOrigin = cityOrigin;
    }

    public CCities getCityDestination() {
        return cityDestination;
    }

    public void setCityDestination(CCities cityDestination) {
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
