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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "C_EMPLOYEE_DOCUMENTS_TYPES")
public class CEmployeeDocumentsTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idDocumentType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DOCUMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String documentName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FIELD")
    @JsonView(JsonViews.Root.class)
    private int field;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUIRED")
    @JsonView(JsonViews.Root.class)
    private int required;

    @Basic(optional = false)
    @NotNull
    @Column(name = "UPDATABLE")
    @JsonView(JsonViews.Root.class)
    private boolean updatable;

    public CEmployeeDocumentsTypes() {
    }

    public CEmployeeDocumentsTypes(Integer idDocumentType) {
        this.idDocumentType = idDocumentType;
    }

    public CEmployeeDocumentsTypes(Integer idDocumentType, String documentName, int field) {
        this.idDocumentType = idDocumentType;
        this.documentName = documentName;
        this.field = field;
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

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
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
        if (!(object instanceof CEmployeeDocumentsTypes)) {
            return false;
        }
        CEmployeeDocumentsTypes other = (CEmployeeDocumentsTypes) object;
        if ((this.idDocumentType == null && other.idDocumentType != null) || (this.idDocumentType != null && !this.idDocumentType.equals(other.idDocumentType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CEmployeeDocumentsTypes[ idDocumentType=" + idDocumentType + " ]";
    }
}
