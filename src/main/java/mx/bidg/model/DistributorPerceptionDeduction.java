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
import org.hibernate.annotations.Type;

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
@Table(name = "DISTRIBUTOR_PERCEPTION_DEDUCTION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class DistributorPerceptionDeduction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DISTRIBUTOR_PD")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorPd;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_C_PD", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCPd;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributor;

    @JoinColumn(name = "ID_C_PD", referencedColumnName = "ID_C_PD")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CPerceptionsDeductions cPerceptionsDeductions;

    @Column(name = "HAS_PD", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean hasPd;

    public DistributorPerceptionDeduction() {
    }

    public DistributorPerceptionDeduction(Integer idDistributorPd) {
        this.idDistributorPd = idDistributorPd;
    }

    public Integer getIdDistributorPd() {
        return idDistributorPd;
    }

    public void setIdDistributorPd(Integer idDistributorPd) {
        this.idDistributorPd = idDistributorPd;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdCPd() {
        return idCPd;
    }

    public void setIdCPd(Integer idCPd) {
        this.idCPd = idCPd;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    public CPerceptionsDeductions getcPerceptionsDeductions() {
        return cPerceptionsDeductions;
    }

    public void setcPerceptionsDeductions(CPerceptionsDeductions cPerceptionsDeductions) {
        this.cPerceptionsDeductions = cPerceptionsDeductions;
    }

    public Boolean getHasPd() {
        return hasPd;
    }

    public void setHasPd(Boolean hasPd) {
        this.hasPd = hasPd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistributorPd != null ? idDistributorPd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistributorPerceptionDeduction)) {
            return false;
        }
        DistributorPerceptionDeduction other = (DistributorPerceptionDeduction) object;
        if ((this.idDistributorPd == null && other.idDistributorPd != null) || (this.idDistributorPd != null && !this.idDistributorPd.equals(other.idDistributorPd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DistributorPerceptionDeduction[ idDistributorPd=" + idDistributorPd + " ]";
    }
    
}
