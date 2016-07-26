package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by gerardo8 on 21/07/16.
 */
@Entity
@DynamicUpdate
@Table(name = "REFUND_CONCEPT_DOCUMENTS")
public class RefundConceptDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REFUND_CONCEPT_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConceptDocument;

    @Column(name = "ID_REFUND_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConcept;

    @JoinColumn(name = "ID_REFUND_CONCEPT", referencedColumnName = "ID_REFUND_CONCEPT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private RefundConcepts refundConcept;

    @Column(name = "ID_REFUND_CONCEPT_DOCUMENT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRefundConceptDocumentType;

    @JoinColumn(name = "ID_REFUND_CONCEPT_DOCUMENT_TYPE", referencedColumnName = "ID_REFUND_CONCEPT_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRefundConceptDocumentsTypes refundConceptDocumentType;

    @Size(max = 1024)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Size(max = 2048)
    @Column(name = "DOCUMENT_URL")
    @JsonView(JsonViews.Root.class)
    private String documentUrl;

    @Column(name = "UPLOADING_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime uploadingDate;

    @Column(name = "CURRENT_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer currentDocument;

    public RefundConceptDocuments() {
    }

    public RefundConceptDocuments(Integer idRefundConceptDocument) {
        this.idRefundConceptDocument = idRefundConceptDocument;
    }

    public RefundConceptDocuments(Integer idRefundConcept, RefundConcepts refundConcept, Integer idRefundConceptDocumentType, CRefundConceptDocumentsTypes refundConceptDocumentType, String documentName, String documentUrl, LocalDateTime uploadingDate, Integer currentDocument) {
        this.idRefundConcept = idRefundConcept;
        this.refundConcept = refundConcept;
        this.idRefundConceptDocumentType = idRefundConceptDocumentType;
        this.refundConceptDocumentType = refundConceptDocumentType;
        this.documentName = documentName;
        this.documentUrl = documentUrl;
        this.uploadingDate = uploadingDate;
        this.currentDocument = currentDocument;
    }

    public Integer getIdRefundConceptDocument() {
        return idRefundConceptDocument;
    }

    public void setIdRefundConceptDocument(Integer idRefundConceptDocument) {
        this.idRefundConceptDocument = idRefundConceptDocument;
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

    public Integer getIdRefundConceptDocumentType() {
        return idRefundConceptDocumentType;
    }

    public void setIdRefundConceptDocumentType(Integer idRefundConceptDocumentType) {
        this.idRefundConceptDocumentType = idRefundConceptDocumentType;
    }

    public CRefundConceptDocumentsTypes getRefundConceptDocumentType() {
        return refundConceptDocumentType;
    }

    public void setRefundConceptDocumentType(CRefundConceptDocumentsTypes refundConceptDocumentType) {
        this.refundConceptDocumentType = refundConceptDocumentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public LocalDateTime getUploadingDate() {
        return uploadingDate;
    }

    public void setUploadingDate(LocalDateTime uploadingDate) {
        this.uploadingDate = uploadingDate;
    }

    public Integer getCurrentDocument() {
        return currentDocument;
    }

    public void setCurrentDocument(Integer currentDocument) {
        this.currentDocument = currentDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundConceptDocuments that = (RefundConceptDocuments) o;

        if (idRefundConceptDocument != null ? !idRefundConceptDocument.equals(that.idRefundConceptDocument) : that.idRefundConceptDocument != null)
            return false;
        if (idRefundConcept != null ? !idRefundConcept.equals(that.idRefundConcept) : that.idRefundConcept != null)
            return false;
        if (refundConcept != null ? !refundConcept.equals(that.refundConcept) : that.refundConcept != null)
            return false;
        if (idRefundConceptDocumentType != null ? !idRefundConceptDocumentType.equals(that.idRefundConceptDocumentType) : that.idRefundConceptDocumentType != null)
            return false;
        if (refundConceptDocumentType != null ? !refundConceptDocumentType.equals(that.refundConceptDocumentType) : that.refundConceptDocumentType != null)
            return false;
        if (documentName != null ? !documentName.equals(that.documentName) : that.documentName != null) return false;
        if (documentUrl != null ? !documentUrl.equals(that.documentUrl) : that.documentUrl != null) return false;
        if (uploadingDate != null ? !uploadingDate.equals(that.uploadingDate) : that.uploadingDate != null)
            return false;
        return currentDocument != null ? currentDocument.equals(that.currentDocument) : that.currentDocument == null;

    }

    @Override
    public int hashCode() {
        int result = idRefundConceptDocument != null ? idRefundConceptDocument.hashCode() : 0;
        result = 31 * result + (idRefundConcept != null ? idRefundConcept.hashCode() : 0);
        result = 31 * result + (refundConcept != null ? refundConcept.hashCode() : 0);
        result = 31 * result + (idRefundConceptDocumentType != null ? idRefundConceptDocumentType.hashCode() : 0);
        result = 31 * result + (refundConceptDocumentType != null ? refundConceptDocumentType.hashCode() : 0);
        result = 31 * result + (documentName != null ? documentName.hashCode() : 0);
        result = 31 * result + (documentUrl != null ? documentUrl.hashCode() : 0);
        result = 31 * result + (uploadingDate != null ? uploadingDate.hashCode() : 0);
        result = 31 * result + (currentDocument != null ? currentDocument.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RefundConceptDocuments{" +
                "idRefundConceptDocument=" + idRefundConceptDocument +
                ", idRefundConcept=" + idRefundConcept +
                ", refundConcept=" + refundConcept +
                ", idRefundConceptDocumentType=" + idRefundConceptDocumentType +
                ", refundConceptDocumentType=" + refundConceptDocumentType +
                ", documentName='" + documentName + '\'' +
                ", documentUrl='" + documentUrl + '\'' +
                ", uploadingDate=" + uploadingDate +
                ", currentDocument=" + currentDocument +
                '}';
    }
}
