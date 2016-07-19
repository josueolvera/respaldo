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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "C_PLANE_SEATS_TYPES")
public class CPlaneSeatsTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final CPlaneTicketsTypes VENTANA = new CPlaneTicketsTypes(1);
    public static final CPlaneTicketsTypes PASILLO = new CPlaneTicketsTypes(2);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PLANE_SEAT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneSeatType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PLANE_SEAT_TYPE")
    @JsonView(JsonViews.Root.class)
    private String planeSeatType;

    public CPlaneSeatsTypes() {
    }

    public CPlaneSeatsTypes(Integer idPlaneSeatType) {
        this.idPlaneSeatType = idPlaneSeatType;
    }

    public CPlaneSeatsTypes(Integer idPlaneSeatType, String planeSeatType) {
        this.idPlaneSeatType = idPlaneSeatType;
        this.planeSeatType = planeSeatType;
    }

    public Integer getIdPlaneSeatType() {
        return idPlaneSeatType;
    }

    public void setIdPlaneSeatType(Integer idPlaneSeatType) {
        this.idPlaneSeatType = idPlaneSeatType;
    }

    public String getPlaneSeatType() {
        return planeSeatType;
    }

    public void setPlaneSeatType(String planeSeatType) {
        this.planeSeatType = planeSeatType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaneSeatType != null ? idPlaneSeatType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPlaneSeatsTypes)) {
            return false;
        }
        CPlaneSeatsTypes other = (CPlaneSeatsTypes) object;
        if ((this.idPlaneSeatType == null && other.idPlaneSeatType != null) || (this.idPlaneSeatType != null && !this.idPlaneSeatType.equals(other.idPlaneSeatType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPlaneSeatsTypes[ idPlaneSeatType=" + idPlaneSeatType + " ]";
    }
    
}
