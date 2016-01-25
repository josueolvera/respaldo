/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_FIELDS")
public class CFields implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FIELD")
    @JsonView(JsonViews.Root.class)
    private Integer idField;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FIELD_NAME")
    @JsonView(JsonViews.Root.class)
    private String fieldName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public CFields() {
    }

    public CFields(Integer idField) {
        this.idField = idField;
    }

    public CFields(Integer idField, String fieldName, LocalDateTime creationDate) {
        this.idField = idField;
        this.fieldName = fieldName;
        this.creationDate = creationDate;
    }

    public Integer getIdField() {
        return idField;
    }

    public void setIdField(Integer idField) {
        this.idField = idField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idField != null ? idField.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFields)) {
            return false;
        }
        CFields other = (CFields) object;
        if ((this.idField == null && other.idField != null) || (this.idField != null && !this.idField.equals(other.idField))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CFields[ idField=" + idField + " ]";
    }
    
}
