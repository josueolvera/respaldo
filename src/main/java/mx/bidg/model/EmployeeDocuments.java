/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "EMPLOYEE_DOCUMENTS")
public class EmployeeDocuments implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idDocument;

    @Size(max = 2048)
    @Column(name = "DOCUMENT_URL")
    @JsonView(JsonViews.Root.class)
    private String documentUrl;

    @Size(max = 1024)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Column(name = "UPLOADING_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime uploadingDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CURRENT_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private int currentDocument;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Column(name = "FIELD")
    @JsonView(JsonViews.Root.class)
    private String field;

    @Column(name = "ID_DOCUMENT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDocumentType;

    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @JoinColumn(name = "ID_DOCUMENT_TYPE", referencedColumnName = "ID_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CEmployeeDocumentsTypes cDocumentType;

    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;

    public EmployeeDocuments() {
    }

    public EmployeeDocuments(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public EmployeeDocuments(Integer idDocument, int currentDocument) {
        this.idDocument = idDocument;
        this.currentDocument = currentDocument;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
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

    public int getCurrentDocument() {
        return currentDocument;
    }

    public void setCurrentDocument(int currentDocument) {
        this.currentDocument = currentDocument;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public CEmployeeDocumentsTypes getcDocumentType() {
        return cDocumentType;
    }

    public void setcDocumentType(CEmployeeDocumentsTypes cDocumentType) {
        this.cDocumentType = cDocumentType;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public DateFormatsPojo getUploadDateFormats() {
        return (uploadingDate == null) ? null : new DateFormatsPojo(uploadingDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocument != null ? idDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeDocuments)) {
            return false;
        }
        EmployeeDocuments other = (EmployeeDocuments) object;
        if ((this.idDocument == null && other.idDocument != null) || (this.idDocument != null && !this.idDocument.equals(other.idDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.EmployeeDocuments[ idDocument=" + idDocument + " ]";
    }
    
}
