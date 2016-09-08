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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "C_DIVS_CALCULATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CDivsCalculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DIV_CALCULATION")
    @JsonView(JsonViews.Root.class)
    private Integer idDivCalculation;

    @Size(max = 50)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    @Column(name = "ID_FIXED_OPERATIONS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idFixedOperations;

    @JoinColumn(name = "ID_FIXED_OPERATIONS", referencedColumnName = "ID_FIXED_OPERATIONS")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private FixedOperations fixedOperations;

    public CDivsCalculation() {
    }

    public Integer getIdDivCalculation() {
        return idDivCalculation;
    }

    public void setIdDivCalculation(Integer idDivCalculation) {
        this.idDivCalculation = idDivCalculation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdFixedOperations() {
        return idFixedOperations;
    }

    public void setIdFixedOperations(Integer idFixedOperations) {
        this.idFixedOperations = idFixedOperations;
    }

    public FixedOperations getFixedOperations() {
        return fixedOperations;
    }

    public void setFixedOperations(FixedOperations fixedOperations) {
        this.fixedOperations = fixedOperations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDivCalculation != null ? idDivCalculation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CDivsCalculation)) {
            return false;
        }
        CDivsCalculation other = (CDivsCalculation) object;
        if ((this.idDivCalculation == null && other.idDivCalculation != null) || (this.idDivCalculation != null && !this.idDivCalculation.equals(other.idDivCalculation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CDivsCalculation[ idDivCalculation=" + idDivCalculation + " ]";
    }
    
}
