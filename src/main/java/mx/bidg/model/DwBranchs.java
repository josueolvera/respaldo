/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "DW_BRANCHS")
public class DwBranchs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BRANCH")
    private Integer idBranch;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INDEX_REPROCESSING")
    private BigDecimal indexReprocessing;
    @Column(name = "PRODUCTIVITY")
    private BigDecimal productivity;
    @Column(name = "PTTO_PROM_VTA")
    private Integer pttoPromVta;
    @Column(name = "PTTO_PROM_REAL")
    private Integer pttoPromReal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPLOADED_DATE")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime uploadedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private int status;

    public DwBranchs() {
    }

    public DwBranchs(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public BigDecimal getIndexReprocessing() {
        return indexReprocessing;
    }

    public void setIndexReprocessing(BigDecimal indexReprocessing) {
        this.indexReprocessing = indexReprocessing;
    }

    public BigDecimal getProductivity() {
        return productivity;
    }

    public void setProductivity(BigDecimal productivity) {
        this.productivity = productivity;
    }

    public Integer getPttoPromVta() {
        return pttoPromVta;
    }

    public void setPttoPromVta(Integer pttoPromVta) {
        this.pttoPromVta = pttoPromVta;
    }

    public Integer getPttoPromReal() {
        return pttoPromReal;
    }

    public void setPttoPromReal(Integer pttoPromReal) {
        this.pttoPromReal = pttoPromReal;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBranch != null ? idBranch.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DwBranchs)) {
            return false;
        }
        DwBranchs other = (DwBranchs) object;
        if ((this.idBranch == null && other.idBranch != null) || (this.idBranch != null && !this.idBranch.equals(other.idBranch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DwBranchs[ idBranch=" + idBranch + " ]";
    }
    
}
