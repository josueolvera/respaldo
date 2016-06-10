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
import mx.bidg.pojos.DateFormatsPojo;
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
@Table(name = "ACCOUNTS_PAYABLE_DOCUMENTS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AccountsPayableDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT_PAYABLE_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayableDocument;

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

    @Column(name="ID_DOCUMENT_TYPE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDocumentType;

    @Column(name="ID_ACCOUNT_PAYABLE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayable;

    @JoinColumn(name = "ID_DOCUMENT_TYPE", referencedColumnName = "ID_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAccountsPayableDocumentsType documentType;

    @JoinColumn(name = "ID_ACCOUNT_PAYABLE", referencedColumnName = "ID_ACCOUNT_PAYABLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private AccountsPayable accountPayable;

    public AccountsPayableDocuments() {
    }

    public AccountsPayableDocuments(Integer idAccountPayableDocument) {
        this.idAccountPayableDocument = idAccountPayableDocument;
    }

    public AccountsPayableDocuments(Integer idAccountPayableDocument, LocalDateTime uploadingDate) {
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

    public CAccountsPayableDocumentsType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(CAccountsPayableDocumentsType documentType) {
        this.documentType = documentType;
    }

    public AccountsPayable getAccountPayable() {
        return accountPayable;
    }

    public void setAccountPayable(AccountsPayable accountPayable) {
        this.accountPayable = accountPayable;
    }

    public DateFormatsPojo getCreationDateFormats() {
        return (uploadingDate == null) ? null : new DateFormatsPojo(uploadingDate);
    }

    public Integer getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public Integer getIdAccountPayable() {
        return idAccountPayable;
    }

    public void setIdAccountPayable(Integer idAccountPayable) {
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
