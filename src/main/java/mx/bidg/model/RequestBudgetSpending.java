/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author desarrollador
 */
@Entity
@DynamicUpdate
@Table(name = "REQUEST_BUDGET_SPENDING")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestBudgetSpending implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_BUDGET_SPENDING")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestBudgetSpending;

    @Column(name = "ID_DISTRIBUTOR_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorCostCenter;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "JANUARY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryAmount;

    @Column(name = "JANUARY_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januarySpended;

    @Column(name = "FEBRUARY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryAmount;

    @Column(name = "FEBRUARY_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februarySpended;

    @Column(name = "MARCH_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchAmount;

    @Column(name = "MARCH_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchSpended;

    @Column(name = "APRIL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilAmount;

    @Column(name = "APRIL_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilSpended;

    @Column(name = "MAY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayAmount;

    @Column(name = "MAY_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal maySpended;

    @Column(name = "JUNE_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneAmount;

    @Column(name = "JUNE_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneSpended;

    @Column(name = "JULY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyAmount;

    @Column(name = "JULY_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julySpended;

    @Column(name = "AUGUST_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustAmount;

    @Column(name = "AUGUST_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustSpended;

    @Column(name = "SEPTEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberAmount;

    @Column(name = "SEPTEMBER_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberSpended;

    @Column(name = "OCTOBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberAmount;

    @Column(name = "OCTOBER_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberSpended;

    @Column(name = "NOVEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberAmount;

    @Column(name = "NOVEMBER_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberSpended;

    @Column(name = "DECEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberAmount;

    @Column(name = "DECEMBER_SPENDED")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberSpended;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private int year;

    @JoinColumn(name = "ID_DISTRIBUTOR_COST_CENTER", referencedColumnName = "ID_DISTRIBUTOR_COST_CENTER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private DistributorCostCenter distributorCostCenter;


    public RequestBudgetSpending() {
    }

    public RequestBudgetSpending(Integer idRequestBudgetSpending) {
        this.idRequestBudgetSpending = idRequestBudgetSpending;
    }

    public RequestBudgetSpending(Integer idRequestBudgetSpending, LocalDateTime creationDate) {
        this.idRequestBudgetSpending = idRequestBudgetSpending;
        this.creationDate = creationDate;
    }

    public Integer getIdRequestBudgetSpending() {
        return idRequestBudgetSpending;
    }

    public void setIdRequestBudgetSpending(Integer idRequestBudgetSpending) {
        this.idRequestBudgetSpending = idRequestBudgetSpending;
    }

    public BigDecimal getJanuaryAmount() {
        return januaryAmount;
    }

    public void setJanuaryAmount(BigDecimal januaryAmount) {
        this.januaryAmount = januaryAmount;
    }

    public BigDecimal getJanuarySpended() {
        return januarySpended;
    }

    public void setJanuarySpended(BigDecimal januarySpended) {
        this.januarySpended = januarySpended;
    }

    public BigDecimal getFebruaryAmount() {
        return februaryAmount;
    }

    public void setFebruaryAmount(BigDecimal februaryAmount) {
        this.februaryAmount = februaryAmount;
    }

    public BigDecimal getFebruarySpended() {
        return februarySpended;
    }

    public void setFebruarySpended(BigDecimal februarySpended) {
        this.februarySpended = februarySpended;
    }

    public BigDecimal getMarchAmount() {
        return marchAmount;
    }

    public void setMarchAmount(BigDecimal marchAmount) {
        this.marchAmount = marchAmount;
    }

    public BigDecimal getMarchSpended() {
        return marchSpended;
    }

    public void setMarchSpended(BigDecimal marchSpended) {
        this.marchSpended = marchSpended;
    }

    public BigDecimal getAprilAmount() {
        return aprilAmount;
    }

    public void setAprilAmount(BigDecimal aprilAmount) {
        this.aprilAmount = aprilAmount;
    }

    public BigDecimal getAprilSpended() {
        return aprilSpended;
    }

    public void setAprilSpended(BigDecimal aprilSpended) {
        this.aprilSpended = aprilSpended;
    }

    public BigDecimal getMayAmount() {
        return mayAmount;
    }

    public void setMayAmount(BigDecimal mayAmount) {
        this.mayAmount = mayAmount;
    }

    public BigDecimal getMaySpended() {
        return maySpended;
    }

    public void setMaySpended(BigDecimal maySpended) {
        this.maySpended = maySpended;
    }

    public BigDecimal getJuneAmount() {
        return juneAmount;
    }

    public void setJuneAmount(BigDecimal juneAmount) {
        this.juneAmount = juneAmount;
    }

    public BigDecimal getJuneSpended() {
        return juneSpended;
    }

    public void setJuneSpended(BigDecimal juneSpended) {
        this.juneSpended = juneSpended;
    }

    public BigDecimal getJulyAmount() {
        return julyAmount;
    }

    public void setJulyAmount(BigDecimal julyAmount) {
        this.julyAmount = julyAmount;
    }

    public BigDecimal getJulySpended() {
        return julySpended;
    }

    public void setJulySpended(BigDecimal julySpended) {
        this.julySpended = julySpended;
    }

    public BigDecimal getAugustAmount() {
        return augustAmount;
    }

    public void setAugustAmount(BigDecimal augustAmount) {
        this.augustAmount = augustAmount;
    }

    public BigDecimal getAugustSpended() {
        return augustSpended;
    }

    public void setAugustSpended(BigDecimal augustSpended) {
        this.augustSpended = augustSpended;
    }

    public BigDecimal getSeptemberAmount() {
        return septemberAmount;
    }

    public void setSeptemberAmount(BigDecimal septemberAmount) {
        this.septemberAmount = septemberAmount;
    }

    public BigDecimal getSeptemberSpended() {
        return septemberSpended;
    }

    public void setSeptemberSpended(BigDecimal septemberSpended) {
        this.septemberSpended = septemberSpended;
    }

    public BigDecimal getOctoberAmount() {
        return octoberAmount;
    }

    public void setOctoberAmount(BigDecimal octoberAmount) {
        this.octoberAmount = octoberAmount;
    }

    public BigDecimal getOctoberSpended() {
        return octoberSpended;
    }

    public void setOctoberSpended(BigDecimal octoberSpended) {
        this.octoberSpended = octoberSpended;
    }

    public BigDecimal getNovemberAmount() {
        return novemberAmount;
    }

    public void setNovemberAmount(BigDecimal novemberAmount) {
        this.novemberAmount = novemberAmount;
    }

    public BigDecimal getNovemberSpended() {
        return novemberSpended;
    }

    public void setNovemberSpended(BigDecimal novemberSpended) {
        this.novemberSpended = novemberSpended;
    }

    public BigDecimal getDecemberAmount() {
        return decemberAmount;
    }

    public void setDecemberAmount(BigDecimal decemberAmount) {
        this.decemberAmount = decemberAmount;
    }

    public BigDecimal getDecemberSpended() {
        return decemberSpended;
    }

    public void setDecemberSpended(BigDecimal decemberSpended) {
        this.decemberSpended = decemberSpended;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public DistributorCostCenter getDistributorCostCenter() {
        return distributorCostCenter;
    }

    public void setDistributorCostCenter(DistributorCostCenter distributorCostCenter) {
        this.distributorCostCenter = distributorCostCenter;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestBudgetSpending != null ? idRequestBudgetSpending.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestBudgetSpending)) {
            return false;
        }
        RequestBudgetSpending other = (RequestBudgetSpending) object;
        if ((this.idRequestBudgetSpending == null && other.idRequestBudgetSpending != null) || (this.idRequestBudgetSpending != null && !this.idRequestBudgetSpending.equals(other.idRequestBudgetSpending))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RequestBudgetSpending[ idRequestBudgetSpending=" + idRequestBudgetSpending + " ]";
    }
    
}
