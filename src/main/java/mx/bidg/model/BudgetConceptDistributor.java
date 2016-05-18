package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
@Entity
@Table(
        name = "BUDGET_CONCEPT_DISTRIBUTOR",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UNIQUE_CONCEPT_DISTRIBUTOR", columnNames = {"ID_BUDGET_MONTH_CONCEPT", "ID_DISTRIBUTOR"}
                )
        }
)
public class BudgetConceptDistributor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BUDGET_CONCEPT_DISTRIBUTOR")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConceptDistributor;

    @Column(name = "ID_BUDGET_MONTH_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthConcept;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idDistributor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Max(value = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERCENT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal percent;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributor;

    @JoinColumn(name = "ID_BUDGET_MONTH_CONCEPT", referencedColumnName = "ID_BUDGET_MONTH_CONCEPT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private BudgetMonthConcepts budgetMonthConcept;

    public BudgetConceptDistributor() {
    }

    public BudgetConceptDistributor(Integer idBudgetConceptDistributor) {
        this.idBudgetConceptDistributor = idBudgetConceptDistributor;
    }

    public BudgetConceptDistributor(Integer idBudgetConceptDistributor, int idDistributor, BigDecimal amount, BigDecimal percent) {
        this.idBudgetConceptDistributor = idBudgetConceptDistributor;
        this.idDistributor = idDistributor;
        this.amount = amount;
        this.percent = percent;
    }

    public Integer getIdBudgetConceptDistributor() {
        return idBudgetConceptDistributor;
    }

    public void setIdBudgetConceptDistributor(Integer idBudgetConceptDistributor) {
        this.idBudgetConceptDistributor = idBudgetConceptDistributor;
    }

    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
        this.idDistributor = idDistributor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Integer getIdBudgetMonthConcept() {
        return idBudgetMonthConcept;
    }

    public void setIdBudgetMonthConcept(Integer idBudgetMonthConcept) {
        this.idBudgetMonthConcept = idBudgetMonthConcept;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    public BudgetMonthConcepts getBudgetMonthConcept() {
        return budgetMonthConcept;
    }

    public void setBudgetMonthConcept(BudgetMonthConcepts budgetMonthConcept) {
        this.budgetMonthConcept = budgetMonthConcept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetConceptDistributor != null ? idBudgetConceptDistributor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetConceptDistributor)) {
            return false;
        }
        BudgetConceptDistributor other = (BudgetConceptDistributor) object;
        if ((this.idBudgetConceptDistributor == null && other.idBudgetConceptDistributor != null) || (this.idBudgetConceptDistributor != null && !this.idBudgetConceptDistributor.equals(other.idBudgetConceptDistributor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetConceptDistributor[ idBudgetConceptDistributor=" + idBudgetConceptDistributor + " ]";
    }
    
}
