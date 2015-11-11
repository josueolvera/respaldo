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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTable")
    @JsonView(JsonViews.Embedded.class)
    private List<TablesField> tablesFieldList;

    public CTables() {
    }

    public CTables(Integer idTable) {
        this.idTable = idTable;
    }

    public CTables(Integer idTable, String tableName, Date creationDate) {
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
