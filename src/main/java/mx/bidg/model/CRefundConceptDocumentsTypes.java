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
 * Created by gerardo8 on 21/07/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_REFUNDS_CONCEPT_DOCUMENTS_TYPES")
public class CRefundConceptDocumentsTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REFUND_CONCEPT_DOCUMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConceptDocumentType;

    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Column(name = "ID_VOUCHER_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idVoucherType;

    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "ID_VOUCHER_TYPE", referencedColumnName = "ID_VOUCHER_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CVoucherTypes voucherType;

    public CRefundConceptDocumentsTypes() {
    }

    public CRefundConceptDocumentsTypes(Integer idRefundConceptDocumentType) {
        this.idRefundConceptDocumentType = idRefundConceptDocumentType;
    }

    public CRefundConceptDocumentsTypes(String documentName) {
        this.documentName = documentName;
    }

    public Integer getIdRefundConceptDocumentType() {
        return idRefundConceptDocumentType;
    }

    public void setIdRefundConceptDocumentType(Integer idRefundConceptDocumentType) {
        this.idRefundConceptDocumentType = idRefundConceptDocumentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CRefundConceptDocumentsTypes that = (CRefundConceptDocumentsTypes) o;

        if (idRefundConceptDocumentType != null ? !idRefundConceptDocumentType.equals(that.idRefundConceptDocumentType) : that.idRefundConceptDocumentType != null)
            return false;
        if (documentName != null ? !documentName.equals(that.documentName) : that.documentName != null) return false;
        if (idVoucherType != null ? !idVoucherType.equals(that.idVoucherType) : that.idVoucherType != null)
            return false;
        return voucherType != null ? voucherType.equals(that.voucherType) : that.voucherType == null;

    }

    @Override
    public int hashCode() {
        int result = idRefundConceptDocumentType != null ? idRefundConceptDocumentType.hashCode() : 0;
        result = 31 * result + (documentName != null ? documentName.hashCode() : 0);
        result = 31 * result + (idVoucherType != null ? idVoucherType.hashCode() : 0);
        result = 31 * result + (voucherType != null ? voucherType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CRefundConceptDocumentsTypes{" +
                "idRefundConceptDocumentType=" + idRefundConceptDocumentType +
                ", documentName='" + documentName + '\'' +
                ", idVoucherType=" + idVoucherType +
                ", voucherType=" + voucherType +
                '}';
    }
}
