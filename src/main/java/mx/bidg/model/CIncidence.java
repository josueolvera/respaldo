/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author gerardo8
 */
@Entity
@Table(name = "C_INCIDENCE")
public class CIncidence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_INCIDENCE")
    private Integer idIncidence;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "INCIDENCE_NAME")
    private String incidenceName;

    public CIncidence() {
    }

    public CIncidence(Integer idIncidence) {
        this.idIncidence = idIncidence;
    }


    public Integer getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(Integer idIncidence) {
        this.idIncidence = idIncidence;
    }

    public String getIncidenceName() {
        return incidenceName;
    }

    public void setIncidenceName(String incidenceName) {
        this.incidenceName = incidenceName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIncidence != null ? idIncidence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CIncidence)) {
            return false;
        }
        CIncidence other = (CIncidence) object;
        if ((this.idIncidence == null && other.idIncidence != null) || (this.idIncidence != null && !this.idIncidence.equals(other.idIncidence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CIncidence[ idIncidence=" + idIncidence + " ]";
    }
    
}
