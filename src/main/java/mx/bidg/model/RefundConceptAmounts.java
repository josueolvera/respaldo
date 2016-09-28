package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gerardo8 on 22/07/16.
 */
@Entity
@DynamicUpdate
@Table(name = "REFUND_CONCEPT_AMOUNTS")
public class RefundConceptAmounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REFUND_CONCEPT_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConceptAmount;

    @Column(name = "ID_REFUND_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConcept;

    @JoinColumn(name = "ID_REFUND_CONCEPT", referencedColumnName = "ID_REFUND_CONCEPT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private RefundConcepts refundConcept;

    @Basic(optional = false)
    @NotNull
    @Size(max = 15)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    public RefundConceptAmounts() {
    }

    public RefundConceptAmounts(Integer idVoucherConcept) {
        this.idRefundConcept = idVoucherConcept;
    }

    public RefundConceptAmounts(Integer idRefundConcept, RefundConcepts refundConcept, String description, BigDecimal amount) {
        this.idRefundConcept = idRefundConcept;
        this.refundConcept = refundConcept;
        this.description = description;
        this.amount = amount;
    }

    public Integer getIdRefundConceptAmount() {
        return idRefundConceptAmount;
    }

    public void setIdRefundConceptAmount(Integer idRefundConceptAmount) {
        this.idRefundConceptAmount = idRefundConceptAmount;
    }

    public Integer getIdRefundConcept() {
        return idRefundConcept;
    }

    public void setIdRefundConcept(Integer idRefundConcept) {
        this.idRefundConcept = idRefundConcept;
    }

    public RefundConcepts getRefundConcept() {
        return refundConcept;
    }

    public void setRefundConcept(RefundConcepts refundConcept) {
        this.refundConcept = refundConcept;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundConceptAmounts that = (RefundConceptAmounts) o;

        if (idRefundConceptAmount != null ? !idRefundConceptAmount.equals(that.idRefundConceptAmount) : that.idRefundConceptAmount != null)
            return false;
        if (idRefundConcept != null ? !idRefundConcept.equals(that.idRefundConcept) : that.idRefundConcept != null)
            return false;
        if (refundConcept != null ? !refundConcept.equals(that.refundConcept) : that.refundConcept != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;

    }

    @Override
    public int hashCode() {
        int result = idRefundConceptAmount != null ? idRefundConceptAmount.hashCode() : 0;
        result = 31 * result + (idRefundConcept != null ? idRefundConcept.hashCode() : 0);
        result = 31 * result + (refundConcept != null ? refundConcept.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RefundConceptAmounts{" +
                "idRefundConceptAmount=" + idRefundConceptAmount +
                ", idRefundConcept=" + idRefundConcept +
                ", refundConcept=" + refundConcept +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
