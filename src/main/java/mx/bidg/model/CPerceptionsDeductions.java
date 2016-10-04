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
@Table(name = "C_PERCEPTIONS_DEDUCTIONS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CPerceptionsDeductions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_C_PD")
    @JsonView(JsonViews.Root.class)
    private Integer idCPd;

    @Size(max = 30)
    @Column(name = "NAME_P_D")
    @JsonView(JsonViews.Root.class)
    private String namePD;

    @Column(name = "ID_TYPE_OPERATION")
    @JsonView(JsonViews.Root.class)
    private Integer idTypeOperation;

    public CPerceptionsDeductions() {
    }

    public CPerceptionsDeductions(Integer idCPd) {
        this.idCPd = idCPd;
    }

    public Integer getIdCPd() {
        return idCPd;
    }

    public void setIdCPd(Integer idCPd) {
        this.idCPd = idCPd;
    }

    public String getNamePD() {
        return namePD;
    }

    public void setNamePD(String namePD) {
        this.namePD = namePD;
    }

    public Integer getIdTypeOperation() {
        return idTypeOperation;
    }

    public void setIdTypeOperation(Integer idTypeOperation) {
        this.idTypeOperation = idTypeOperation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCPd != null ? idCPd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPerceptionsDeductions)) {
            return false;
        }
        CPerceptionsDeductions other = (CPerceptionsDeductions) object;
        if ((this.idCPd == null && other.idCPd != null) || (this.idCPd != null && !this.idCPd.equals(other.idCPd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPerceptionsDeductions[ idCPd=" + idCPd + " ]";
    }
    
}
