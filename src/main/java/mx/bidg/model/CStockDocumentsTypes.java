package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Rafael Viveros
 */
@Entity
@DynamicUpdate
@Table(name = "C_STOCK_DOCUMENTS_TYPES")
public class CStockDocumentsTypes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOCUMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idDocumentType;

    @Size(max = 100)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Column(name = "REQUIRED")
    @JsonView(JsonViews.Root.class)
    private int required;

    public CStockDocumentsTypes() {
    }

    public CStockDocumentsTypes(Integer idDocumentType) {
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

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
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
        if (!(object instanceof CStockDocumentsTypes)) {
            return false;
        }
        CStockDocumentsTypes other = (CStockDocumentsTypes) object;
        if ((this.idDocumentType == null && other.idDocumentType != null) || (this.idDocumentType != null && !this.idDocumentType.equals(other.idDocumentType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CStockDocumentsTypes[ idDocumentType=" + idDocumentType + " ]";
    }
    
}
