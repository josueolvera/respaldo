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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

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

    @Column(name = "STATUS", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean status;
    
    @Column(name = "ID_BRANCH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;
    
    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;
    
    @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CBranchs branchs;
    
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Employees employees;

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
    
    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public CBranchs getBranchs() {
        return branchs;
    }

    public void setBranchs(CBranchs branchs) {
        this.branchs = branchs;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
