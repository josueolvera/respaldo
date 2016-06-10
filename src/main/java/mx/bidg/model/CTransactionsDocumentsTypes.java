/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "C_TRANSACTIONS_DOCUMENTS_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CTransactionsDocumentsTypes.findAll", query = "SELECT c FROM CTransactionsDocumentsTypes c"),
    @NamedQuery(name = "CTransactionsDocumentsTypes.findByIdTransactionDocumentType", query = "SELECT c FROM CTransactionsDocumentsTypes c WHERE c.idTransactionDocumentType = :idTransactionDocumentType"),
    @NamedQuery(name = "CTransactionsDocumentsTypes.findByDocumentName", query = "SELECT c FROM CTransactionsDocumentsTypes c WHERE c.documentName = :documentName")})
public class CTransactionsDocumentsTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRANSACTION_DOCUMENT_TYPE")
    private Integer idTransactionDocumentType;
    @Size(max = 100)
    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    public CTransactionsDocumentsTypes() {
    }

    public CTransactionsDocumentsTypes(Integer idTransactionDocumentType) {
        this.idTransactionDocumentType = idTransactionDocumentType;
    }

    public Integer getIdTransactionDocumentType() {
        return idTransactionDocumentType;
    }

    public void setIdTransactionDocumentType(Integer idTransactionDocumentType) {
        this.idTransactionDocumentType = idTransactionDocumentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransactionDocumentType != null ? idTransactionDocumentType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTransactionsDocumentsTypes)) {
            return false;
        }
        CTransactionsDocumentsTypes other = (CTransactionsDocumentsTypes) object;
        if ((this.idTransactionDocumentType == null && other.idTransactionDocumentType != null) || (this.idTransactionDocumentType != null && !this.idTransactionDocumentType.equals(other.idTransactionDocumentType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTransactionsDocumentsTypes[ idTransactionDocumentType=" + idTransactionDocumentType + " ]";
    }
    
}
