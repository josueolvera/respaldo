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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "TABLES_FIELDS_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class TablesFieldsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TABLE_FIELD_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idTableFieldRole;

    @JoinColumn(name = "ID_TABLE_FIELD", referencedColumnName = "ID_TABLE_FIELD")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private TablesField idTableField;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public TablesFieldsRole() {
    }

    public TablesFieldsRole(Integer idTableFieldRole) {
        this.idTableFieldRole = idTableFieldRole;
    }

    public Integer getIdTableFieldRole() {
        return idTableFieldRole;
    }

    public void setIdTableFieldRole(Integer idTableFieldRole) {
        this.idTableFieldRole = idTableFieldRole;
    }

    public TablesField getIdTableField() {
        return idTableField;
    }

    public void setIdTableField(TablesField idTableField) {
        this.idTableField = idTableField;
    }

    public SystemRoles getIdSystemRole() {
        return idSystemRole;
    }

    public void setIdSystemRole(SystemRoles idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTableFieldRole != null ? idTableFieldRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablesFieldsRole)) {
            return false;
        }
        TablesFieldsRole other = (TablesFieldsRole) object;
        if ((this.idTableFieldRole == null && other.idTableFieldRole != null) || (this.idTableFieldRole != null && !this.idTableFieldRole.equals(other.idTableFieldRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TablesFieldsRole[ idTableFieldRole=" + idTableFieldRole + " ]";
    }
    
}
