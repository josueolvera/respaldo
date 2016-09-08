/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

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

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "FIXED_OPERATION_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class FixedOperationRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_FIXED_OPERATION_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idFixedOperationRole;

    @Column(name = "ID_FIXED_OPERATIONS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idFixedOperations;

    @Column(name = "ID_CALCULATION_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCalculationRole;

    @JoinColumn(name = "ID_FIXED_OPERATIONS", referencedColumnName = "ID_FIXED_OPERATIONS")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private FixedOperations fixedOperations;

    @JoinColumn(name = "ID_CALCULATION_ROLE", referencedColumnName = "ID_CALCULATION_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CalculationRoles calculationRole;

    public FixedOperationRole() {
    }

    public FixedOperationRole(Integer idFixedOperationRole) {
        this.idFixedOperationRole = idFixedOperationRole;
    }

    public Integer getIdFixedOperationRole() {
        return idFixedOperationRole;
    }

    public void setIdFixedOperationRole(Integer idFixedOperationRole) {
        this.idFixedOperationRole = idFixedOperationRole;
    }

    public Integer getIdFixedOperations() {
        return idFixedOperations;
    }

    public void setIdFixedOperations(Integer idFixedOperations) {
        this.idFixedOperations = idFixedOperations;
    }

    public Integer getIdCalculationRole() {
        return idCalculationRole;
    }

    public void setIdCalculationRole(Integer idCalculationRole) {
        this.idCalculationRole = idCalculationRole;
    }

    public FixedOperations getFixedOperations() {
        return fixedOperations;
    }

    public void setFixedOperations(FixedOperations fixedOperations) {
        this.fixedOperations = fixedOperations;
    }

    public CalculationRoles getCalculationRole() {
        return calculationRole;
    }

    public void setCalculationRole(CalculationRoles calculationRole) {
        this.calculationRole = calculationRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFixedOperationRole != null ? idFixedOperationRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FixedOperationRole)) {
            return false;
        }
        FixedOperationRole other = (FixedOperationRole) object;
        if ((this.idFixedOperationRole == null && other.idFixedOperationRole != null) || (this.idFixedOperationRole != null && !this.idFixedOperationRole.equals(other.idFixedOperationRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.FixedOperationRole[ idFixedOperationRole=" + idFixedOperationRole + " ]";
    }
    
}
