/**
 *
 * @author rafael
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "C_ATTRIBUTES")
public class CAttributes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATTRIBUTE")
    @JsonView(JsonViews.Root.class)
    private Integer idAttribute;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "ATTRIBUTE_NAME")
    @JsonView(JsonViews.Root.class)
    private String attributeName;

    public CAttributes() {
    }

    public CAttributes(Integer idAttribute) {
        this.idAttribute = idAttribute;
    }

    public CAttributes(Integer idAttribute, String attributeName) {
        this.idAttribute = idAttribute;
        this.attributeName = attributeName;
    }

    public Integer getIdAttribute() {
        return idAttribute;
    }

    public void setIdAttribute(Integer idAttribute) {
        this.idAttribute = idAttribute;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttribute != null ? idAttribute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAttributes)) {
            return false;
        }
        CAttributes other = (CAttributes) object;
        if ((this.idAttribute == null && other.idAttribute != null) || (this.idAttribute != null && !this.idAttribute.equals(other.idAttribute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAttributes[ idAttribute=" + idAttribute + " ]";
    }
}
