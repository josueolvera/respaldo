/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "PASSENGER_DOCUMENTS")
public class PassengerDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PASSENGER_DOCUMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idPassengerDocument;

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

    @Column(name = "ID_PASSENGER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPassenger;

    @Column(name = "ID_PASSENGER_DOCUMENT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPassengerDocumentType;

    @JoinColumn(name = "ID_PASSENGER", referencedColumnName = "ID_PASSENGER")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Passengers passenger;

    @JoinColumn(name = "ID_PASSENGER_DOCUMENT_TYPE", referencedColumnName = "ID_PASSENGER_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CPassengerDocumentsTypes passengerDocumentType;

    public PassengerDocuments() {
    }

    public PassengerDocuments(Integer idPassengerDocument) {
        this.idPassengerDocument = idPassengerDocument;
    }

    public Integer getIdPassengerDocument() {
        return idPassengerDocument;
    }

    public void setIdPassengerDocument(Integer idPassengerDocument) {
        this.idPassengerDocument = idPassengerDocument;
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

    public Integer getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(Integer idPassenger) {
        this.idPassenger = idPassenger;
    }

    public Integer getIdPassengerDocumentType() {
        return idPassengerDocumentType;
    }

    public void setIdPassengerDocumentType(Integer idPassengerDocumentType) {
        this.idPassengerDocumentType = idPassengerDocumentType;
    }

    public Passengers getPassenger() {
        return passenger;
    }

    public void setPassenger(Passengers passenger) {
        this.passenger = passenger;
    }

    public CPassengerDocumentsTypes getPassengerDocumentType() {
        return passengerDocumentType;
    }

    public void setPassengerDocumentType(CPassengerDocumentsTypes passengerDocumentType) {
        this.passengerDocumentType = passengerDocumentType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPassengerDocument != null ? idPassengerDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PassengerDocuments)) {
            return false;
        }
        PassengerDocuments other = (PassengerDocuments) object;
        if ((this.idPassengerDocument == null && other.idPassengerDocument != null) || (this.idPassengerDocument != null && !this.idPassengerDocument.equals(other.idPassengerDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.PassengerDocuments[ idPassengerDocument=" + idPassengerDocument + " ]";
    }
    
}
