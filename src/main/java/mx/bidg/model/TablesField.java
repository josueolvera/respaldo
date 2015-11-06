/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TABLES_FIELD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablesField.findAll", query = "SELECT t FROM TablesField t")})
public class TablesField implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TABLE_FIELD")
    @JsonView(JsonViews.Root.class)
    private Integer idTableField;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TABLE_FIELD_NAME")
    @JsonView(JsonViews.Root.class)
    private String tableFieldName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @JoinColumn(name = "ID_TABLE", referencedColumnName = "ID_TABLE")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(optional = false)
    private CTables idTable;

    @JoinColumn(name = "ID_FIELD", referencedColumnName = "ID_FIELD")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(optional = false)
    private CFields idField;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTableField")
    @JsonView(JsonViews.Embedded.class)
    private List<TablesFieldsRole> tablesFieldsRoleList;

    public TablesField() {
    }

    public TablesField(Integer idTableField) {
        this.idTableField = idTableField;
    }

    public TablesField(Integer idTableField, String tableFieldName, Date creationDate) {
        this.idTableField = idTableField;
        this.tableFieldName = tableFieldName;
        this.creationDate = creationDate;
    }

    public Integer getIdTableField() {
        return idTableField;
    }

    public void setIdTableField(Integer idTableField) {
        this.idTableField = idTableField;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CTables getIdTable() {
        return idTable;
    }

    public void setIdTable(CTables idTable) {
        this.idTable = idTable;
    }

    public CFields getIdField() {
        return idField;
    }

    public void setIdField(CFields idField) {
        this.idField = idField;
    }

    @XmlTransient
    public List<TablesFieldsRole> getTablesFieldsRoleList() {
        return tablesFieldsRoleList;
    }

    public void setTablesFieldsRoleList(List<TablesFieldsRole> tablesFieldsRoleList) {
        this.tablesFieldsRoleList = tablesFieldsRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTableField != null ? idTableField.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablesField)) {
            return false;
        }
        TablesField other = (TablesField) object;
        if ((this.idTableField == null && other.idTableField != null) || (this.idTableField != null && !this.idTableField.equals(other.idTableField))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TablesField[ idTableField=" + idTableField + " ]";
    }
    
}
