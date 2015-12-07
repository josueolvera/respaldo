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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "DW_ENTERPRISES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class DwEnterprises implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DW_ENTERPRISE")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class, JsonViews.IdsEnterprises.class})
    private Integer idDwEnterprise;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BRAND")
    @JsonView(JsonViews.Root.class)
    private int idBrand;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GBRANCH")
    @JsonView(JsonViews.Root.class)
    private int idGbranch;
    
    @Column(name = "ID_GROUP", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idgroup;
    
    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int iddistributor;
    
    @Column(name = "ID_REGION", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idregion;
    
    @Column(name = "ID_BRANCH", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idbranch;
    
    @Column(name = "ID_AREA", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idarea;
    
    @JoinColumn(name = "ID_GROUP", referencedColumnName = "ID_GROUP")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CGroups idGroup;
    
    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CDistributors idDistributor;
    
    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CRegions idRegion;
    
    @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CBranchs idBranch;
    
    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CAreas idArea;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDwEnterprise")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthBranch> budgetMonthBranchList;

    public DwEnterprises() {
    }

    public DwEnterprises(Integer idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public DwEnterprises(Integer idDwEnterprise, int idBrand, int idGbranch) {
        this.idDwEnterprise = idDwEnterprise;
        this.idBrand = idBrand;
        this.idGbranch = idGbranch;
    }

    public Integer getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(Integer idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public int getIdGbranch() {
        return idGbranch;
    }

    public void setIdGbranch(int idGbranch) {
        this.idGbranch = idGbranch;
    }

    public CGroups getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(CGroups idGroup) {
        this.idGroup = idGroup;
    }

    public CDistributors getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(CDistributors idDistributor) {
        this.idDistributor = idDistributor;
    }

    public CRegions getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(CRegions idRegion) {
        this.idRegion = idRegion;
    }

    public CBranchs getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(CBranchs idBranch) {
        this.idBranch = idBranch;
    }

    public CAreas getIdArea() {
        return idArea;
    }

    public void setIdArea(CAreas idArea) {
        this.idArea = idArea;
    }

    public List<BudgetMonthBranch> getBudgetMonthBranchList() {
        return budgetMonthBranchList;
    }

    public void setBudgetMonthBranchList(List<BudgetMonthBranch> budgetMonthBranchList) {
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    public int getIdgroup() {
        return idgroup;
    }

    public void setIdgroup(int idgroup) {
        this.idgroup = idgroup;
    }

    public int getIddistributor() {
        return iddistributor;
    }

    public void setIddistributor(int iddistributor) {
        this.iddistributor = iddistributor;
    }

    public int getIdregion() {
        return idregion;
    }

    public void setIdregion(int idregion) {
        this.idregion = idregion;
    }

    public int getIdbranch() {
        return idbranch;
    }

    public void setIdbranch(int idbranch) {
        this.idbranch = idbranch;
    }

    public int getIdarea() {
        return idarea;
    }

    public void setIdarea(int idarea) {
        this.idarea = idarea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDwEnterprise != null ? idDwEnterprise.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DwEnterprises)) {
            return false;
        }
        DwEnterprises other = (DwEnterprises) object;
        if ((this.idDwEnterprise == null && other.idDwEnterprise != null) || (this.idDwEnterprise != null && !this.idDwEnterprise.equals(other.idDwEnterprise))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DwEnterprises[ idDwEnterprise=" + idDwEnterprise + " ]";
    }
    
}
