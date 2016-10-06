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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "PLANE_TICKETS")
public class PlaneTickets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PLANE_TICKET")
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneTicket;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_PLANE_TICKET_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneTicketType;

    @JoinColumn(name = "ID_PLANE_TICKET_TYPE", referencedColumnName = "ID_PLANE_TICKET_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CPlaneTicketsTypes planeTicketType;

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Requests request;

    @OneToMany(mappedBy = "planeTicket")
    @JsonView(JsonViews.Embedded.class)
    private List<Flights> flights;

    @OneToMany(mappedBy = "planeTicket")
    @JsonView(JsonViews.Embedded.class)
    private List<Passengers> passengers;

    public PlaneTickets() {
    }

    public PlaneTickets(Integer idPlaneTicket) {
        this.idPlaneTicket = idPlaneTicket;
    }

    public PlaneTickets(Integer idPlaneTicket, LocalDateTime creationDate) {
        this.idPlaneTicket = idPlaneTicket;
        this.creationDate = creationDate;
    }

    public Integer getIdPlaneTicket() {
        return idPlaneTicket;
    }

    public void setIdPlaneTicket(Integer idPlaneTicket) {
        this.idPlaneTicket = idPlaneTicket;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdPlaneTicketType() {
        return idPlaneTicketType;
    }

    public void setIdPlaneTicketType(Integer idPlaneTicketType) {
        this.idPlaneTicketType = idPlaneTicketType;
    }

    public CPlaneTicketsTypes getPlaneTicketType() {
        return planeTicketType;
    }

    public void setPlaneTicketType(CPlaneTicketsTypes planeTicketType) {
        this.planeTicketType = planeTicketType;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Requests getRequest() {
        return request;
    }

    public boolean getIsOutOfDateRequest() {

        if (request.getApplyingDate() != null) {
            long daysBetween = DAYS.between(LocalDateTime.now().toLocalDate(), request.getApplyingDate().toLocalDate());

            return daysBetween < 5;
        } else {
            return false;
        }
    }

    public void setRequest(Requests request) {
        this.request = request;
    }

    public List<Flights> getFlights() {
        return flights;
    }

    public void setFlights(List<Flights> flights) {
        this.flights = flights;
    }

    public List<Passengers> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passengers> passengers) {
        this.passengers = passengers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaneTicket != null ? idPlaneTicket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaneTickets)) {
            return false;
        }
        PlaneTickets other = (PlaneTickets) object;
        if ((this.idPlaneTicket == null && other.idPlaneTicket != null) || (this.idPlaneTicket != null && !this.idPlaneTicket.equals(other.idPlaneTicket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.PlaneTickets[ idPlaneTicket=" + idPlaneTicket + " ]";
    }
    
}
