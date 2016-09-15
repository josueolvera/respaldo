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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_YEAR_CONCEPT")
public class BudgetYearConcept implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_YEAR_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetYearConcept;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JANUARY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FEBRUARY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MARCH_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "APRIL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JUNE_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JULY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AUGUST_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SEPTEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OCTOBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NOVEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DECEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalAmount;

    @Column(name = "JANUARY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryExpendedAmount;

    @Column(name = "FEBRUARY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryExpendedAmount;

    @Column(name = "MARCH_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchExpendedAmount;

    @Column(name = "APRIL_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilExpendedAmount;

    @Column(name = "MAY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayExpendedAmount;

    @Column(name = "JUNE_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneExpendedAmount;

    @Column(name = "JULY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyExpendedAmount;

    @Column(name = "AUGUST_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustExpendedAmount;

    @Column(name = "SEPTEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberExpendedAmount;

    @Column(name = "OCTOBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberExpendedAmount;

    @Column(name = "NOVEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberExpendedAmount;

    @Column(name = "DECEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberExpendedAmount;

    @Column(name = "TOTAL_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalExpendedAmount;

    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @JoinColumn(name = "ID_BUDGET_YEAR", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private BudgetYear budgetYear;

    @JoinColumn(name = "ID_BUDGET_CONCEPT", referencedColumnName = "ID_BUDGET_CONCEPT")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CBudgetConcepts budgetConcept;
    
    @Column(name = "ID_BUDGET_YEAR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetYear;

    @Column(name = "ID_BUDGET_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_AUTHORIZED", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean isAuthorized;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public BudgetYearConcept() {
    }

    public BudgetYearConcept(BudgetYear budgetYear, CBudgetConcepts budgetConcept) {
        this.budgetYear = budgetYear;
        this.budgetConcept = budgetConcept;
    }

    public Integer getIdBudgetYearConcept() {
        return idBudgetYearConcept;
    }

    public void setIdBudgetYearConcept(Integer idBudgetYearConcept) {
        this.idBudgetYearConcept = idBudgetYearConcept;
    }

    public BigDecimal getJanuaryAmount() {
        return januaryAmount;
    }

    public void setJanuaryAmount(BigDecimal januaryAmount) {
        this.januaryAmount = januaryAmount;
    }

    public BigDecimal getFebruaryAmount() {
        return februaryAmount;
    }

    public void setFebruaryAmount(BigDecimal februaryAmount) {
        this.februaryAmount = februaryAmount;
    }

    public BigDecimal getMarchAmount() {
        return marchAmount;
    }

    public void setMarchAmount(BigDecimal marchAmount) {
        this.marchAmount = marchAmount;
    }

    public BigDecimal getAprilAmount() {
        return aprilAmount;
    }

    public void setAprilAmount(BigDecimal aprilAmount) {
        this.aprilAmount = aprilAmount;
    }

    public BigDecimal getMayAmount() {
        return mayAmount;
    }

    public void setMayAmount(BigDecimal mayAmount) {
        this.mayAmount = mayAmount;
    }

    public BigDecimal getJuneAmount() {
        return juneAmount;
    }

    public void setJuneAmount(BigDecimal juneAmount) {
        this.juneAmount = juneAmount;
    }

    public BigDecimal getJulyAmount() {
        return julyAmount;
    }

    public void setJulyAmount(BigDecimal julyAmount) {
        this.julyAmount = julyAmount;
    }

    public BigDecimal getAugustAmount() {
        return augustAmount;
    }

    public void setAugustAmount(BigDecimal augustAmount) {
        this.augustAmount = augustAmount;
    }

    public BigDecimal getSeptemberAmount() {
        return septemberAmount;
    }

    public void setSeptemberAmount(BigDecimal septemberAmount) {
        this.septemberAmount = septemberAmount;
    }

    public BigDecimal getOctoberAmount() {
        return octoberAmount;
    }

    public void setOctoberAmount(BigDecimal octoberAmount) {
        this.octoberAmount = octoberAmount;
    }

    public BigDecimal getNovemberAmount() {
        return novemberAmount;
    }

    public void setNovemberAmount(BigDecimal novemberAmount) {
        this.novemberAmount = novemberAmount;
    }

    public BigDecimal getDecemberAmount() {
        return decemberAmount;
    }

    public void setDecemberAmount(BigDecimal decemberAmount) {
        this.decemberAmount = decemberAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getJanuaryExpendedAmount() {
        return januaryExpendedAmount;
    }

    public void setJanuaryExpendedAmount(BigDecimal januaryExpendedAmount) {
        this.januaryExpendedAmount = januaryExpendedAmount;
    }

    public BigDecimal getFebruaryExpendedAmount() {
        return februaryExpendedAmount;
    }

    public void setFebruaryExpendedAmount(BigDecimal februaryExpendedAmount) {
        this.februaryExpendedAmount = februaryExpendedAmount;
    }

    public BigDecimal getMarchExpendedAmount() {
        return marchExpendedAmount;
    }

    public void setMarchExpendedAmount(BigDecimal marchExpendedAmount) {
        this.marchExpendedAmount = marchExpendedAmount;
    }

    public BigDecimal getAprilExpendedAmount() {
        return aprilExpendedAmount;
    }

    public void setAprilExpendedAmount(BigDecimal aprilExpendedAmount) {
        this.aprilExpendedAmount = aprilExpendedAmount;
    }

    public BigDecimal getMayExpendedAmount() {
        return mayExpendedAmount;
    }

    public void setMayExpendedAmount(BigDecimal mayExpendedAmount) {
        this.mayExpendedAmount = mayExpendedAmount;
    }

    public BigDecimal getJuneExpendedAmount() {
        return juneExpendedAmount;
    }

    public void setJuneExpendedAmount(BigDecimal juneExpendedAmount) {
        this.juneExpendedAmount = juneExpendedAmount;
    }

    public BigDecimal getJulyExpendedAmount() {
        return julyExpendedAmount;
    }

    public void setJulyExpendedAmount(BigDecimal julyExpendedAmount) {
        this.julyExpendedAmount = julyExpendedAmount;
    }

    public BigDecimal getAugustExpendedAmount() {
        return augustExpendedAmount;
    }

    public void setAugustExpendedAmount(BigDecimal augustExpendedAmount) {
        this.augustExpendedAmount = augustExpendedAmount;
    }

    public BigDecimal getSeptemberExpendedAmount() {
        return septemberExpendedAmount;
    }

    public void setSeptemberExpendedAmount(BigDecimal septemberExpendedAmount) {
        this.septemberExpendedAmount = septemberExpendedAmount;
    }

    public BigDecimal getOctoberExpendedAmount() {
        return octoberExpendedAmount;
    }

    public void setOctoberExpendedAmount(BigDecimal octoberExpendedAmount) {
        this.octoberExpendedAmount = octoberExpendedAmount;
    }

    public BigDecimal getNovemberExpendedAmount() {
        return novemberExpendedAmount;
    }

    public void setNovemberExpendedAmount(BigDecimal novemberExpendedAmount) {
        this.novemberExpendedAmount = novemberExpendedAmount;
    }

    public BigDecimal getDecemberExpendedAmount() {
        return decemberExpendedAmount;
    }

    public void setDecemberExpendedAmount(BigDecimal decemberExpendedAmount) {
        this.decemberExpendedAmount = decemberExpendedAmount;
    }

    public BigDecimal getTotalExpendedAmount() {
        return totalExpendedAmount;
    }

    public void setTotalExpendedAmount(BigDecimal totalExpendedAmount) {
        this.totalExpendedAmount = totalExpendedAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BudgetYear getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(BudgetYear budgetYear) {
        this.budgetYear = budgetYear;
    }

    public CBudgetConcepts getBudgetConcept() {
        return budgetConcept;
    }

    public void setBudgetConcept(CBudgetConcepts budgetConcept) {
        this.budgetConcept = budgetConcept;
    }

    public Integer getIdBudgetYear() {
        return idBudgetYear;
    }

    public void setIdBudgetYear(Integer idBudgetYear) {
        this.idBudgetYear = idBudgetYear;
    }

    public Integer getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetYearConcept that = (BudgetYearConcept) o;

        if (idBudgetYear != null ? !idBudgetYear.equals(that.idBudgetYear) : that.idBudgetYear != null) return false;
        return idBudgetConcept != null ? idBudgetConcept.equals(that.idBudgetConcept) : that.idBudgetConcept == null;

    }

    @Override
    public int hashCode() {
        int result = idBudgetYear != null ? idBudgetYear.hashCode() : 0;
        result = 31 * result + (idBudgetConcept != null ? idBudgetConcept.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetYearConcept{" +
                "idBudgetYearConcept=" + idBudgetYearConcept +
                ", januaryAmount=" + januaryAmount +
                ", februaryAmount=" + februaryAmount +
                ", marchAmount=" + marchAmount +
                ", aprilAmount=" + aprilAmount +
                ", mayAmount=" + mayAmount +
                ", juneAmount=" + juneAmount +
                ", julyAmount=" + julyAmount +
                ", augustAmount=" + augustAmount +
                ", septemberAmount=" + septemberAmount +
                ", octoberAmount=" + octoberAmount +
                ", novemberAmount=" + novemberAmount +
                ", decemberAmount=" + decemberAmount +
                ", totalAmount=" + totalAmount +
                ", januaryExpendedAmount=" + januaryExpendedAmount +
                ", februaryExpendedAmount=" + februaryExpendedAmount +
                ", marchExpendedAmount=" + marchExpendedAmount +
                ", aprilExpendedAmount=" + aprilExpendedAmount +
                ", mayExpendedAmount=" + mayExpendedAmount +
                ", juneExpendedAmount=" + juneExpendedAmount +
                ", julyExpendedAmount=" + julyExpendedAmount +
                ", augustExpendedAmount=" + augustExpendedAmount +
                ", septemberExpendedAmount=" + septemberExpendedAmount +
                ", octoberExpendedAmount=" + octoberExpendedAmount +
                ", novemberExpendedAmount=" + novemberExpendedAmount +
                ", decemberExpendedAmount=" + decemberExpendedAmount +
                ", totalExpendedAmount=" + totalExpendedAmount +
                ", username='" + username + '\'' +
                ", budgetYear=" + budgetYear +
                ", budgetConcept=" + budgetConcept +
                ", idBudgetYear=" + idBudgetYear +
                ", idBudgetConcept=" + idBudgetConcept +
                ", isAuthorized=" + isAuthorized +
                ", creationDate=" + creationDate +
                '}';
    }
}
