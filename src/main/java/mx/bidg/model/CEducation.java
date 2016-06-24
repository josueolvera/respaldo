package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by jolvera on 22/06/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_EDUCATION")

public class CEducation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_EDUCATION")
    @JsonView(JsonViews.Root.class)
    private Integer idEducation;


    @Size(max = 100)
    @Column(name = "EDUCATION_NAME")
    @JsonView(JsonViews.Root.class)
    private String educationName;

    public CEducation() {
    }

    public CEducation(Integer idEducation){
        this.idEducation = idEducation;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public Integer getIdEducation() {
        return idEducation;
    }

    public void setIdEducation(Integer idEducation) {
        this.idEducation = idEducation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CEducation that = (CEducation) o;

        if (idEducation != null ? !idEducation.equals(that.idEducation) : that.idEducation != null) return false;
        return educationName != null ? educationName.equals(that.educationName) : that.educationName == null;

    }

    @Override
    public int hashCode() {
        int result = idEducation != null ? idEducation.hashCode() : 0;
        result = 31 * result + (educationName != null ? educationName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CEducation{" +
                "idEducation=" + idEducation +
                ", educationName='" + educationName + '\'' +
                '}';
    }
}
