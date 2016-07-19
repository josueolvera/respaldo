/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_EMPLOYEE_TYPE")

public class CEmployeeType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer employeeType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMPLOYEE_TYPE_NAME")
    @JsonView(JsonViews.Root.class)
    private String employeeTypeName;

    public CEmployeeType() {
    }

    public CEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

    public CEmployeeType(Integer employeeType, String employeeTypeName) {
        this.employeeType = employeeType;
        this.employeeTypeName = employeeTypeName;
    }

    public Integer getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeType != null ? employeeType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CEmployeeType)) {
            return false;
        }
        CEmployeeType other = (CEmployeeType) object;
        if ((this.employeeType == null && other.employeeType != null) || (this.employeeType != null && !this.employeeType.equals(other.employeeType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CEmployeeType[ employeeType=" + employeeType + " ]";
    }
    
}
