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
@Table(name = "GROUPS_AGREEMENTS_CALCULATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class GroupsAgreementsCalculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GAC")
    @JsonView(JsonViews.Root.class)
    private Integer idGac;

    @Column(name = "ID_CALCULATION_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCalculationRole;

    @Column(name = "ID_AG", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAg;

    @JoinColumn(name = "ID_AG", referencedColumnName = "ID_AG")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAgreementsGroups aG;

    @JoinColumn(name = "ID_CALCULATION_ROLE", referencedColumnName = "ID_CALCULATION_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CalculationRoles calculationRole;

    public GroupsAgreementsCalculation() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGac != null ? idGac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsAgreementsCalculation)) {
            return false;
        }
        GroupsAgreementsCalculation other = (GroupsAgreementsCalculation) object;
        if ((this.idGac == null && other.idGac != null) || (this.idGac != null && !this.idGac.equals(other.idGac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.GroupsAgreementsCalculation[ idGac=" + idGac + " ]";
    }
    
}
