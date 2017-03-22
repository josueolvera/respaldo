package mx.bidg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.TimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author PC_YAIR
 */
@Entity
@DynamicUpdate
@Table(name = "POLICY_TRUCKDRIVER_MONTHLY")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")

public class PolicyTruckdriverMonthly implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRUCKDRIVER_MONTHLY")
    @JsonView (JsonViews.Root.class)
    private Integer idTruckdriverMonthly;

    @Size(max = 15)
    @Column(name = "NUM_LICENSE_PLATE")
    @JsonView (JsonViews.Root.class)
    private String numLicensePlate;

    @Size(max = 15)
    @Column(name = "NUM_FOLIO")
    @JsonView (JsonViews.Root.class)
    private String numFolio;

    @Column(name = "H_CONTRACTING")
    @JsonView (JsonViews.Root.class)
    @Convert(converter = TimeConverter.class)
    private LocalTime hContracting;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_START_VALIDITY")
    @JsonView (JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate dStartValidity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "D_END_VALIDITY")
    @JsonView (JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate dEndValidity;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INSURANCE_AMOUNT")
    @JsonView (JsonViews.Root.class)
    private BigDecimal insuranceAmount;

    @Size(max = 40)
    @Column(name = "NAME_CCONDUCTOR")
    @JsonView (JsonViews.Root.class)
    private String nameCconductor;

    @Column(name = "DRIVER_AGE")
    @JsonView (JsonViews.Root.class)
    private Integer driverAge;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 25)
    @Column(name = "EMAIL")
    @JsonView (JsonViews.Root.class)
    private String email;

    @Size(max = 40)
    @Column(name = "NAME_BENEFICIARY")
    @JsonView (JsonViews.Root.class)
    private String nameBeneficiary;

    @Column(name = "BENEFICIARY_AGE")
    @JsonView (JsonViews.Root.class)
    private Integer beneficiaryAge;

    @Size(max = 5)
    @Column(name = "DAYS")
    @JsonView (JsonViews.Root.class)
    private String days;

    @Basic(optional = false)
    @Column(name = "CREATION_DATE", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Size(max = 30)
    @Column(name = "PAGO_ID")
    @JsonView (JsonViews.Root.class)
    private String pagoId;

    @Size(max = 30)
    @Column(name = "AUTHORIZATION_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String authorizationNumber;

    @Column(name = "ID_INSURANCE_PREMIUM", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idInsurancePremium;

    @JoinColumn(name = "ID_INSURANCE_PREMIUM", referencedColumnName = "ID_INSURANCE_PREMIUM")
    @ManyToOne
    @JsonView (JsonViews.Embedded.class)
    private InsurancePremium insurancePremium;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private BigDecimal subtotal;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private BigDecimal iva;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private BigDecimal total;

    public PolicyTruckdriverMonthly() {
    }

    public PolicyTruckdriverMonthly(Integer idTruckdriverMonthly) {
        this.idTruckdriverMonthly = idTruckdriverMonthly;
    }

    public PolicyTruckdriverMonthly(Integer idTruckdriverMonthly, LocalDate dStartValidity, LocalDate dEndValidity) {
        this.idTruckdriverMonthly = idTruckdriverMonthly;
        this.dStartValidity = dStartValidity;
        this.dEndValidity = dEndValidity;
    }

    public Integer getIdTruckdriverMonthly() {
        return idTruckdriverMonthly;
    }

    public void setIdTruckdriverMonthly(Integer idTruckdriverMonthly) {
        this.idTruckdriverMonthly = idTruckdriverMonthly;
    }

    public String getNumLicensePlate() {
        return numLicensePlate;
    }

    public void setNumLicensePlate(String numLicensePlate) {
        this.numLicensePlate = numLicensePlate;
    }

    public String getNumFolio() {
        return numFolio;
    }

    public void setNumFolio(String numFolio) {
        this.numFolio = numFolio;
    }

    public LocalTime gethContracting() {
        return hContracting;
    }

    public void sethContracting(LocalTime hContracting) {
        this.hContracting = hContracting;
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

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getNameCconductor() {
        return nameCconductor;
    }

    public void setNameCconductor(String nameCconductor) {
        this.nameCconductor = nameCconductor;
    }

    public Integer getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(Integer driverAge) {
        this.driverAge = driverAge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameBeneficiary() {
        return nameBeneficiary;
    }

    public void setNameBeneficiary(String nameBeneficiary) {
        this.nameBeneficiary = nameBeneficiary;
    }

    public Integer getBeneficiaryAge() {
        return beneficiaryAge;
    }

    public void setBeneficiaryAge(Integer beneficiaryAge) {
        this.beneficiaryAge = beneficiaryAge;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getPagoId() {
        return pagoId;
    }

    public void setPagoId(String pagoId) {
        this.pagoId = pagoId;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        hash += (idTruckdriverMonthly != null ? idTruckdriverMonthly.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolicyTruckdriverMonthly that = (PolicyTruckdriverMonthly) o;

        if (!idTruckdriverMonthly.equals(that.idTruckdriverMonthly)) return false;
        if (!numLicensePlate.equals(that.numLicensePlate)) return false;
        if (!numFolio.equals(that.numFolio)) return false;
        if (!hContracting.equals(that.hContracting)) return false;
        if (!dStartValidity.equals(that.dStartValidity)) return false;
        if (!dEndValidity.equals(that.dEndValidity)) return false;
        if (!insuranceAmount.equals(that.insuranceAmount)) return false;
        if (!nameCconductor.equals(that.nameCconductor)) return false;
        if (!driverAge.equals(that.driverAge)) return false;
        if (!email.equals(that.email)) return false;
        if (!nameBeneficiary.equals(that.nameBeneficiary)) return false;
        if (!beneficiaryAge.equals(that.beneficiaryAge)) return false;
        return creationDate.equals(that.creationDate);

    }

    @Override
    public String toString() {
        return "mx.bidg.dao.PolicyTruckdriver[ idTruckdriverMonthly=" + idTruckdriverMonthly + " ]";
    }

}
