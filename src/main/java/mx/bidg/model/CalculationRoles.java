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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "CALCULATION_ROLES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CalculationRoles implements Serializable {

    public static final CalculationRoles ASESOR_DE_CREDITO = new CalculationRoles(1);
    public static final CalculationRoles SUPERVISOR = new CalculationRoles(3);
    public static final CalculationRoles AUXILIAR = new CalculationRoles(3);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CALCULATION_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idCalculationRole;

    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;

    @Size(max = 50)
    @Column(name = "FIELD_NAME")
    @JsonView(JsonViews.Root.class)
    private String fieldName;

    public CalculationRoles() {
    }

    public CalculationRoles(Integer idCalculationRole) {
        this.idCalculationRole = idCalculationRole;
    }

    public Integer getIdCalculationRole() {
        return idCalculationRole;
    }

    public void setIdCalculationRole(Integer idCalculationRole) {
        this.idCalculationRole = idCalculationRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalculationRole != null ? idCalculationRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalculationRoles)) {
            return false;
        }
        CalculationRoles other = (CalculationRoles) object;
        if ((this.idCalculationRole == null && other.idCalculationRole != null) || (this.idCalculationRole != null && !this.idCalculationRole.equals(other.idCalculationRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CalculationRoles[ idCalculationRole=" + idCalculationRole + " ]";
    }
    
}
