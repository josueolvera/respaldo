/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "GROUPS_AGREEMENTS")

public class GroupsAgreements implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GA")
    @JsonView(JsonViews.Root.class)
    private Integer idGa;
   
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Column(name = "ID_AG", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAg;
       
    @Column(name = "ID_AGREEMENT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAgreement;
       
    @JoinColumn(name = "ID_AG", referencedColumnName = "ID_AG")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAgreementsGroups agreementGroup;
    
    @JoinColumn(name = "ID_AGREEMENT", referencedColumnName = "ID_AGREEMENT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAgreements agreement;

    public GroupsAgreements() {
    }

    public GroupsAgreements(Integer idGa) {
        this.idGa = idGa;
    }

    public Integer getIdGa() {
        return idGa;
    }

    public void setIdGa(Integer idGa) {
        this.idGa = idGa;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdAg() {
        return idAg;
    }

    public void setIdAg(Integer idAg) {
        this.idAg = idAg;
    }

    public Integer getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Integer idAgreement) {
        this.idAgreement = idAgreement;
    }

    public CAgreementsGroups getAgreementGroup() {
        return agreementGroup;
    }

    public void setAgreementGroup(CAgreementsGroups agreementGroup) {
        this.agreementGroup = agreementGroup;
    }

    public CAgreements getAgreement() {
        return agreement;
    }

    public void setAgreement(CAgreements agreement) {
        this.agreement = agreement;
    }
    
        

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGa != null ? idGa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsAgreements)) {
            return false;
        }
        GroupsAgreements other = (GroupsAgreements) object;
        if ((this.idGa == null && other.idGa != null) || (this.idGa != null && !this.idGa.equals(other.idGa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.GroupsAgreements[ idGa=" + idGa + " ]";
    }
    
}
