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
import javax.xml.bind.annotation.XmlTransient;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "DW_ENTERPRISES")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class DwEnterprises implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final DwEnterprises DEFAULT_DW_ENTERPRISES = new DwEnterprises(113);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DW_ENTERPRISE")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class, JsonViews.IdsEnterprises.class})
    private Integer idDwEnterprise;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BRAND")
    @JsonView(JsonViews.Root.class)
    private int idBrand;

    @Column(name = "ID_GROUP", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idGroup;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idDistributor;

    @Column(name = "ID_REGION", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idRegion;

    @Column(name = "ID_BRANCH", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idBranch;

    @Column(name = "ID_AREA", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idArea;

    @Column(name = "ID_ZONA", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private int idZona;

    @Column(name = "BUDGETABLE")
    @JsonView(JsonViews.Root.class)
    private Integer budgetable;

    @JoinColumn(name = "ID_GROUP", referencedColumnName = "ID_GROUP")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CGroups group;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CDistributors distributor;

    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CRegions region;

    @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CBranchs branch;

    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CAreas area;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dwEnterprise")
    @JsonView(JsonViews.Embedded.class)
    private List<DwEmployees> dwEmployeesList;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private boolean status;

    @Column(name = "ID_BUSINESS_LINE", insertable = false, updatable = false)
    @JsonView({JsonViews.Root.class, JsonViews.IdsEnterprises.class})
    private Integer idBusinessLine;

    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID_ZONAS")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CZonas zona;

    @JoinColumn(name = "ID_BUSINESS_LINE", referencedColumnName = "ID_BUSINESS_LINE")
    @ManyToOne(optional = true)
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private CBussinessLine businessLine;





    public DwEnterprises() {
    }

    public DwEnterprises(Integer idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public DwEnterprises(Integer idDwEnterprise, int idBrand) {
        this.idDwEnterprise = idDwEnterprise;
        this.idBrand = idBrand;
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

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
        this.idDistributor = idDistributor;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public int getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(int idBranch) {
        this.idBranch = idBranch;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public Integer getBudgetable() {
        return budgetable;
    }

    public void setBudgetable(Integer budgetable) {
        this.budgetable = budgetable;
    }

    public CGroups getGroup() {
        return group;
    }

    public void setGroup(CGroups group) {
        this.group = group;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    public CRegions getRegion() {
        return region;
    }

    public void setRegion(CRegions region) {
        this.region = region;
    }

    public CBranchs getBranch() {
        return branch;
    }

    public void setBranch(CBranchs branch) {
        this.branch = branch;
    }

    public CAreas getArea() {
        return area;
    }

    public void setArea(CAreas area) {
        this.area = area;
    }

    public List<DwEmployees> getDwEmployeesList() {
        return dwEmployeesList;
    }

    public void setDwEmployeesList(List<DwEmployees> dwEmployeesList) {
        this.dwEmployeesList = dwEmployeesList;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getIdBusinessLine() {
        return idBusinessLine;
    }

    public void setIdBusinessLine(Integer idBusinessLine) {
        this.idBusinessLine = idBusinessLine;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public CZonas getZona() {
        return zona;
    }

    public void setZona(CZonas zona) {
        this.zona = zona;
    }

    public CBussinessLine getBusinessLine() {
        return businessLine;
    }

    public void setBussinessLine(CBussinessLine businessLine) {
        this.businessLine = businessLine;
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
        return "DwEnterprises{" +
                "idDwEnterprise=" + idDwEnterprise +
                ", idBrand=" + idBrand +
                ", idGroup=" + idGroup +
                ", idDistributor=" + idDistributor +
                ", idRegion=" + idRegion +
                ", idBranch=" + idBranch +
                ", idArea=" + idArea +
                ", idZona=" + idZona +
                ", budgetable=" + budgetable +
                ", group=" + group +
                ", distributor=" + distributor +
                ", region=" + region +
                ", branch=" + branch +
                ", area=" + area +
                ", dwEmployeesList=" + dwEmployeesList +
                ", status=" + status +
                ", zona=" + zona +
                '}';
    }
}
