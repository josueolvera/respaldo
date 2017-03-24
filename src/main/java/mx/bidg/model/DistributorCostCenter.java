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

    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Column(name = "ID_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_BUSINESS_LINE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBussinessLine;

    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private AccountingAccounts accountingAccounts;

    @JoinColumn(name = "ID_COST_CENTER", referencedColumnName = "ID_COST_CENTER")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CCostCenter costCenter;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CDistributors distributors;

    @JoinColumn(name = "ID_BUSINESS_LINE", referencedColumnName = "ID_BUSINESS_LINE")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBussinessLine cBussinessLine;

    public DistributorCostCenter() {
    }

    public DistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public DistributorCostCenter(Integer idDistributorCostCenter, LocalDateTime creationDate) {
        this.idDistributorCostCenter = idDistributorCostCenter;
        this.creationDate = creationDate;
    }

    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
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

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdBussinessLine() {
        return idBussinessLine;
    }

    public void setIdBussinessLine(Integer idBussinessLine) {
        this.idBussinessLine = idBussinessLine;
    }

    public AccountingAccounts getAccountingAccounts() {
        return accountingAccounts;
    }

    public void setAccountingAccounts(AccountingAccounts accountingAccounts) {
        this.accountingAccounts = accountingAccounts;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CCostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public CDistributors getDistributors() {
        return distributors;
    }

    public void setDistributors(CDistributors distributors) {
        this.distributors = distributors;
    }

    public CBussinessLine getcBussinessLine() {
        return cBussinessLine;
    }

    public void setcBussinessLine(CBussinessLine cBussinessLine) {
        this.cBussinessLine = cBussinessLine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistributorCostCenter != null ? idDistributorCostCenter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistributorCostCenter)) {
            return false;
        }
        DistributorCostCenter other = (DistributorCostCenter) object;
        if ((this.idDistributorCostCenter == null && other.idDistributorCostCenter != null) || (this.idDistributorCostCenter != null && !this.idDistributorCostCenter.equals(other.idDistributorCostCenter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DistributorCostCenter[ idDistributorCostCenter=" + idDistributorCostCenter + " ]";
    }

}
