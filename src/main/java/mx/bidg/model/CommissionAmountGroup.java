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
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "COMMISSION_AMOUNT_GROUP")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CommissionAmountGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMISSION_AMOUNT_GROUP")
    @JsonView(JsonViews.Root.class)
    private Integer idComissionAmountGroup;

    @Size(max = 50)
    @Column(name = "CLAVE_SAP")
    @JsonView(JsonViews.Root.class)
    private String claveSap;

    @Column(name = "ID_AG")
    @JsonView(JsonViews.Root.class)
    private Integer idAg;

    @Size(max = 50)
    @Column(name = "GROUP_NAME")
    @JsonView(JsonViews.Root.class)
    private String groupName;

    @Column(name = "APPLICATIONS_NUMBER")
    @JsonView(JsonViews.Root.class)
    private BigDecimal applicationsNumber;

    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Column(name = "TABULATOR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal tabulator;

    @Column(name = "COMMISSION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commission;

    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;

    @Column(name = "ID_REGION")
    @JsonView(JsonViews.Root.class)
    private Integer idRegion;

    @Column(name = "ID_ZONA")
    @JsonView(JsonViews.Root.class)
    private Integer idZona;

    @Column(name = "ID_DISTRIBUTOR")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    public CommissionAmountGroup() {
    }

    public CommissionAmountGroup(Integer idComissionAmountGroup) {
        this.idComissionAmountGroup = idComissionAmountGroup;
    }

    public Integer getIdComissionAmountGroup() {
        return idComissionAmountGroup;
    }

    public void setIdComissionAmountGroup(Integer idComissionAmountGroup) {
        this.idComissionAmountGroup = idComissionAmountGroup;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public Integer getIdAg() {
        return idAg;
    }

    public void setIdAg(Integer idAg) {
        this.idAg = idAg;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigDecimal getApplicationsNumber() {
        return applicationsNumber;
    }

    public void setApplicationsNumber(BigDecimal applicationsNumber) {
        this.applicationsNumber = applicationsNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTabulator() {
        return tabulator;
    }

    public void setTabulator(BigDecimal tabulator) {
        this.tabulator = tabulator;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComissionAmountGroup != null ? idComissionAmountGroup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommissionAmountGroup)) {
            return false;
        }
        CommissionAmountGroup other = (CommissionAmountGroup) object;
        if ((this.idComissionAmountGroup == null && other.idComissionAmountGroup != null) || (this.idComissionAmountGroup != null && !this.idComissionAmountGroup.equals(other.idComissionAmountGroup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommissionAmountGroup{" +
                "idComissionAmountGroup=" + idComissionAmountGroup +
                ", claveSap='" + claveSap + '\'' +
                ", idAg=" + idAg +
                ", groupName='" + groupName + '\'' +
                ", applicationsNumber=" + applicationsNumber +
                ", amount=" + amount +
                ", tabulator=" + tabulator +
                ", commission=" + commission +
                ", idBranch=" + idBranch +
                ", idRegion=" + idRegion +
                ", idZona=" + idZona +
                ", idDistributor=" + idDistributor +
                '}';
    }
}
