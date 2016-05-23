/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

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
@Table(name = "TICKET")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TICKET")
    private Integer idTicket;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "FOLIO")
    private String folio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CORREO")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION_PROBLEMA")
    private String descripcionProblema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime fechaInicio;
    @Column(name = "FECHA_FINAL")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime fechaFinal;
    @JoinColumn(name = "ID_INCIDENCE", referencedColumnName = "ID_INCIDENCE")
    @ManyToOne(optional = false)
    private CIncidence incidence;
    @JoinColumn(name = "ID_PRIORITY", referencedColumnName = "ID_PRIORITY")
    @ManyToOne(optional = false)
    private CPriority priority;
    @JoinColumn(name = "ID_TICKET_STATUS", referencedColumnName = "ID_TICKET_STATUS")
    @ManyToOne(optional = false)
    private CTicketStatus ticketStatus;

    public Ticket() {
    }

    public Ticket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Ticket(String folio, String correo, String descripcionProblema, LocalDateTime fechaInicio, LocalDateTime fechaFinal, CIncidence incidence, CPriority priority, CTicketStatus ticketStatus) {
        this.folio = folio;
        this.correo = correo;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
