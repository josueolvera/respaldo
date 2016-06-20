/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_AGREEMENTS")

public class CAgreements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AGREEMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idAgreement;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AGREEMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String agreementName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "UPLOADED_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime uploadedDate;

    @Column(name = "LOW_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime lowDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Size(min = 1, max = 50)
    @Column(name = "AGREEMENT_NAME_CLEAN")
    @JsonView(JsonViews.Root.class)
    private String agreementNameClean;


    public CAgreements() {
    }

    public CAgreements(Integer idAgreement) {
        this.idAgreement = idAgreement;
    }

    public CAgreements(Integer idAgreement, String agreementName, LocalDateTime uploadedDate, int status, int idAccessLevel) {
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

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public LocalDateTime getLowDate() {
        return lowDate;
    }

    public void setLowDate(LocalDateTime lowDate) {
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

    public String getAgreementNameClean() {
        return agreementNameClean;
    }

    public void setAgreementNameClean(String agreementNameClean) {
        this.agreementNameClean = agreementNameClean;
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
        return "mx.bidg.model.CAgreementsDao[ idAgreement=" + idAgreement + " ]";
    }
    
}
