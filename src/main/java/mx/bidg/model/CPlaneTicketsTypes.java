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
@DynamicUpdate
@Table(name = "C_PLANE_TICKETS_TYPES")
public class CPlaneTicketsTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PLANE_TICKET_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneTicketType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PLANE_TICKET_NAME")
    @JsonView(JsonViews.Root.class)
    private String planeTicketName;

    @OneToMany(mappedBy = "planeTicketType")
    @JsonView(JsonViews.Embedded.class)
    private List<PlaneTickets> planeTicketsList;

    public CPlaneTicketsTypes() {
    }

    public CPlaneTicketsTypes(Integer idPlaneTicketType) {
        this.idPlaneTicketType = idPlaneTicketType;
    }

    public CPlaneTicketsTypes(Integer idPlaneTicketType, String planeTicketName) {
        this.idPlaneTicketType = idPlaneTicketType;
        this.planeTicketName = planeTicketName;
    }

    public Integer getIdPlaneTicketType() {
        return idPlaneTicketType;
    }

    public void setIdPlaneTicketType(Integer idPlaneTicketType) {
        this.idPlaneTicketType = idPlaneTicketType;
    }

    public String getPlaneTicketName() {
        return planeTicketName;
    }

    public void setPlaneTicketName(String planeTicketName) {
        this.planeTicketName = planeTicketName;
    }

    public List<PlaneTickets> getPlaneTicketsList() {
        return planeTicketsList;
    }

    public void setPlaneTicketsList(List<PlaneTickets> planeTicketsList) {
        this.planeTicketsList = planeTicketsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaneTicketType != null ? idPlaneTicketType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPlaneTicketsTypes)) {
            return false;
        }
        CPlaneTicketsTypes other = (CPlaneTicketsTypes) object;
        if ((this.idPlaneTicketType == null && other.idPlaneTicketType != null) || (this.idPlaneTicketType != null && !this.idPlaneTicketType.equals(other.idPlaneTicketType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPlaneTicketsTypes[ idPlaneTicketType=" + idPlaneTicketType + " ]";
    }
    
}
