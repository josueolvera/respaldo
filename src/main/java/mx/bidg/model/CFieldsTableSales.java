package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_FIELDS_TABLE_SALES")
public class CFieldsTableSales implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @JsonView(JsonViews.Root.class)
    private Integer id;

    @Size(max = 50)
    @Column(name = "FIELD_NAME")
    @JsonView(JsonViews.Root.class)
    private String fieldName;

    @Size(max = 50)
    @Column(name = "FIELD_USER")
    @JsonView(JsonViews.Root.class)
    private String fieldUser;

    public CFieldsTableSales() {
    }

    public CFieldsTableSales(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldUser() {
        return fieldUser;
    }

    public void setFieldUser(String fieldUser) {
        this.fieldUser = fieldUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFieldsTableSales)) {
            return false;
        }
        CFieldsTableSales other = (CFieldsTableSales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CFieldsTableSales[ id=" + id + " ]";
    }
    
}
