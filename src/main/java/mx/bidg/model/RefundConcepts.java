package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
@Entity
@DynamicUpdate
@Table(name = "REFUND_CONCEPTS")
public class RefundConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REFUND_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConcept;

    @Column(name = "ID_REFUND", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRefund;

    @JoinColumn(name = "ID_REFUND", referencedColumnName = "ID_REFUND")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Refunds refund;

    @Column(name = "ID_VOUCHER_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idVoucherType;

    @JoinColumn(name = "ID_VOUCHER_TYPE", referencedColumnName = "ID_VOUCHER_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CVoucherTypes voucherType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "VOUCHER_FOLIO")
    @JsonView(JsonViews.Root.class)
    private String voucherFolio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "VOUCHER_TOTAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal voucherTotal;

    @Column(name = "ID_BUDGET_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;

    @JoinColumn(name = "ID_BUDGET_CONCEPT", referencedColumnName = "ID_BUDGET_CONCEPT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CBudgetConcepts budgetConcept;

    @OneToMany(mappedBy = "refundConcept")
    @JsonView(JsonViews.Embedded.class)
    private List<RefundConceptDocuments> refundConceptDocuments;

    @OneToMany(mappedBy = "refundConcept")
    @JsonView(JsonViews.Embedded.class)
    private List<RefundConceptAmounts> refundConceptAmounts;

    public RefundConcepts() {
    }

    public RefundConcepts(Integer idRefundConcept) {
        this.idRefundConcept = idRefundConcept;
    }

    public RefundConcepts(Refunds refund, CVoucherTypes voucherType, String voucherFolio, BigDecimal voucherTotal, CBudgetConcepts budgetConcept) {
        this.refund = refund;
        this.voucherType = voucherType;
        this.voucherFolio = voucherFolio;
        this.voucherTotal = voucherTotal;
        this.budgetConcept = budgetConcept;
    }

    public Integer getIdRefundConcept() {
        return idRefundConcept;
    }

    public void setIdRefundConcept(Integer idRefundConcept) {
        this.idRefundConcept = idRefundConcept;
    }

    public Integer getIdRefund() {
        return idRefund;
    }

    public void setIdRefund(Integer idRefund) {
        this.idRefund = idRefund;
    }

    public Refunds getRefund() {
        return refund;
    }

    public void setRefund(Refunds refund) {
        this.refund = refund;
    }

    public Integer getIdVoucherType() {
        return idVoucherType;
    }

    public void setIdVoucherType(Integer idVoucherType) {
        this.idVoucherType = idVoucherType;
    }

    public CVoucherTypes getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(CVoucherTypes voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherFolio() {
        return voucherFolio;
    }

    public void setVoucherFolio(String voucherFolio) {
        this.voucherFolio = voucherFolio;
    }

    public BigDecimal getVoucherSubTotal() {
        return voucherTotal.multiply(BigDecimal.valueOf(0.84D));
    }


    public BigDecimal getVoucherTotal() {
        return voucherTotal;
    }

    public void setVoucherTotal(BigDecimal voucherTotal) {
        this.voucherTotal = voucherTotal;
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

    public List<RefundConceptDocuments> getRefundConceptDocuments() {
        return refundConceptDocuments;
    }

    public void setRefundConceptDocuments(List<RefundConceptDocuments> refundConceptDocuments) {
        this.refundConceptDocuments = refundConceptDocuments;
    }

    public List<RefundConceptAmounts> getRefundConceptAmounts() {
        return refundConceptAmounts;
    }

    public void setRefundConceptAmounts(List<RefundConceptAmounts> refundConceptAmounts) {
        this.refundConceptAmounts = refundConceptAmounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundConcepts that = (RefundConcepts) o;

        return idRefundConcept != null ? idRefundConcept.equals(that.idRefundConcept) : that.idRefundConcept == null;

    }

    @Override
    public int hashCode() {
        return idRefundConcept != null ? idRefundConcept.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RefundConcepts{" +
                "idRefundConcept=" + idRefundConcept +
                ", idRefund=" + idRefund +
                ", refund=" + refund +
                ", idVoucherType=" + idVoucherType +
                ", voucherType=" + voucherType +
                ", voucherFolio='" + voucherFolio + '\'' +
                ", voucherTotal=" + voucherTotal +
                ", refundConceptDocuments=" + refundConceptDocuments +
                ", refundConceptAmounts=" + refundConceptAmounts +
                '}';
    }
}
