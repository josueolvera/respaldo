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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ACCOUNTS_PAYABLE_DOCUMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountsPayableDocuments.findAll", query = "SELECT a FROM AccountsPayableDocuments a"),
    @NamedQuery(name = "AccountsPayableDocuments.findByIdAccountPayableDocument", query = "SELECT a FROM AccountsPayableDocuments a WHERE a.idAccountPayableDocument = :idAccountPayableDocument"),
    @NamedQuery(name = "AccountsPayableDocuments.findByDocumentUrl", query = "SELECT a FROM AccountsPayableDocuments a WHERE a.documentUrl = :documentUrl"),
    @NamedQuery(name = "AccountsPayableDocuments.findByDocumentName", query = "SELECT a FROM AccountsPayableDocuments a WHERE a.documentName = :documentName"),
    @NamedQuery(name = "AccountsPayableDocuments.findByUploadingDate", query = "SELECT a FROM AccountsPayableDocuments a WHERE a.uploadingDate = :uploadingDate"),
    @NamedQuery(name = "AccountsPayableDocuments.findByIdAccessLevel", query = "SELECT a FROM AccountsPayableDocuments a WHERE a.idAccessLevel = :idAccessLevel")})
public class AccountsPayableDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT_PAYABLE_DOCUMENT")
    private Integer idAccountPayableDocument;
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
    @JoinColumn(name = "ID_DOCUMENT_TYPE", referencedColumnName = "ID_DOCUMENT_TYPE")
    @ManyToOne
    private CAccountsPayableDocumentsType idDocumentType;
    @JoinColumn(name = "ID_ACCOUNT_PAYABLE", referencedColumnName = "ID_ACCOUNT_PAYABLE")
    @ManyToOne
    private AccountsPayable idAccountPayable;

    public AccountsPayableDocuments() {
    }

    public AccountsPayableDocuments(Integer idAccountPayableDocument) {
        this.idAccountPayableDocument = idAccountPayableDocument;
    }

    public AccountsPayableDocuments(Integer idAccountPayableDocument, Date uploadingDate) {
        this.idAccountPayableDocument = idAccountPayableDocument;
        this.uploadingDate = uploadingDate;
    }

    public Integer getIdAccountPayableDocument() {
        return idAccountPayableDocument;
    }

    public void setIdAccountPayableDocument(Integer idAccountPayableDocument) {
        this.idAccountPayableDocument = idAccountPayableDocument;
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

    public CAccountsPayableDocumentsType getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(CAccountsPayableDocumentsType idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public AccountsPayable getIdAccountPayable() {
        return idAccountPayable;
    }

    public void setIdAccountPayable(AccountsPayable idAccountPayable) {
        this.idAccountPayable = idAccountPayable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountPayableDocument != null ? idAccountPayableDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountsPayableDocuments)) {
            return false;
        }
        AccountsPayableDocuments other = (AccountsPayableDocuments) object;
        if ((this.idAccountPayableDocument == null && other.idAccountPayableDocument != null) || (this.idAccountPayableDocument != null && !this.idAccountPayableDocument.equals(other.idAccountPayableDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AccountsPayableDocuments[ idAccountPayableDocument=" + idAccountPayableDocument + " ]";
    }
    
}
