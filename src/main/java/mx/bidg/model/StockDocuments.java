package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Rafael Viveros
 */
@Entity
@DynamicUpdate
@Table(name = "STOCK_DOCUMENTS")
public class StockDocuments implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_STOCK_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idStockDocument;

    @Size(max = 2048)
    @Column(name = "DOCUMENT_URL")
    @JsonView(JsonViews.Root.class)
    private String documentUrl;

    @Size(max = 1024)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Column(name = "ID_DOCUMENT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDocumentType;

    @Column(name = "ID_STOCK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idStock;

    @Column(name = "UPLOADING_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime uploadingDate;

    @Column(name = "CURRENT_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer currentDocument;

    @JoinColumn(name = "ID_DOCUMENT_TYPE", referencedColumnName = "ID_DOCUMENT_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    @JsonProperty("documentType")
    private CStockDocumentsTypes cStockDocumentsTypes;

    @JoinColumn(name = "ID_STOCK", referencedColumnName = "ID_STOCK")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Stocks stock;

    public StockDocuments() {
    }

    public StockDocuments(Integer idStockDocument) {
        this.idStockDocument = idStockDocument;
    }

    public Integer getIdStockDocument() {
        return idStockDocument;
    }

    public void setIdStockDocument(Integer idStockDocument) {
        this.idStockDocument = idStockDocument;
    }

    public Integer getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getCurrentDocument() {
        return currentDocument;
    }

    public void setCurrentDocument(Integer currentDocument) {
        this.currentDocument = currentDocument;
    }

    public LocalDateTime getUploadingDate() {
        return uploadingDate;
    }

    public void setUploadingDate(LocalDateTime uploadingDate) {
        this.uploadingDate = uploadingDate;
    }

    @JsonProperty("documentType")
    public CStockDocumentsTypes getCStockDocumentsTypes() {
        return cStockDocumentsTypes;
    }

    public void setCStockDocumentsTypes(CStockDocumentsTypes cStockDocumentsTypes) {
        this.cStockDocumentsTypes = cStockDocumentsTypes;
    }

    public Stocks getStock() {
        return stock;
    }

    public void setStock(Stocks stocks) {
        this.stock = stocks;
    }

    public DateFormatsPojo getUploadingDateFormats() {
        return (uploadingDate == null) ? null : new DateFormatsPojo(uploadingDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStockDocument != null ? idStockDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockDocuments)) {
            return false;
        }
        StockDocuments other = (StockDocuments) object;
        if ((this.idStockDocument == null && other.idStockDocument != null) || (this.idStockDocument != null && !this.idStockDocument.equals(other.idStockDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.StockDocuments[ idStockDocument=" + idStockDocument + " ]";
    }
    
}
