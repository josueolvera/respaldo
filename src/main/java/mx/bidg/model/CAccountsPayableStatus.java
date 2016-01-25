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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_ACCOUNTS_PAYABLE_STATUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CAccountsPayableStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT_PAYABLE_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayableStatus;
    
    @Size(max = 100)
    @Column(name = "ACCOUNTS_PAYABLE_STATUS")
    @JsonView(JsonViews.Root.class)
    private String accountsPayableStatus;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountPayableStatus")
    @JsonView(JsonViews.Embedded.class)
    private List<AccountsPayable> accountsPayableList;

    public CAccountsPayableStatus() {
    }

    public CAccountsPayableStatus(Integer idAccountPayableStatus) {
        this.idAccountPayableStatus = idAccountPayableStatus;
    }

    public Integer getIdAccountPayableStatus() {
        return idAccountPayableStatus;
    }

    public void setIdAccountPayableStatus(Integer idAccountPayableStatus) {
        this.idAccountPayableStatus = idAccountPayableStatus;
    }

    public String getAccountsPayableStatus() {
        return accountsPayableStatus;
    }

    public void setAccountsPayableStatus(String accountsPayableStatus) {
        this.accountsPayableStatus = accountsPayableStatus;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<AccountsPayable> getAccountsPayableList() {
        return accountsPayableList;
    }

    public void setAccountsPayableList(List<AccountsPayable> accountsPayableList) {
        this.accountsPayableList = accountsPayableList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountPayableStatus != null ? idAccountPayableStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAccountsPayableStatus)) {
            return false;
        }
        CAccountsPayableStatus other = (CAccountsPayableStatus) object;
        if ((this.idAccountPayableStatus == null && other.idAccountPayableStatus != null) || (this.idAccountPayableStatus != null && !this.idAccountPayableStatus.equals(other.idAccountPayableStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAccountsPayableStatus[ idAccountPayableStatus=" + idAccountPayableStatus + " ]";
    }
    
}
