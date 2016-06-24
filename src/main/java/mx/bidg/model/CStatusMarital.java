package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by jolvera on 23/06/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_STATUS_MARITAL")
public class CStatusMarital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_STATUS_MARITAL")
    @JsonView(JsonViews.Root.class)
    private Integer idStatusMarital;


    @Size(max = 100)
    @Column(name = "MARITAL_NAME")
    @JsonView(JsonViews.Root.class)
    private String maritalName;

    public CStatusMarital() {
    }

    public CStatusMarital(Integer idStatusMarital){
        this.idStatusMarital = idStatusMarital;
    }

    public Integer getIdStatusMarital() {
        return idStatusMarital;
    }

    public void setIdStatusMarital(Integer idStatusMarital) {
        this.idStatusMarital = idStatusMarital;
    }

    public String getMaritalName() {
        return maritalName;
    }

    public void setMaritalName(String maritalName) {
        this.maritalName = maritalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CStatusMarital that = (CStatusMarital) o;

        if (idStatusMarital != null ? !idStatusMarital.equals(that.idStatusMarital) : that.idStatusMarital != null)
            return false;
        return maritalName != null ? maritalName.equals(that.maritalName) : that.maritalName == null;

    }

    @Override
    public int hashCode() {
        int result = idStatusMarital != null ? idStatusMarital.hashCode() : 0;
        result = 31 * result + (maritalName != null ? maritalName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CStatusMarital{" +
                "idStatusMarital=" + idStatusMarital +
                ", maritalName='" + maritalName + '\'' +
                '}';
    }
}
