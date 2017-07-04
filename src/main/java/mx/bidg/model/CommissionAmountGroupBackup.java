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
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "COMMISSION_AMOUNT_GROUP_BACKUP")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CommissionAmountGroupBackup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMISSION_AMOUNT_GROUP_BACKUP")
    @JsonView(JsonViews.Root.class)
    private Integer idComissionAmountGroupBackup;

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

    @Column(name = "INDEX_REPROCESSING")
    @JsonView(JsonViews.Root.class)
    private BigDecimal indexReprocessing;

    @Column(name = "GOAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal goal;

    @Column(name = "SCOPE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal scope;

    @Column(name = "PTTO_PROM_VTA")
    @JsonView(JsonViews.Root.class)
    private Integer pttoPromVta;

    @Column(name = "PTTO_PROM_REAL")
    @JsonView(JsonViews.Root.class)
    private Integer pttoPromReal;

    @Column(name = "FROM_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime fromDate;

    @Column(name = "TO_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime toDate;

    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "BONUS_COMMISSIONABLE_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal bonusCommissionableAmount;

    @Column(name = "ADJUSTMENT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal adjustment;

    @Column(name = "CSB_COMMISSION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal csbCommission;

    @Column(name = "TOTAL_COMMISSION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalCommission;

    public CommissionAmountGroupBackup() {
    }

    public CommissionAmountGroupBackup(Integer idComissionAmountGroupBackup) {
        this.idComissionAmountGroupBackup = idComissionAmountGroupBackup;
    }

    public Integer getIdComissionAmountGroupBackup() {
        return idComissionAmountGroupBackup;
    }

    public void setIdComissionAmountGroupBackup(Integer idComissionAmountGroupBackup) {
        this.idComissionAmountGroupBackup = idComissionAmountGroupBackup;
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

    public BigDecimal getIndexReprocessing() {
        return indexReprocessing;
    }

    public void setIndexReprocessing(BigDecimal indexReprocessing) {
        this.indexReprocessing = indexReprocessing;
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

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public BigDecimal getScope() {
        return scope;
    }

    public void setScope(BigDecimal scope) {
        this.scope = scope;
    }

    public DateFormatsPojo getFromDateFormats() {
        if (fromDate == null) {
            return null;
        }
        return new DateFormatsPojo(fromDate);
    }

    public DateFormatsPojo getToDateFormats() {
        if (toDate == null) {
            return null;
        }
        return new DateFormatsPojo(toDate);
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public BigDecimal getBonusCommissionableAmount() {
        return bonusCommissionableAmount;
    }

    public void setBonusCommissionableAmount(BigDecimal bonusCommissionableAmount) {
        this.bonusCommissionableAmount = bonusCommissionableAmount;
    }

    public BigDecimal getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(BigDecimal adjustment) {
        this.adjustment = adjustment;
    }

    public BigDecimal getCsbCommission() {
        return csbCommission;
    }

    public void setCsbCommission(BigDecimal csbCommission) {
        this.csbCommission = csbCommission;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommissionAmountGroupBackup that = (CommissionAmountGroupBackup) o;

        if (idComissionAmountGroupBackup != null ? !idComissionAmountGroupBackup.equals(that.idComissionAmountGroupBackup) : that.idComissionAmountGroupBackup != null)
            return false;
        if (claveSap != null ? !claveSap.equals(that.claveSap) : that.claveSap != null) return false;
        if (idAg != null ? !idAg.equals(that.idAg) : that.idAg != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (applicationsNumber != null ? !applicationsNumber.equals(that.applicationsNumber) : that.applicationsNumber != null)
            return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (tabulator != null ? !tabulator.equals(that.tabulator) : that.tabulator != null) return false;
        if (commission != null ? !commission.equals(that.commission) : that.commission != null) return false;
        if (idBranch != null ? !idBranch.equals(that.idBranch) : that.idBranch != null) return false;
        if (idRegion != null ? !idRegion.equals(that.idRegion) : that.idRegion != null) return false;
        if (idZona != null ? !idZona.equals(that.idZona) : that.idZona != null) return false;
        if (idDistributor != null ? !idDistributor.equals(that.idDistributor) : that.idDistributor != null)
            return false;
        if (indexReprocessing != null ? !indexReprocessing.equals(that.indexReprocessing) : that.indexReprocessing != null)
            return false;
        if (goal != null ? !goal.equals(that.goal) : that.goal != null) return false;
        if (scope != null ? !scope.equals(that.scope) : that.scope != null) return false;
        if (pttoPromVta != null ? !pttoPromVta.equals(that.pttoPromVta) : that.pttoPromVta != null) return false;
        if (pttoPromReal != null ? !pttoPromReal.equals(that.pttoPromReal) : that.pttoPromReal != null) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        return toDate != null ? toDate.equals(that.toDate) : that.toDate == null;

    }

    @Override
    public int hashCode() {
        int result = idComissionAmountGroupBackup != null ? idComissionAmountGroupBackup.hashCode() : 0;
        result = 31 * result + (claveSap != null ? claveSap.hashCode() : 0);
        result = 31 * result + (idAg != null ? idAg.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (applicationsNumber != null ? applicationsNumber.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (tabulator != null ? tabulator.hashCode() : 0);
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (idBranch != null ? idBranch.hashCode() : 0);
        result = 31 * result + (idRegion != null ? idRegion.hashCode() : 0);
        result = 31 * result + (idZona != null ? idZona.hashCode() : 0);
        result = 31 * result + (idDistributor != null ? idDistributor.hashCode() : 0);
        result = 31 * result + (indexReprocessing != null ? indexReprocessing.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (pttoPromVta != null ? pttoPromVta.hashCode() : 0);
        result = 31 * result + (pttoPromReal != null ? pttoPromReal.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommissionAmountGroupBackup{" +
                "idComissionAmountGroupBackup=" + idComissionAmountGroupBackup +
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
                ", indexReprocessing=" + indexReprocessing +
                ", goal=" + goal +
                ", scope=" + scope +
                ", pttoPromVta=" + pttoPromVta +
                ", pttoPromReal=" + pttoPromReal +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}

