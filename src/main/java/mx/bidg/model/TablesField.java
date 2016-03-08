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
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "TABLES_FIELD")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
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
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

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

    public TablesField(Integer idTableField, String tableFieldName, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
