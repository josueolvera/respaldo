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
@Table(name = "C_ACCOUNTS_TYPES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CAccountsTypes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountType;
    
    @Size(max = 45)
    @Column(name = "ACCOUNT_TYPE")
    @JsonView(JsonViews.Root.class)
    private String accountType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountType")
    @JsonView(JsonViews.Embedded.class)
    private List<Accounts> accountsList;

    public CAccountsTypes() {
    }

    public CAccountsTypes(Integer idAccountType) {
        this.idAccountType = idAccountType;
    }

    public Integer getIdAccountType() {
        return idAccountType;
    }

    public void setIdAccountType(Integer idAccountType) {
        this.idAccountType = idAccountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Accounts> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(List<Accounts> accountsList) {
        this.accountsList = accountsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountType != null ? idAccountType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAccountsTypes)) {
            return false;
        }
        CAccountsTypes other = (CAccountsTypes) object;
        if ((this.idAccountType == null && other.idAccountType != null) || (this.idAccountType != null && !this.idAccountType.equals(other.idAccountType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAccountsTypes[ idAccountType=" + idAccountType + " ]";
    }
    
}
