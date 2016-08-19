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
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "PRODUCTS_TYPES_PRODUCT")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ProductTypesProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUCT_TYPE_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idProductTypeProduct;
    
    @Column(name = "ID_PRODUCT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProduct;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CProducts product;
    
    @Column(name = "ID_ACCOUNTING_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;
    
    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AccountingAccounts accountingAccounts;
    

    public ProductTypesProduct() {
    }

    public ProductTypesProduct(Integer idProductTypeProduct) {
        this.idProductTypeProduct = idProductTypeProduct;
    }

    public Integer getIdProductTypeProduct() {
        return idProductTypeProduct;
    }

    public void setIdProductTypeProduct(Integer idProductTypeProduct) {
        this.idProductTypeProduct = idProductTypeProduct;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CProducts getProduct() {
        return product;
    }

    public void setProduct(CProducts product) {
        this.product = product;
    }
    
    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public AccountingAccounts getAccountingAccounts() {
        return accountingAccounts;
    }

    public void setAccountingAccounts(AccountingAccounts accountingAccounts) {
        this.accountingAccounts = accountingAccounts;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductTypeProduct != null ? idProductTypeProduct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductTypesProduct)) {
            return false;
        }
        ProductTypesProduct other = (ProductTypesProduct) object;
        if ((this.idProductTypeProduct == null && other.idProductTypeProduct != null) || (this.idProductTypeProduct != null && !this.idProductTypeProduct.equals(other.idProductTypeProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ProductTypesProduct[ idProductTypeProduct=" + idProductTypeProduct + " ]";
    }
    
}
