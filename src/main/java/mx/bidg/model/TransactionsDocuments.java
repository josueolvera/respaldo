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
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "TRANSACTIONS_DOCUMENTS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class TransactionsDocuments implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRANSACTION_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idTransactionDocument;

    @Size(max = 2048)
    @Column(name = "DOCUMENT_URL")
    @JsonView(JsonViews.Root.class)
    private String documentUrl;

    @Size(max = 1024)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "UPLOADING_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime uploadingDate;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Column(name = "ID_TRANSACTION_DOCUMENT_TYPE", insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTransactionDocumentType;

    @Column(name="ID_TRANSACTION", insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTransaction;

    @JoinColumn(name = "ID_TRANSACTION_DOCUMENT_TYPE", referencedColumnName = "ID_TRANSACTION_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTransactionsDocumentsTypes transactionDocumentType;

    @JoinColumn(name = "ID_TRANSACTION", referencedColumnName = "ID_TRANSACTION")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Transactions transaction;

    public TransactionsDocuments() {
    }

    public TransactionsDocuments(Integer idTransactionDocument) {
        this.idTransactionDocument = idTransactionDocument;
    }

    public TransactionsDocuments(Integer idTransactionDocument, LocalDateTime uploadingDate) {
        this.idTransactionDocument = idTransactionDocument;
        this.uploadingDate = uploadingDate;
    }

    public Integer getIdTransactionDocument() {
        return idTransactionDocument;
    }

    public void setIdTransactionDocument(Integer idTransactionDocument) {
        this.idTransactionDocument = idTransactionDocument;
    }

    public Integer getIdTransactionDocumentType() {
        return idTransactionDocumentType;
    }

    public void setIdTransactionDocumentType(Integer idTransactionDocumentType) {
        this.idTransactionDocumentType = idTransactionDocumentType;
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

    public LocalDateTime getUploadingDate() {
        return uploadingDate;
    }

    public void setUploadingDate(LocalDateTime uploadingDate) {
        this.uploadingDate = uploadingDate;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public CTransactionsDocumentsTypes getTransactionDocumentType() {
        return transactionDocumentType;
    }

    public void setTransactionDocumentType(CTransactionsDocumentsTypes transactionDocumentType) {
        this.transactionDocumentType = transactionDocumentType;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransactionDocument != null ? idTransactionDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionsDocuments)) {
            return false;
        }
        TransactionsDocuments other = (TransactionsDocuments) object;
        if ((this.idTransactionDocument == null && other.idTransactionDocument != null) || (this.idTransactionDocument != null && !this.idTransactionDocument.equals(other.idTransactionDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TransactionsDocuments[ idTransactionDocument=" + idTransactionDocument + " ]";
    }
    
}
