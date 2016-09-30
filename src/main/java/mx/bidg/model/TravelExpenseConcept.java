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
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "TRAVEL_EXPENSE_CONCEPT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class TravelExpenseConcept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRAVEL_EXPENSE_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpeseConcept;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_TRAVEL_EXPENSE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpense;

    @JoinColumn(name = "ID_TRAVEL_EXPENSE", referencedColumnName = "ID_TRAVEL_EXPENSE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private TravelExpenses travelExpense;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;

    @Column(name = "ID_BUDGET_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;

    @JoinColumn(name = "ID_BUDGET_CONCEPT", referencedColumnName = "ID_BUDGET_CONCEPT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CBudgetConcepts budgetConcept;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo creationDateFormats;

    public TravelExpenseConcept() {
    }

    public TravelExpenseConcept(Integer idTravelExpense) {
        this.idTravelExpense = idTravelExpense;
    }

    public TravelExpenseConcept(Integer idTravelExpense, BigDecimal amount, LocalDateTime creationDate) {
        this.idTravelExpense = idTravelExpense;
        this.amount = amount;
        this.creationDate = creationDate;
    }

    public Integer getIdTravelExpeseConcept() {
        return idTravelExpeseConcept;
    }

    public void setIdTravelExpeseConcept(Integer idTravelExpeseConcept) {
        this.idTravelExpeseConcept = idTravelExpeseConcept;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public Integer getIdTravelExpense() {
        return idTravelExpense;
    }

    public void setIdTravelExpense(Integer idTravelExpense) {
        this.idTravelExpense = idTravelExpense;
    }

    public TravelExpenses getTravelExpense() {
        return travelExpense;
    }

    public void setTravelExpense(TravelExpenses travelExpense) {
        this.travelExpense = travelExpense;
    }

    public Integer getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public CBudgetConcepts getBudgetConcept() {
        return budgetConcept;
    }

    public void setBudgetConcept(CBudgetConcepts budgetConcept) {
        this.budgetConcept = budgetConcept;
    }

    public DateFormatsPojo getCreationDateFormats() {
        this.creationDateFormats = (creationDate == null) ? null : new DateFormatsPojo(creationDate);
        return this.creationDateFormats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelExpenseConcept that = (TravelExpenseConcept) o;

        return idTravelExpeseConcept != null ? idTravelExpeseConcept.equals(that.idTravelExpeseConcept) : that.idTravelExpeseConcept == null;

    }

    @Override
    public int hashCode() {
        return idTravelExpeseConcept != null ? idTravelExpeseConcept.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TravelExpenseConcept{" +
                "idTravelExpeseConcept=" + idTravelExpeseConcept +
                ", amount=" + amount +
                ", creationDate=" + creationDate +
                ", idTravelExpense=" + idTravelExpense +
                ", travelExpense=" + travelExpense +
                ", idCurrency=" + idCurrency +
                ", currency=" + currency +
                ", idBudgetConcept=" + idBudgetConcept +
                ", budgetConcept=" + budgetConcept +
                ", creationDateFormats=" + creationDateFormats +
                '}';
    }
}
