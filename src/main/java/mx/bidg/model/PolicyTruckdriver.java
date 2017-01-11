package mx.bidg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC_YAIR
 */
@Entity
@DynamicUpdate
@Table(name = "POLICY_TRUCKDRIVER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")

public class PolicyTruckdriver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRUCKDRIVER")
    @JsonView (JsonViews.Root.class)

    private Integer idTruckdriver;
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
    @Temporal(TemporalType.TIME)

    private Date hContracting;
    @Basic(optional = false)
    @NotNull
    @Column(name = "D_START_VALIDITY")
    @JsonView (JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)

    private Date dStartValidity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "D_END_VALIDITY")
    @JsonView (JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)

    private Date dEndValidity;
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
    @JoinColumn(name = "ID_TYPE_SECURE", referencedColumnName = "ID_TYPE_SECURE")
    @ManyToOne
    @JsonView (JsonViews.Embedded.class)

    private CTypeSecure idTypeSecure;

    public PolicyTruckdriver() {
    }

    public PolicyTruckdriver(Integer idTruckdriver) {
        this.idTruckdriver = idTruckdriver;
    }

    public PolicyTruckdriver(Integer idTruckdriver, Date dStartValidity, Date dEndValidity) {
        this.idTruckdriver = idTruckdriver;
        this.dStartValidity = dStartValidity;
        this.dEndValidity = dEndValidity;
    }

    public Integer getIdTruckdriver() {
        return idTruckdriver;
    }

    public void setIdTruckdriver(Integer idTruckdriver) {
        this.idTruckdriver = idTruckdriver;
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

    public Date getHContracting() {
        return hContracting;
    }

    public void setHContracting(Date hContracting) {
        this.hContracting = hContracting;
    }

    public Date getDStartValidity() {
        return dStartValidity;
    }

    public void setDStartValidity(Date dStartValidity) {
        this.dStartValidity = dStartValidity;
    }

    public Date getDEndValidity() {
        return dEndValidity;
    }

    public void setDEndValidity(Date dEndValidity) {
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

    public CTypeSecure getIdTypeSecure() {
        return idTypeSecure;
    }

    public void setIdTypeSecure(CTypeSecure idTypeSecure) {
        this.idTypeSecure = idTypeSecure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTruckdriver != null ? idTruckdriver.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolicyTruckdriver)) {
            return false;
        }
        PolicyTruckdriver other = (PolicyTruckdriver) object;
        if ((this.idTruckdriver == null && other.idTruckdriver != null) || (this.idTruckdriver != null && !this.idTruckdriver.equals(other.idTruckdriver))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.dao.PolicyTruckdriver[ idTruckdriver=" + idTruckdriver + " ]";
    }

}
