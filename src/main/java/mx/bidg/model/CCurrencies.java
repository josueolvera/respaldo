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

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_CURRENCIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CCurrencies implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CURRENCY")
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    
    @Size(max = 50)
    @Column(name = "CURRENCY")
    @JsonView(JsonViews.Root.class)
    private String currency;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency")
    @JsonView(JsonViews.Embedded.class)
    private List<AccountsPayable> accountsPayableList;

    public CCurrencies() {
    }

    public CCurrencies(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurrency != null ? idCurrency.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CCurrencies)) {
            return false;
        }
        CCurrencies other = (CCurrencies) object;
        if ((this.idCurrency == null && other.idCurrency != null) || (this.idCurrency != null && !this.idCurrency.equals(other.idCurrency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CCurrencies[ idCurrency=" + idCurrency + " ]";
    }

    public List<AccountsPayable> getAccountsPayableList() {
        return accountsPayableList;
    }

    public void setAccountsPayableList(List<AccountsPayable> accountsPayableList) {
        this.accountsPayableList = accountsPayableList;
    }
    
}
