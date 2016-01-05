/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_BRANCHS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CBranchs implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BRANCH")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private Integer idBranch;
    
    @Size(max = 50)
    @Column(name = "BRANCH_NAME")
    @JsonView(JsonViews.Root.class)
    private String branchName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "BRANCH_SHORT")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private String branchShort;
    
    @Size(max = 50)
    @Column(name = "LOCATION")
    @JsonView(JsonViews.Root.class)
    private String location;
    
    @Size(max = 400)
    @Column(name = "ADDRESS")
    @JsonView(JsonViews.Root.class)
    private String address;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBranch")
    @JsonView(JsonViews.Embedded.class)
    private List<DwEnterprises> dwEnterprisesList;

    public CBranchs() {
    }

    public CBranchs(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public CBranchs(Integer idBranch, String branchShort, LocalDateTime uploadedDate, int status) {
        this.idBranch = idBranch;
        this.branchShort = branchShort;
        this.uploadedDate = uploadedDate;
        this.status = status;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchShort() {
        return branchShort;
    }

    public void setBranchShort(String branchShort) {
        this.branchShort = branchShort;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    
    public List<DwEnterprises> getDwEnterprisesList() {
        return dwEnterprisesList;
    }

    public void setDwEnterprisesList(List<DwEnterprises> dwEnterprisesList) {
        this.dwEnterprisesList = dwEnterprisesList;
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
        if (!(object instanceof CBranchs)) {
            return false;
        }
        CBranchs other = (CBranchs) object;
        if ((this.idBranch == null && other.idBranch != null) || (this.idBranch != null && !this.idBranch.equals(other.idBranch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBranchs[ idBranch=" + idBranch + " ]";
    }

    }
