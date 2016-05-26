/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author gerardo8
 */
@Entity
@Table(name = "C_TICKET_STATUS")
public class CTicketStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final CTicketStatus ABIERTO = new CTicketStatus(1);
    public static final CTicketStatus EN_REVISION = new CTicketStatus(2);
    public static final CTicketStatus ASIGNADO = new CTicketStatus(3);
    public static final CTicketStatus CERRADO = new CTicketStatus(4);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TICKET_STATUS")
    private Integer idTicketStatus;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TICKET_STATUS_NAME")
    private String ticketStatusName;

    public CTicketStatus() {
    }

    public CTicketStatus(Integer idTicketStatus) {
        this.idTicketStatus = idTicketStatus;
    }

    public Integer getIdTicketStatus() {
        return idTicketStatus;
    }

    public void setIdTicketStatus(Integer idTicketStatus) {
        this.idTicketStatus = idTicketStatus;
    }

    public String getTicketStatusName() {
        return ticketStatusName;
    }

    public void setTicketStatusName(String ticketStatusName) {
        this.ticketStatusName = ticketStatusName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTicketStatus != null ? idTicketStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTicketStatus)) {
            return false;
        }
        CTicketStatus other = (CTicketStatus) object;
        if ((this.idTicketStatus == null && other.idTicketStatus != null) || (this.idTicketStatus != null && !this.idTicketStatus.equals(other.idTicketStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTicketStatus[ idTicketStatus=" + idTicketStatus + " ]";
    }
    
}
