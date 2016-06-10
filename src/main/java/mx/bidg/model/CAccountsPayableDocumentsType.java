/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "C_ACCOUNTS_PAYABLE_DOCUMENTS_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CAccountsPayableDocumentsType.findAll", query = "SELECT c FROM CAccountsPayableDocumentsType c"),
    @NamedQuery(name = "CAccountsPayableDocumentsType.findByIdDocumentType", query = "SELECT c FROM CAccountsPayableDocumentsType c WHERE c.idDocumentType = :idDocumentType"),
    @NamedQuery(name = "CAccountsPayableDocumentsType.findByDocumentName", query = "SELECT c FROM CAccountsPayableDocumentsType c WHERE c.documentName = :documentName")})
public class CAccountsPayableDocumentsType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENT_TYPE")
    private Integer idDocumentType;
    @Size(max = 100)
    @Column(name = "DOCUMENT_NAME")
    private String documentName;
    @OneToMany(mappedBy = "idDocumentType")
    private List<AccountsPayableDocuments> accountsPayableDocumentsList;

    public CAccountsPayableDocumentsType() {
    }

    public CAccountsPayableDocumentsType(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public Integer getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @XmlTransient
    public List<AccountsPayableDocuments> getAccountsPayableDocumentsList() {
        return accountsPayableDocumentsList;
    }

    public void setAccountsPayableDocumentsList(List<AccountsPayableDocuments> accountsPayableDocumentsList) {
        this.accountsPayableDocumentsList = accountsPayableDocumentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentType != null ? idDocumentType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAccountsPayableDocumentsType)) {
            return false;
        }
        CAccountsPayableDocumentsType other = (CAccountsPayableDocumentsType) object;
        if ((this.idDocumentType == null && other.idDocumentType != null) || (this.idDocumentType != null && !this.idDocumentType.equals(other.idDocumentType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAccountsPayableDocumentsType[ idDocumentType=" + idDocumentType + " ]";
    }
    
}