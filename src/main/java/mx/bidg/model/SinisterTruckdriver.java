package mx.bidg.model;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.TimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDate;
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
    private String numLicensePlate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_START_VALIDITY")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate dStartValidity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_END_VALIDITY")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate dEndValidity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_SINISTER")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate dSinister;

    @Column(name = "H_SINISTER")
    @Convert(converter = TimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalTime hSinister;

    @Size(max = 30)
    @Column(name = "LOCATION_SINISTER")
    @JsonView(JsonViews.Root.class)
    private String locationSinister;

    @Column(name = "ID_TYPE_ASSISTANCE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idTypeAssistance;

    @JoinColumn(name = "ID_TYPE_ASSISTANCE", referencedColumnName = "ID_TYPE_ASSISTANCE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTypeAssistance cTypeAssistance;

    @Basic(optional = false)
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Size(max = 30)
    @Column(name = "AUTHORIZATION_NUMBER")
    @JsonView (JsonViews.Root.class)
    private String authorizationNumber;


    public SinisterTruckdriver() {
    }

    public SinisterTruckdriver(Integer idSinister) {
        this.idSinister = idSinister;
    }

    public SinisterTruckdriver(Integer idSinister, LocalDate dStartValidity, LocalDate dEndValidity, LocalDate dSinister) {
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

    public String getNumLicensePlate() {
        return numLicensePlate;
    }

    public void setNumLicensePlate(String numLicensePlate) {
        this.numLicensePlate = numLicensePlate;
    }

    public String getLocationSinister() {
        return locationSinister;
    }

    public void setLocationSinister(String locationSinister) {
        this.locationSinister = locationSinister;
    }

    public int getIdTypeAssistance() {
        return idTypeAssistance;
    }

    public void setIdTypeAssistance(int idTypeAssistance) {
        this.idTypeAssistance = idTypeAssistance;
    }

    public CTypeAssistance getcTypeAssistance() {
        return cTypeAssistance;
    }

    public void setcTypeAssistance(CTypeAssistance cTypeAssistance) {
        this.cTypeAssistance = cTypeAssistance;
    }

    public LocalDate getdStartValidity() {
        return dStartValidity;
    }

    public void setdStartValidity(LocalDate dStartValidity) {
        this.dStartValidity = dStartValidity;
    }

    public LocalDate getdEndValidity() {
        return dEndValidity;
    }

    public void setdEndValidity(LocalDate dEndValidity) {
        this.dEndValidity = dEndValidity;
    }

    public LocalDate getdSinister() {
        return dSinister;
    }

    public void setdSinister(LocalDate dSinister) {
        this.dSinister = dSinister;
    }

    public LocalTime gethSinister() {
        return hSinister;
    }

    public void sethSinister(LocalTime hSinister) {
        this.hSinister = hSinister;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
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
