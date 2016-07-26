package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
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

    @Column(name = "ID_TRAVEL_EXPENSE_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpenseConcept;

    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "ID_TRAVEL_EXPENSE_CONCEPT", referencedColumnName = "ID_TRAVEL_EXPENSE_CONCEPT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelExpensesConcepts travelExpenseConcept;

    @Column(name = "ID_VOUCHER_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idVoucherType;

    @Basic(optional = false)
    @NotNull
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
    @Column(name = "VOUCHER_TAX_TOTAL")
    @JsonView(JsonViews.Root.class)
    private Float voucherTaxTotal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "VOUCHER_TOTAL")
    @JsonView(JsonViews.Root.class)
    private Float voucherTotal;

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

    public RefundConcepts(Integer idRefund, Refunds refund, Integer idTravelExpenseConcept, CTravelExpensesConcepts travelExpenseConcept, Integer idVoucherType, CVoucherTypes voucherType, String voucherFolio, Float voucherTaxTotal, Float voucherTotal, List<RefundConceptDocuments> refundConceptDocuments, List<RefundConceptAmounts> refundConceptAmounts) {
        this.idRefund = idRefund;
        this.refund = refund;
        this.idTravelExpenseConcept = idTravelExpenseConcept;
        this.travelExpenseConcept = travelExpenseConcept;
        this.idVoucherType = idVoucherType;
        this.voucherType = voucherType;
        this.voucherFolio = voucherFolio;
        this.voucherTaxTotal = voucherTaxTotal;
        this.voucherTotal = voucherTotal;
        this.refundConceptDocuments = refundConceptDocuments;
        this.refundConceptAmounts = refundConceptAmounts;
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

    public Integer getIdTravelExpenseConcept() {
        return idTravelExpenseConcept;
    }

    public void setIdTravelExpenseConcept(Integer idTravelExpenseConcept) {
        this.idTravelExpenseConcept = idTravelExpenseConcept;
    }

    public CTravelExpensesConcepts getTravelExpenseConcept() {
        return travelExpenseConcept;
    }

    public void setTravelExpenseConcept(CTravelExpensesConcepts travelExpenseConcept) {
        this.travelExpenseConcept = travelExpenseConcept;
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

    public Float getVoucherTaxTotal() {
        return voucherTaxTotal;
    }

    public void setVoucherTaxTotal(Float voucherTaxTotal) {
        this.voucherTaxTotal = voucherTaxTotal;
    }

    public Float getVoucherTotal() {
        return voucherTotal;
    }

    public void setVoucherTotal(Float voucherTotal) {
        this.voucherTotal = voucherTotal;
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

        if (idRefundConcept != null ? !idRefundConcept.equals(that.idRefundConcept) : that.idRefundConcept != null)
            return false;
        if (idRefund != null ? !idRefund.equals(that.idRefund) : that.idRefund != null) return false;
        if (refund != null ? !refund.equals(that.refund) : that.refund != null) return false;
        if (idTravelExpenseConcept != null ? !idTravelExpenseConcept.equals(that.idTravelExpenseConcept) : that.idTravelExpenseConcept != null)
            return false;
        if (travelExpenseConcept != null ? !travelExpenseConcept.equals(that.travelExpenseConcept) : that.travelExpenseConcept != null)
            return false;
        if (idVoucherType != null ? !idVoucherType.equals(that.idVoucherType) : that.idVoucherType != null)
            return false;
        if (voucherType != null ? !voucherType.equals(that.voucherType) : that.voucherType != null) return false;
        if (voucherFolio != null ? !voucherFolio.equals(that.voucherFolio) : that.voucherFolio != null) return false;
        if (voucherTaxTotal != null ? !voucherTaxTotal.equals(that.voucherTaxTotal) : that.voucherTaxTotal != null)
            return false;
        if (voucherTotal != null ? !voucherTotal.equals(that.voucherTotal) : that.voucherTotal != null) return false;
        if (refundConceptDocuments != null ? !refundConceptDocuments.equals(that.refundConceptDocuments) : that.refundConceptDocuments != null)
            return false;
        return refundConceptAmounts != null ? refundConceptAmounts.equals(that.refundConceptAmounts) : that.refundConceptAmounts == null;

    }

    @Override
    public int hashCode() {
        int result = idRefundConcept != null ? idRefundConcept.hashCode() : 0;
        result = 31 * result + (idRefund != null ? idRefund.hashCode() : 0);
        result = 31 * result + (refund != null ? refund.hashCode() : 0);
        result = 31 * result + (idTravelExpenseConcept != null ? idTravelExpenseConcept.hashCode() : 0);
        result = 31 * result + (travelExpenseConcept != null ? travelExpenseConcept.hashCode() : 0);
        result = 31 * result + (idVoucherType != null ? idVoucherType.hashCode() : 0);
        result = 31 * result + (voucherType != null ? voucherType.hashCode() : 0);
        result = 31 * result + (voucherFolio != null ? voucherFolio.hashCode() : 0);
        result = 31 * result + (voucherTaxTotal != null ? voucherTaxTotal.hashCode() : 0);
        result = 31 * result + (voucherTotal != null ? voucherTotal.hashCode() : 0);
        result = 31 * result + (refundConceptDocuments != null ? refundConceptDocuments.hashCode() : 0);
        result = 31 * result + (refundConceptAmounts != null ? refundConceptAmounts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RefundConcepts{" +
                "idRefundConcept=" + idRefundConcept +
                ", idRefund=" + idRefund +
                ", refund=" + refund +
                ", idTravelExpenseConcept=" + idTravelExpenseConcept +
                ", travelExpenseConcept=" + travelExpenseConcept +
                ", idVoucherType=" + idVoucherType +
                ", voucherType=" + voucherType +
                ", voucherFolio='" + voucherFolio + '\'' +
                ", voucherTaxTotal=" + voucherTaxTotal +
                ", voucherTotal=" + voucherTotal +
                ", refundConceptDocuments=" + refundConceptDocuments +
                ", refundConceptAmounts=" + refundConceptAmounts +
                '}';
    }
}
