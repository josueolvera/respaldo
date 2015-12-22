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

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "PRODUCT_TYPES_PRODUCT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ProductTypesProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUCT_TYPE_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idProductTypeProduct;
    
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CProducts idProduct;
    
    @JoinColumn(name = "ID_PRODUCT_TYPE", referencedColumnName = "ID_PRODUCT_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CProductTypes idProductType;
    
    @JoinColumn(name = "ID_ACCESS_LEVEL", referencedColumnName = "ID_ACCESS_LEVEL")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AccessLevel idAccessLevel;

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

    public CProducts getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(CProducts idProduct) {
        this.idProduct = idProduct;
    }

    public CProductTypes getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(CProductTypes idProductType) {
        this.idProductType = idProductType;
    }

    public AccessLevel getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(AccessLevel idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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
