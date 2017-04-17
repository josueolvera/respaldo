package mx.bidg.model;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.TimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @Size(max = 15)
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
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Size(max = 30)
    @Column(name = "AUTHORIZATION_NUMBER")
    @JsonView (JsonViews.Root.class)
    private String authorizationNumber;

    @Size(max = 15)
    @Column(name = "NUM_FOLIO")
    @JsonView (JsonViews.Root.class)
    private String numFolio;

    @Column(name = "AMOUNT_OF_COVERAGE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountOfCoverage;

    @Column(name = "ID_INSURANCE_PREMIUM", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idInsurancePremium;

    @JoinColumn(name = "ID_INSURANCE_PREMIUM", referencedColumnName = "ID_INSURANCE_PREMIUM")
    @ManyToOne(optional = true)
    @JsonView (JsonViews.Embedded.class)
    private InsurancePremium insurancePremium;


    public SinisterTruckdriver() {
    }

    public SinisterTruckdriver(Integer idSinister) {
        this.idSinister = idSinister;
    }

    public SinisterTruckdriver(Integer idSinister, LocalDate dStartValidity, LocalDate dEndValidity) {
        this.idSinister = idSinister;
        this.dStartValidity = dStartValidity;
        this.dEndValidity = dEndValidity;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public String getNumFolio() {
        return numFolio;
    }

    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public BigDecimal getAmountOfCoverage() {
        return amountOfCoverage;
    }

    public void setAmountOfCoverage(BigDecimal amountOfCoverage) {
        this.amountOfCoverage = amountOfCoverage;
    }

    public Integer getIdInsurancePremium() {
        return idInsurancePremium;
    }

    public void setIdInsurancePremium(Integer idInsurancePremium) {
        this.idInsurancePremium = idInsurancePremium;
    }

    public InsurancePremium getInsurancePremium() {
        return insurancePremium;
    }

    public void setInsurancePremium(InsurancePremium insurancePremium) {
        this.insurancePremium = insurancePremium;
    }

    public DateFormatsPojo getStartDateFormats() {
        if (dStartValidity == null) {
            return null;
        }
        return new DateFormatsPojo(dStartValidity);
    }

    public DateFormatsPojo getEndDateFormats() {
        if (dEndValidity == null) {
            return null;
        }
        return new DateFormatsPojo(dEndValidity);
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
