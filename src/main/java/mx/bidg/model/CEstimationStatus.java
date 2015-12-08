/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_ESTIMATION_STATUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CEstimationStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ESTIMATION_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idEstimationStatus;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ESTIMATION_STATUS")
    @JsonView(JsonViews.Root.class)
    private String estimationStatus;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstimationStatus")
    @JsonView(JsonViews.Embedded.class)
    private List<PriceEstimations> priceEstimationsList;

    public CEstimationStatus() {
    }

    public CEstimationStatus(Integer idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public CEstimationStatus(Integer idEstimationStatus, String estimationStatus, int idAccessLevel) {
        this.idEstimationStatus = idEstimationStatus;
        this.estimationStatus = estimationStatus;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(Integer idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public String getEstimationStatus() {
        return estimationStatus;
    }

    public void setEstimationStatus(String estimationStatus) {
        this.estimationStatus = estimationStatus;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }
    
    public List<PriceEstimations> getPriceEstimationsList() {
        return priceEstimationsList;
    }

    public void setPriceEstimationsList(List<PriceEstimations> priceEstimationsList) {
        this.priceEstimationsList = priceEstimationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstimationStatus != null ? idEstimationStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CEstimationStatus)) {
            return false;
        }
        CEstimationStatus other = (CEstimationStatus) object;
        if ((this.idEstimationStatus == null && other.idEstimationStatus != null) || (this.idEstimationStatus != null && !this.idEstimationStatus.equals(other.idEstimationStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CEstimationStatus[ idEstimationStatus=" + idEstimationStatus + " ]";
    }
    
}
