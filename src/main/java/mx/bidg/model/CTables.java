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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_TABLES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CTables implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TABLE")
    @JsonView(JsonViews.Root.class)
    private Integer idTable;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TABLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String tableName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTable")
    @JsonView(JsonViews.Embedded.class)
    private List<TablesField> tablesFieldList;

    public CTables() {
    }

    public CTables(Integer idTable) {
        this.idTable = idTable;
    }

    public CTables(Integer idTable, String tableName, LocalDateTime creationDate) {
        this.idTable = idTable;
        this.tableName = tableName;
        this.creationDate = creationDate;
    }

    public Integer getIdTable() {
        return idTable;
    }

    public void setIdTable(Integer idTable) {
        this.idTable = idTable;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
        hash += (idTable != null ? idTable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTables)) {
            return false;
        }
        CTables other = (CTables) object;
        if ((this.idTable == null && other.idTable != null) || (this.idTable != null && !this.idTable.equals(other.idTable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTables[ idTable=" + idTable + " ]";
    }
    
}
