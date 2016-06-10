/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "TRANSACTIONS_DOCUMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionsDocuments.findAll", query = "SELECT t FROM TransactionsDocuments t"),
    @NamedQuery(name = "TransactionsDocuments.findByIdTransactionDocument", query = "SELECT t FROM TransactionsDocuments t WHERE t.idTransactionDocument = :idTransactionDocument"),
    @NamedQuery(name = "TransactionsDocuments.findByIdTransactionDocumentType", query = "SELECT t FROM TransactionsDocuments t WHERE t.idTransactionDocumentType = :idTransactionDocumentType"),
    @NamedQuery(name = "TransactionsDocuments.findByDocumentUrl", query = "SELECT t FROM TransactionsDocuments t WHERE t.documentUrl = :documentUrl"),
    @NamedQuery(name = "TransactionsDocuments.findByDocumentName", query = "SELECT t FROM TransactionsDocuments t WHERE t.documentName = :documentName"),
    @NamedQuery(name = "TransactionsDocuments.findByUploadingDate", query = "SELECT t FROM TransactionsDocuments t WHERE t.uploadingDate = :uploadingDate"),
    @NamedQuery(name = "TransactionsDocuments.findByIdAccessLevel", query = "SELECT t FROM TransactionsDocuments t WHERE t.idAccessLevel = :idAccessLevel")})
public class TransactionsDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRANSACTION_DOCUMENT")
    private Integer idTransactionDocument;
    @Column(name = "ID_TRANSACTION_DOCUMENT_TYPE")
    private Integer idTransactionDocumentType;
    @Size(max = 2048)
    @Column(name = "DOCUMENT_URL")
    private String documentUrl;
    @Size(max = 1024)
    @Column(name = "DOCUMENT_NAME")
    private String documentName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPLOADING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadingDate;
    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;

    public TransactionsDocuments() {
    }

    public TransactionsDocuments(Integer idTransactionDocument) {
        this.idTransactionDocument = idTransactionDocument;
    }

    public TransactionsDocuments(Integer idTransactionDocument, Date uploadingDate) {
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

    public Date getUploadingDate() {
        return uploadingDate;
    }

    public void setUploadingDate(Date uploadingDate) {
        this.uploadingDate = uploadingDate;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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
