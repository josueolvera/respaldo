/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Cristhian de la cruz
 */
@Entity
@DynamicUpdate
@Table(name = "MULTILEVEL_EMPLOYEE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class MultilevelEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MULTILEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idMultilevel;
    
    @Column(name = "ID_EMPLOYEE_MULTILEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployeeMultilevel;
    
    @Column(name = "LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer level;

    public MultilevelEmployee() {
    }

    public MultilevelEmployee(Integer idMultilevel) {
        this.idMultilevel = idMultilevel;
    }

    public Integer getIdMultilevel() {
        return idMultilevel;
    }

    public void setIdMultilevel(Integer idMultilevel) {
        this.idMultilevel = idMultilevel;
    }

    public Integer getIdEmployeeMultilevel() {
        return idEmployeeMultilevel;
    }

    public void setIdEmployeeMultilevel(Integer idEmployeeMultilevel) {
        this.idEmployeeMultilevel = idEmployeeMultilevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultilevel != null ? idMultilevel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultilevelEmployee)) {
            return false;
        }
        MultilevelEmployee other = (MultilevelEmployee) object;
        if ((this.idMultilevel == null && other.idMultilevel != null) || (this.idMultilevel != null && !this.idMultilevel.equals(other.idMultilevel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.MultilevelEmployee[ idMultilevel=" + idMultilevel + " ]";
    }
    
}
