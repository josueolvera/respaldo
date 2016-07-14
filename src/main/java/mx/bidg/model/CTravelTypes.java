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
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_TRAVEL_TYPES")
public class CTravelTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRAVEL_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idTravelType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TYPE_NAME")
    @JsonView(JsonViews.Root.class)
    private String typeName;

    public CTravelTypes() {
    }

    public CTravelTypes(Integer idTravelType) {
        this.idTravelType = idTravelType;
    }

    public CTravelTypes(Integer idTravelType, String typeName) {
        this.idTravelType = idTravelType;
        this.typeName = typeName;
    }

    public Integer getIdTravelType() {
        return idTravelType;
    }

    public void setIdTravelType(Integer idTravelType) {
        this.idTravelType = idTravelType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTravelType != null ? idTravelType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTravelTypes)) {
            return false;
        }
        CTravelTypes other = (CTravelTypes) object;
        if ((this.idTravelType == null && other.idTravelType != null) || (this.idTravelType != null && !this.idTravelType.equals(other.idTravelType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTravelTypes[ idTravelType=" + idTravelType + " ]";
    }
    
}
