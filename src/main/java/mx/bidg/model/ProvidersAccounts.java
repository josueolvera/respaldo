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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "PROVIDERS_ACCOUNTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ProvidersAccounts implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROVIDER_ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idProviderAccount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedAccounts.class})
    private Accounts idAccount;
    
    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Providers idProvider;

    public ProvidersAccounts() {
    }

    public ProvidersAccounts(Integer idProviderAccount) {
        this.idProviderAccount = idProviderAccount;
    }

    public ProvidersAccounts(Integer idProviderAccount, int idAccessLevel) {
        this.idProviderAccount = idProviderAccount;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdProviderAccount() {
        return idProviderAccount;
    }

    public void setIdProviderAccount(Integer idProviderAccount) {
        this.idProviderAccount = idProviderAccount;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Accounts getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Accounts idAccount) {
        this.idAccount = idAccount;
    }

    public Providers getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Providers idProvider) {
        this.idProvider = idProvider;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProviderAccount != null ? idProviderAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvidersAccounts)) {
            return false;
        }
        ProvidersAccounts other = (ProvidersAccounts) object;
        if ((this.idProviderAccount == null && other.idProviderAccount != null) || (this.idProviderAccount != null && !this.idProviderAccount.equals(other.idProviderAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ProvidersAccounts[ idProviderAccount=" + idProviderAccount + " ]";
    }
    
}
