package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by gerardo8 on 01/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_SIZES")
public class CSizes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SIZE")
    @JsonView(JsonViews.Root.class)
    private Integer idSize;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SIZE_NAME")
    @JsonView(JsonViews.Root.class)
    private String sizeName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SIZE_ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String sizeAcronyms;

    public CSizes() {
    }

    public CSizes(Integer idSize) {
        this.idSize = idSize;
    }

    public CSizes(String sizeName, String sizeAcronyms) {
        this.sizeName = sizeName;
        this.sizeAcronyms = sizeAcronyms;
    }

    public Integer getIdSize() {
        return idSize;
    }

    public void setIdSize(Integer idSize) {
        this.idSize = idSize;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeAcronyms() {
        return sizeAcronyms;
    }

    public void setSizeAcronyms(String sizeAcronyms) {
        this.sizeAcronyms = sizeAcronyms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSizes cSizes = (CSizes) o;

        return idSize != null ? idSize.equals(cSizes.idSize) : cSizes.idSize == null;

    }

    @Override
    public int hashCode() {
        return idSize != null ? idSize.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CSizes{" +
                "idSize=" + idSize +
                ", sizeName='" + sizeName + '\'' +
                ", sizeAcronyms='" + sizeAcronyms + '\'' +
                '}';
    }
}
