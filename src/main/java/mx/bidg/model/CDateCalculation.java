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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_DATE_CALCULATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CDateCalculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DATE_CALCULATION")
    @JsonView(JsonViews.Root.class)
    private Integer idDateCalculation;

    @Size(max = 50)
    @Column(name = "NAME_DATE")
    @JsonView(JsonViews.Root.class)
    private String nameDate;

    public CDateCalculation() {
    }

    public CDateCalculation(Integer idDateCalculation) {
        this.idDateCalculation = idDateCalculation;
    }

    public Integer getIdDateCalculation() {
        return idDateCalculation;
    }

    public void setIdDateCalculation(Integer idDateCalculation) {
        this.idDateCalculation = idDateCalculation;
    }

    public String getNameDate() {
        return nameDate;
    }

    public void setNameDate(String nameDate) {
        this.nameDate = nameDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDateCalculation != null ? idDateCalculation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CDateCalculation)) {
            return false;
        }
        CDateCalculation other = (CDateCalculation) object;
        if ((this.idDateCalculation == null && other.idDateCalculation != null) || (this.idDateCalculation != null && !this.idDateCalculation.equals(other.idDateCalculation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CDateCalculation[ idDateCalculation=" + idDateCalculation + " ]";
    }
    
}
