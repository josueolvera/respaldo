package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 21/07/16.
 */
@Entity
@DynamicUpdate
@Table(name = "REFUNDS")
public class Refunds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REFUND")
    @JsonView(JsonViews.Root.class)
    private Integer idRefund;

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Requests request;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REFUND_TOTAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal refundTotal;

    @OneToMany(mappedBy = "refund")
    @JsonView(JsonViews.Embedded.class)
    private List<RefundConcepts> refundConcepts;

    public Refunds() {
    }

    public Refunds(Integer idRefund) {
        this.idRefund = idRefund;
    }

    public Refunds(Integer idRequest, Requests request, LocalDateTime creationDate, BigDecimal refundTotal, List<RefundConcepts> refundConcepts) {
        this.idRequest = idRequest;
        this.request = request;
        this.creationDate = creationDate;
        this.refundTotal = refundTotal;
        this.refundConcepts = refundConcepts;
    }

    public Integer getIdRefund() {
        return idRefund;
    }

    public void setIdRefund(Integer idRefund) {
        this.idRefund = idRefund;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Requests getRequest() {
        return request;
    }

    public void setRequest(Requests request) {
        this.request = request;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getRequestTotal() {
        return refundTotal;
    }

    public BigDecimal getRefundTotal() {
        return refundTotal;
    }

    public void setRefundTotal(BigDecimal refundTotal) {
        this.refundTotal = refundTotal;
    }

    public List<RefundConcepts> getRefundConcepts() {
        return refundConcepts;
    }

    public void setRefundConcepts(List<RefundConcepts> refundConcepts) {
        this.refundConcepts = refundConcepts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Refunds refunds = (Refunds) o;

        if (idRefund != null ? !idRefund.equals(refunds.idRefund) : refunds.idRefund != null) return false;
        if (idRequest != null ? !idRequest.equals(refunds.idRequest) : refunds.idRequest != null) return false;
        if (request != null ? !request.equals(refunds.request) : refunds.request != null) return false;
        if (creationDate != null ? !creationDate.equals(refunds.creationDate) : refunds.creationDate != null)
            return false;
        if (refundTotal != null ? !refundTotal.equals(refunds.refundTotal) : refunds.refundTotal != null)
            return false;
        return refundConcepts != null ? refundConcepts.equals(refunds.refundConcepts) : refunds.refundConcepts == null;

    }

    @Override
    public int hashCode() {
        int result = idRefund != null ? idRefund.hashCode() : 0;
        result = 31 * result + (idRequest != null ? idRequest.hashCode() : 0);
        result = 31 * result + (request != null ? request.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (refundTotal != null ? refundTotal.hashCode() : 0);
        result = 31 * result + (refundConcepts != null ? refundConcepts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Refunds{" +
                "idRefund=" + idRefund +
                ", idRequest=" + idRequest +
                ", request=" + request +
                ", creationDate=" + creationDate +
                ", refundTotal=" + refundTotal +
                ", refundConcepts=" + refundConcepts +
                '}';
    }
}
