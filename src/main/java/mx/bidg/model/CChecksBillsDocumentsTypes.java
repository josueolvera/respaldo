package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by gerardo8 on 26/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_CHECKS_BILLS_DOCUMENTS_TYPES")
public class CChecksBillsDocumentsTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CHECK_BILL_DOCUMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBillDocumentType;

    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    public CChecksBillsDocumentsTypes() {
    }

    public CChecksBillsDocumentsTypes(String documentName) {
        this.documentName = documentName;
    }

    public Integer getIdCheckBillDocumentType() {
        return idCheckBillDocumentType;
    }

    public void setIdCheckBillDocumentType(Integer idCheckBillDocumentType) {
        this.idCheckBillDocumentType = idCheckBillDocumentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CChecksBillsDocumentsTypes that = (CChecksBillsDocumentsTypes) o;

        if (idCheckBillDocumentType != null ? !idCheckBillDocumentType.equals(that.idCheckBillDocumentType) : that.idCheckBillDocumentType != null)
            return false;
        return documentName != null ? documentName.equals(that.documentName) : that.documentName == null;

    }

    @Override
    public int hashCode() {
        int result = idCheckBillDocumentType != null ? idCheckBillDocumentType.hashCode() : 0;
        result = 31 * result + (documentName != null ? documentName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CChecksBillsDocumentsTypes{" +
                "idCheckBillDocumentType=" + idCheckBillDocumentType +
                ", documentName='" + documentName + '\'' +
                '}';
    }
}
