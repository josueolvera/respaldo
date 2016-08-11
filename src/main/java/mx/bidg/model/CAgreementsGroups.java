/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_AGREEMENTS_GROUPS")

public class CAgreementsGroups implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AG")
    @JsonView(JsonViews.Root.class)
    private Integer idAg;
    
    @Size(max = 50)
    @Column(name = "AGREEMENT_GROUP_NAME")
    @JsonView(JsonViews.Root.class)
    private String agreementGroupName;

    public CAgreementsGroups() {
    }

    public CAgreementsGroups(Integer idAg) {
        this.idAg = idAg;
    }

    public Integer getIdAg() {
        return idAg;
    }

    public void setIdAg(Integer idAg) {
        this.idAg = idAg;
    }

    public String getAgreementGroupName() {
        return agreementGroupName;
    }

    public void setAgreementGroupName(String agreementGroupName) {
        this.agreementGroupName = agreementGroupName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAg != null ? idAg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAgreementsGroups)) {
            return false;
        }
        CAgreementsGroups other = (CAgreementsGroups) object;
        if ((this.idAg == null && other.idAg != null) || (this.idAg != null && !this.idAg.equals(other.idAg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAgreementsGroups[ idAg=" + idAg + " ]";
    }
    
}
