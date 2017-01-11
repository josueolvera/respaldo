package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC_YAIR
 */
@Entity
@DynamicUpdate
@Table(name = "C_TYPE_ASSISTANCE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CTypeAssistance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TYPE_ASSISTANCE")
    @JsonView(JsonViews.Root.class)

    private Integer idTypeAssistance;
    @Size(max = 35)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)

    private String name;
    @OneToMany(mappedBy = "idTypeAssistance")
    @JsonView(JsonViews.Embedded.class)

    private List<SinisterTruckdriver> sinisterTruckdriverList;

    public CTypeAssistance() {
    }

    public CTypeAssistance(Integer idTypeAssistance) {
        this.idTypeAssistance = idTypeAssistance;
    }

    public Integer getIdTypeAssistance() {
        return idTypeAssistance;
    }

    public void setIdTypeAssistance(Integer idTypeAssistance) {
        this.idTypeAssistance = idTypeAssistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<SinisterTruckdriver> getSinisterTruckdriverList() {
        return sinisterTruckdriverList;
    }

    public void setSinisterTruckdriverList(List<SinisterTruckdriver> sinisterTruckdriverList) {
        this.sinisterTruckdriverList = sinisterTruckdriverList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeAssistance != null ? idTypeAssistance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTypeAssistance)) {
            return false;
        }
        CTypeAssistance other = (CTypeAssistance) object;
        if ((this.idTypeAssistance == null && other.idTypeAssistance != null) || (this.idTypeAssistance != null && !this.idTypeAssistance.equals(other.idTypeAssistance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.dao.CTypeAssistance[ idTypeAssistance=" + idTypeAssistance + " ]";
    }

}
