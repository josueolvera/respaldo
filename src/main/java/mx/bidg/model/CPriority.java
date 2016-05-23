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
@Table(name = "C_PRIORITY")
public class CPriority implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRIORITY")
    private Integer idPriority;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PRIORITY_NAME")
    private String priorityName;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority",orphanRemoval = true)
    private List<Ticket> tickets;

    public CPriority() {
    }

    public CPriority(Integer idPriority) {
        this.idPriority = idPriority;
    }

    public Integer getIdPriority() {
        return idPriority;
    }

    public void setIdPriority(Integer idPriority) {
        this.idPriority = idPriority;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPriority != null ? idPriority.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPriority)) {
            return false;
        }
        CPriority other = (CPriority) object;
        if ((this.idPriority == null && other.idPriority != null) || (this.idPriority != null && !this.idPriority.equals(other.idPriority))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPriority[ idPriority=" + idPriority + " ]";
    }
    
}
