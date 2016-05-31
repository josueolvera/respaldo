/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "TICKET", uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_TICKET_FOLIO", columnNames = {"FOLIO"})
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TICKET")
    @JsonView(JsonViews.Root.class)
    private Integer idTicket;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @Column(name = "ID_INCIDENCE", updatable = false, insertable = false)
    private Integer idIncidence;

    @Column(name = "ID_PRIORITY", updatable = false, insertable = false)
    private Integer idPriority;

    @Column(name = "ID_TICKET_STATUS", insertable = false, updatable = false)
    private Integer idTicketStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DESCRIPCION_PROBLEMA")
    @JsonView(JsonViews.Root.class)
    private String descripcionProblema;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime fechaInicio;

    @Column(name = "FECHA_FINAL")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime fechaFinal;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", updatable = false)
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users user;

    @JoinColumn(name = "ID_INCIDENCE", referencedColumnName = "ID_INCIDENCE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CIncidence incidence;

    @JoinColumn(name = "ID_PRIORITY", referencedColumnName = "ID_PRIORITY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CPriority priority;

    @JoinColumn(name = "ID_TICKET_STATUS", referencedColumnName = "ID_TICKET_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CTicketStatus ticketStatus;

    public Ticket() {
    }

    public Ticket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Ticket(String folio, String descripcionProblema, LocalDateTime fechaInicio, LocalDateTime fechaFinal, CIncidence incidence, CPriority priority, CTicketStatus ticketStatus) {
        this.folio = folio;
        this.descripcionProblema = descripcionProblema;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.incidence = incidence;
        this.priority = priority;
        this.ticketStatus = ticketStatus;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(Integer idIncidence) {
        this.idIncidence = idIncidence;
    }

    public Integer getIdPriority() {
        return idPriority;
    }

    public void setIdPriority(Integer idPriority) {
        this.idPriority = idPriority;
    }

    public Integer getIdTicketStatus() {
        return idTicketStatus;
    }

    public void setIdTicketStatus(Integer idTicketStatus) {
        this.idTicketStatus = idTicketStatus;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public DateFormatsPojo getFechaFinalFormats() {
        return (fechaFinal == null) ? null : new DateFormatsPojo(fechaFinal);
    }

    public DateFormatsPojo getFechaInicioFormats() {
        return (fechaInicio == null) ? null : new DateFormatsPojo(fechaInicio);
    }

    public CIncidence getIncidence() {
        return incidence;
    }

    public void setIncidence(CIncidence incidence) {
        this.incidence = incidence;
    }

    public CPriority getPriority() {
        return priority;
    }

    public void setPriority(CPriority priority) {
        this.priority = priority;
    }

    public CTicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(CTicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTicket != null ? idTicket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.idTicket == null && other.idTicket != null) || (this.idTicket != null && !this.idTicket.equals(other.idTicket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Ticket[ idTicket=" + idTicket + " ]";
    }
    
}
