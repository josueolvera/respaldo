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
 * Created by gerardo8 on 26/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "CHECKS_BILLS_DOCUMENTS")
public class ChecksBillsDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CHECK_BILL_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBillDocument;

    @Column(name = "ID_CHECK_BILL", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBill;

    @JoinColumn(name = "ID_CHECK_BILL", referencedColumnName = "ID_CHECK_BILL")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private ChecksBills checkBill;

    @Column(name = "ID_CHECK_BILL_DOCUMENT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBillDocumentType;

    @JoinColumn(name = "ID_CHECK_BILL_DOCUMENT_TYPE", referencedColumnName = "ID_CHECK_BILL_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CChecksBillsDocumentsTypes checkBillDocumentType;

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

    public ChecksBillsDocuments() {
    }

    public ChecksBillsDocuments(ChecksBills checkBill, CChecksBillsDocumentsTypes checkBillDocumentType, String documentName, String documentUrl, LocalDateTime uploadingDate) {
        this.checkBill = checkBill;
        this.checkBillDocumentType = checkBillDocumentType;
        this.documentName = documentName;
        this.documentUrl = documentUrl;
        this.uploadingDate = uploadingDate;
    }

    public Integer getIdCheckBillDocument() {
        return idCheckBillDocument;
    }

    public void setIdCheckBillDocument(Integer idCheckBillDocument) {
        this.idCheckBillDocument = idCheckBillDocument;
    }

    public Integer getIdCheckBill() {
        return idCheckBill;
    }

    public void setIdCheckBill(Integer idCheckBill) {
        this.idCheckBill = idCheckBill;
    }

    public ChecksBills getCheckBill() {
        return checkBill;
    }

    public void setCheckBill(ChecksBills checkBill) {
        this.checkBill = checkBill;
    }

    public Integer getIdCheckBillDocumentType() {
        return idCheckBillDocumentType;
    }

    public void setIdCheckBillDocumentType(Integer idCheckBillDocumentType) {
        this.idCheckBillDocumentType = idCheckBillDocumentType;
    }

    public CChecksBillsDocumentsTypes getCheckBillDocumentType() {
        return checkBillDocumentType;
    }

    public void setCheckBillDocumentType(CChecksBillsDocumentsTypes checkBillDocumentType) {
        this.checkBillDocumentType = checkBillDocumentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecksBillsDocuments that = (ChecksBillsDocuments) o;

        return idCheckBillDocument != null ? idCheckBillDocument.equals(that.idCheckBillDocument) : that.idCheckBillDocument == null;

    }

    @Override
    public int hashCode() {
        return idCheckBillDocument != null ? idCheckBillDocument.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ChecksBillsDocuments{" +
                "idCheckBillDocument=" + idCheckBillDocument +
                ", idCheckBill=" + idCheckBill +
                ", checkBill=" + checkBill +
                ", idCheckBillDocumentType=" + idCheckBillDocumentType +
                ", checkBillDocumentType=" + checkBillDocumentType +
                ", documentName='" + documentName + '\'' +
                ", documentUrl='" + documentUrl + '\'' +
                ", uploadingDate=" + uploadingDate +
                '}';
    }
}
