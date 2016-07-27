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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "DITRIBUTORS_EMPLOYEE_DOCUMENTS")
public class DitributorsEmployeeDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DED")
    @JsonView(JsonViews.Root.class)
    private Integer idDed;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_DOCUMENT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDocumentType;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributor;

    @JoinColumn(name = "ID_DOCUMENT_TYPE", referencedColumnName = "ID_DOCUMENT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CEmployeeDocumentsTypes documentType;

    public DitributorsEmployeeDocuments() {
    }

    public DitributorsEmployeeDocuments(Integer idDed) {
        this.idDed = idDed;
    }

    public Integer getIdDed() {
        return idDed;
    }

    public void setIdDed(Integer idDed) {
        this.idDed = idDed;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdDocumentType() {
        return idDocumentType;
    }

    public void setIdDocumentType(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public CEmployeeDocumentsTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(CEmployeeDocumentsTypes documentType) {
        this.documentType = documentType;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDed != null ? idDed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DitributorsEmployeeDocuments)) {
            return false;
        }
        DitributorsEmployeeDocuments other = (DitributorsEmployeeDocuments) object;
        if ((this.idDed == null && other.idDed != null) || (this.idDed != null && !this.idDed.equals(other.idDed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DitributorsEmployeeDocuments[ idDed=" + idDed + " ]";
    }
    
}
