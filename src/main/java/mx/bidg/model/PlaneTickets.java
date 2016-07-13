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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @Column(name = "ID_PLANE_TICKET_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneTicketType;

    @JoinColumn(name = "ID_PLANE_TICKET_TYPE", referencedColumnName = "ID_PLANE_TICKET_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CPlaneTicketsTypes planeTicketType;

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

    public PlaneTickets(Integer idPlaneTicket, Date creationDate) {
        this.idPlaneTicket = idPlaneTicket;
        this.creationDate = creationDate;
    }

    public Integer getIdPlaneTicket() {
        return idPlaneTicket;
    }

    public void setIdPlaneTicket(Integer idPlaneTicket) {
        this.idPlaneTicket = idPlaneTicket;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CPlaneTicketsTypes getPlaneTicketType() {
        return planeTicketType;
    }

    public void setPlaneTicketType(CPlaneTicketsTypes planeTicketType) {
        this.planeTicketType = planeTicketType;
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
