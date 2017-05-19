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
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "DISTRIBUTOR_COST_CENTER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class DistributorCostCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DISTRIBUTOR_COST_CENTER")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorCostCenter;

    @Column(name = "ID_BUSINESS_LINE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBussinessLine;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;

    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Column(name = "ID_MODULE_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idModuleStatus;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    //Relaciones
    @JoinColumn(name = "ID_BUSINESS_LINE", referencedColumnName = "ID_BUSINESS_LINE")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBussinessLine cBussinessLine;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CDistributors distributors;

    @JoinColumn(name = "ID_COST_CENTER", referencedColumnName = "ID_COST_CENTER")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CCostCenter costCenter;

    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private AccountingAccounts accountingAccounts;

    @JoinColumn(name = "ID_MODULE_STATUS", referencedColumnName = "ID_MODULE_STATUS")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CModuleStatus cModuleStatus;

    //Constructores
    public DistributorCostCenter() {
    }

    public DistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public DistributorCostCenter(Integer idDistributorCostCenter, LocalDateTime creationDate) {
        this.idDistributorCostCenter = idDistributorCostCenter;
        this.creationDate = creationDate;
    }

    //Getters and Setters
    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public Integer getIdBussinessLine() {
        return idBussinessLine;
    }

    public void setIdBussinessLine(Integer idBussinessLine) {
        this.idBussinessLine = idBussinessLine;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public Integer getIdModuleStatus() {
        return idModuleStatus;
    }

    public void setIdModuleStatus(Integer idModuleStatus) {
        this.idModuleStatus = idModuleStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public CBussinessLine getcBussinessLine() {
        return cBussinessLine;
    }

    public void setcBussinessLine(CBussinessLine cBussinessLine) {
        this.cBussinessLine = cBussinessLine;
    }

    public CDistributors getDistributors() {
        return distributors;
    }

    public void setDistributors(CDistributors distributors) {
        this.distributors = distributors;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CCostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public AccountingAccounts getAccountingAccounts() {
        return accountingAccounts;
    }

    public void setAccountingAccounts(AccountingAccounts accountingAccounts) {
        this.accountingAccounts = accountingAccounts;
    }

    public CModuleStatus getcModuleStatus() {
        return cModuleStatus;
    }

    public void setcModuleStatus(CModuleStatus cModuleStatus) {
        this.cModuleStatus = cModuleStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistributorCostCenter that = (DistributorCostCenter) o;

        if (!idDistributorCostCenter.equals(that.idDistributorCostCenter)) return false;
        if (idBussinessLine != null ? !idBussinessLine.equals(that.idBussinessLine) : that.idBussinessLine != null)
            return false;
        if (idDistributor != null ? !idDistributor.equals(that.idDistributor) : that.idDistributor != null)
            return false;
        if (idCostCenter != null ? !idCostCenter.equals(that.idCostCenter) : that.idCostCenter != null) return false;
        if (idAccountingAccount != null ? !idAccountingAccount.equals(that.idAccountingAccount) : that.idAccountingAccount != null)
            return false;
        if (idModuleStatus != null ? !idModuleStatus.equals(that.idModuleStatus) : that.idModuleStatus != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (cBussinessLine != null ? !cBussinessLine.equals(that.cBussinessLine) : that.cBussinessLine != null)
            return false;
        if (distributors != null ? !distributors.equals(that.distributors) : that.distributors != null) return false;
        if (costCenter != null ? !costCenter.equals(that.costCenter) : that.costCenter != null) return false;
        if (accountingAccounts != null ? !accountingAccounts.equals(that.accountingAccounts) : that.accountingAccounts != null)
            return false;
        return cModuleStatus != null ? cModuleStatus.equals(that.cModuleStatus) : that.cModuleStatus == null;
    }

    @Override
    public int hashCode() {
        int result = idDistributorCostCenter.hashCode();
        result = 31 * result + (idBussinessLine != null ? idBussinessLine.hashCode() : 0);
        result = 31 * result + (idDistributor != null ? idDistributor.hashCode() : 0);
        result = 31 * result + (idCostCenter != null ? idCostCenter.hashCode() : 0);
        result = 31 * result + (idAccountingAccount != null ? idAccountingAccount.hashCode() : 0);
        result = 31 * result + (idModuleStatus != null ? idModuleStatus.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (cBussinessLine != null ? cBussinessLine.hashCode() : 0);
        result = 31 * result + (distributors != null ? distributors.hashCode() : 0);
        result = 31 * result + (costCenter != null ? costCenter.hashCode() : 0);
        result = 31 * result + (accountingAccounts != null ? accountingAccounts.hashCode() : 0);
        result = 31 * result + (cModuleStatus != null ? cModuleStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DistributorCostCenter{" +
                "idDistributorCostCenter=" + idDistributorCostCenter +
                ", idBussinessLine=" + idBussinessLine +
                ", idDistributor=" + idDistributor +
                ", idCostCenter=" + idCostCenter +
                ", idAccountingAccount=" + idAccountingAccount +
                ", idModuleStatus=" + idModuleStatus +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", cBussinessLine=" + cBussinessLine +
                ", distributors=" + distributors +
                ", costCenter=" + costCenter +
                ", accountingAccounts=" + accountingAccounts +
                ", cModuleStatus=" + cModuleStatus +
                '}';
    }
}
