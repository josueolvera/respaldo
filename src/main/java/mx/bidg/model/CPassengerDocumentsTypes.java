/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "C_PASSENGER_DOCUMENTS_TYPES")
public class CPassengerDocumentsTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PASSENGER_DOCUMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idPassengerDocumentType;

    @Size(max = 100)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @OneToMany(mappedBy = "passengerDocumentType")
    @JsonView(JsonViews.Embedded.class)
    private List<PassengerDocuments> passengerDocuments;


    public CPassengerDocumentsTypes() {
    }

    public CPassengerDocumentsTypes(Integer idPassengerDocumentType) {
        this.idPassengerDocumentType = idPassengerDocumentType;
    }

    public Integer getIdPassengerDocumentType() {
        return idPassengerDocumentType;
    }

    public void setIdPassengerDocumentType(Integer idPassengerDocumentType) {
        this.idPassengerDocumentType = idPassengerDocumentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public List<PassengerDocuments> getPassengerDocuments() {
        return passengerDocuments;
    }

    public void setPassengerDocuments(List<PassengerDocuments> passengerDocuments) {
        this.passengerDocuments = passengerDocuments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPassengerDocumentType != null ? idPassengerDocumentType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPassengerDocumentsTypes)) {
            return false;
        }
        CPassengerDocumentsTypes other = (CPassengerDocumentsTypes) object;
        if ((this.idPassengerDocumentType == null && other.idPassengerDocumentType != null) || (this.idPassengerDocumentType != null && !this.idPassengerDocumentType.equals(other.idPassengerDocumentType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPassengerDocumentsTypes[ idPassengerDocumentType=" + idPassengerDocumentType + " ]";
    }
    
}
