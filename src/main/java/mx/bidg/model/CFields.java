/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_FIELDS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CFields implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idField")
    @JsonView(JsonViews.Embedded.class)
    private List<TablesField> tablesFieldList;

    public CFields() {
    }

    public CFields(Integer idField) {
        this.idField = idField;
    }

    public CFields(Integer idField, String fieldName, Date creationDate) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<TablesField> getTablesFieldList() {
        return tablesFieldList;
    }

    public void setTablesFieldList(List<TablesField> tablesFieldList) {
        this.tablesFieldList = tablesFieldList;
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
