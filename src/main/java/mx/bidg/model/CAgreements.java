/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_AGREEMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CAgreements.findAll", query = "SELECT c FROM CAgreements c"),
    @NamedQuery(name = "CAgreements.findByIdAgreement", query = "SELECT c FROM CAgreements c WHERE c.idAgreement = :idAgreement"),
    @NamedQuery(name = "CAgreements.findByAgreementName", query = "SELECT c FROM CAgreements c WHERE c.agreementName = :agreementName"),
    @NamedQuery(name = "CAgreements.findByUploadedDate", query = "SELECT c FROM CAgreements c WHERE c.uploadedDate = :uploadedDate"),
    @NamedQuery(name = "CAgreements.findByLowDate", query = "SELECT c FROM CAgreements c WHERE c.lowDate = :lowDate"),
    @NamedQuery(name = "CAgreements.findByStatus", query = "SELECT c FROM CAgreements c WHERE c.status = :status"),
    @NamedQuery(name = "CAgreements.findByIdAccessLevel", query = "SELECT c FROM CAgreements c WHERE c.idAccessLevel = :idAccessLevel")})
public class CAgreements implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AGREEMENT")
    private Integer idAgreement;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AGREEMENT_NAME")
    private String agreementName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPLOADED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedDate;
    @Column(name = "LOW_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lowDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;
    @OneToMany(mappedBy = "idAgreement")
    private List<DwEnterprisesAgreements> dwEnterprisesAgreementsList;

    public CAgreements() {
    }

    public CAgreements(Integer idAgreement) {
        this.idAgreement = idAgreement;
    }

    public CAgreements(Integer idAgreement, String agreementName, Date uploadedDate, int status, int idAccessLevel) {
        this.idAgreement = idAgreement;
        this.agreementName = agreementName;
        this.uploadedDate = uploadedDate;
        this.status = status;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Integer idAgreement) {
        this.idAgreement = idAgreement;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public Date getLowDate() {
        return lowDate;
    }

    public void setLowDate(Date lowDate) {
        this.lowDate = lowDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @XmlTransient
    public List<DwEnterprisesAgreements> getDwEnterprisesAgreementsList() {
        return dwEnterprisesAgreementsList;
    }

    public void setDwEnterprisesAgreementsList(List<DwEnterprisesAgreements> dwEnterprisesAgreementsList) {
        this.dwEnterprisesAgreementsList = dwEnterprisesAgreementsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAgreement != null ? idAgreement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAgreements)) {
            return false;
        }
        CAgreements other = (CAgreements) object;
        if ((this.idAgreement == null && other.idAgreement != null) || (this.idAgreement != null && !this.idAgreement.equals(other.idAgreement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAgreements[ idAgreement=" + idAgreement + " ]";
    }
    
}
