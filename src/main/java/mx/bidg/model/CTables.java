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

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_TABLES")
public class CTables implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TABLE")
    @JsonView(JsonViews.Root.class)
    private Integer idTable;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TABLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String tableName;

    @Size(max = 200)
    @Column(name = "URI")
    @JsonView(JsonViews.Root.class)
    private String uri;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Size(max = 100)
    @Column(name = "CLASS_NAME")
    @JsonView(JsonViews.Root.class)
    private String className;

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
    
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
