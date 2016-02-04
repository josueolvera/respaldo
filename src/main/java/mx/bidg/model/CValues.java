package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@Entity
@DynamicUpdate
@Table(name = "C_VALUES")
@Audited
public class CValues implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VALUE")
    @JsonView(JsonViews.Root.class)
    private Integer idValue;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 65500)
    @Column(name = "VALUE")
    @JsonView(JsonViews.Root.class)
    private String value;

    public CValues() {}

    public CValues(Integer idValue) {
        this.idValue = idValue;
    }

    public Integer getIdValue() {
        return idValue;
    }

    public void setIdValue(Integer idValue) {
        this.idValue = idValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValue != null ? idValue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTasks)) {
            return false;
        }
        CValues other = (CValues) object;
        if ((this.idValue == null && other.idValue != null) || (this.idValue != null && !this.idValue.equals(other.idValue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CValues[ idValue=" + idValue + " ]";
    }
}
