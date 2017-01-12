package mx.bidg.model;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author PC_YAIR
 */
@Entity
@DynamicUpdate
@Table(name = "SINISTER_TRUCKDRIVER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "_id")
public class SinisterTruckdriver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SINISTER")
    @JsonView(JsonViews.Root.class)
    private Integer idSinister;

    @Column(name = "NUM_LICENSE_PLATE")
    @JsonView(JsonViews.Root.class)
    private Integer numLicensePlate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_START_VALIDITY")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime dStartValidity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_END_VALIDITY")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime dEndValidity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_SINISTER")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime dSinister;

    @Column(name = "H_SINISTER")
    @Temporal(TemporalType.TIME)
    @JsonView(JsonViews.Root.class)
    private LocalTime hSinister;

    @Size(max = 30)
    @Column(name = "LOCATION_SINISTER")
    @JsonView(JsonViews.Root.class)
    private String locationSinister;

    @JoinColumn(name = "ID_TYPE_ASSISTANCE", referencedColumnName = "ID_TYPE_ASSISTANCE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTypeAssistance idTypeAssistance;

    public SinisterTruckdriver() {
    }

    public SinisterTruckdriver(Integer idSinister) {
        this.idSinister = idSinister;
    }

    public SinisterTruckdriver(Integer idSinister, LocalDateTime dStartValidity, LocalDateTime dEndValidity, LocalDateTime dSinister) {
        this.idSinister = idSinister;
        this.dStartValidity = dStartValidity;
        this.dEndValidity = dEndValidity;
        this.dSinister = dSinister;
    }

    public Integer getIdSinister() {
        return idSinister;
    }

    public void setIdSinister(Integer idSinister) {
        this.idSinister = idSinister;
    }

    public Integer getNumLicensePlate() {
        return numLicensePlate;
    }

    public void setNumLicensePlate(Integer numLicensePlate) {
        this.numLicensePlate = numLicensePlate;
    }

    public LocalDateTime getDStartValidity() {
        return dStartValidity;
    }

    public void setDStartValidity(LocalDateTime dStartValidity) {
        this.dStartValidity = dStartValidity;
    }

    public LocalDateTime getDEndValidity() {
        return dEndValidity;
    }

    public void setDEndValidity(LocalDateTime dEndValidity) {
        this.dEndValidity = dEndValidity;
    }

    public LocalDateTime getDSinister() {
        return dSinister;
    }

    public void setDSinister(LocalDateTime dSinister) {
        this.dSinister = dSinister;
    }

    public LocalTime getHSinister() {
        return hSinister;
    }

    public void setHSinister(LocalTime hSinister) {
        this.hSinister = hSinister;
    }

    public String getLocationSinister() {
        return locationSinister;
    }

    public void setLocationSinister(String locationSinister) {
        this.locationSinister = locationSinister;
    }

    public CTypeAssistance getIdTypeAssistance() {
        return idTypeAssistance;
    }

    public void setIdTypeAssistance(CTypeAssistance idTypeAssistance) {
        this.idTypeAssistance = idTypeAssistance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSinister != null ? idSinister.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SinisterTruckdriver)) {
            return false;
        }
        SinisterTruckdriver other = (SinisterTruckdriver) object;
        if ((this.idSinister == null && other.idSinister != null) || (this.idSinister != null && !this.idSinister.equals(other.idSinister))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.dao.SinisterTruckdriver[ idSinister=" + idSinister + " ]";
    }

}
