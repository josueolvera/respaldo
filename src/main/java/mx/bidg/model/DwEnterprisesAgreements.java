/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "DW_ENTERPRISES_AGREEMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DwEnterprisesAgreements.findAll", query = "SELECT d FROM DwEnterprisesAgreements d"),
    @NamedQuery(name = "DwEnterprisesAgreements.findByIdDwEnterpriseAgreement", query = "SELECT d FROM DwEnterprisesAgreements d WHERE d.idDwEnterpriseAgreement = :idDwEnterpriseAgreement"),
    @NamedQuery(name = "DwEnterprisesAgreements.findByIdAccessLevel", query = "SELECT d FROM DwEnterprisesAgreements d WHERE d.idAccessLevel = :idAccessLevel")})
public class DwEnterprisesAgreements implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DW_ENTERPRISE_AGREEMENT")
    private Integer idDwEnterpriseAgreement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;
    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne
    private DwEnterprises idDwEnterprise;
    @JoinColumn(name = "ID_AGREEMENT", referencedColumnName = "ID_AGREEMENT")
    @ManyToOne
    private CAgreements idAgreement;

    public DwEnterprisesAgreements() {
    }

    public DwEnterprisesAgreements(Integer idDwEnterpriseAgreement) {
        this.idDwEnterpriseAgreement = idDwEnterpriseAgreement;
    }

    public DwEnterprisesAgreements(Integer idDwEnterpriseAgreement, int idAccessLevel) {
        this.idDwEnterpriseAgreement = idDwEnterpriseAgreement;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdDwEnterpriseAgreement() {
        return idDwEnterpriseAgreement;
    }

    public void setIdDwEnterpriseAgreement(Integer idDwEnterpriseAgreement) {
        this.idDwEnterpriseAgreement = idDwEnterpriseAgreement;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public DwEnterprises getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(DwEnterprises idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public CAgreements getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(CAgreements idAgreement) {
        this.idAgreement = idAgreement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDwEnterpriseAgreement != null ? idDwEnterpriseAgreement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DwEnterprisesAgreements)) {
            return false;
        }
        DwEnterprisesAgreements other = (DwEnterprisesAgreements) object;
        if ((this.idDwEnterpriseAgreement == null && other.idDwEnterpriseAgreement != null) || (this.idDwEnterpriseAgreement != null && !this.idDwEnterpriseAgreement.equals(other.idDwEnterpriseAgreement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DwEnterprisesAgreements[ idDwEnterpriseAgreement=" + idDwEnterpriseAgreement + " ]";
    }
    
}
