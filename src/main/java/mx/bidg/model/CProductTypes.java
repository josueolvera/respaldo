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

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_PRODUCT_TYPES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CProductTypes implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUCT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idProductType;

    @Size(max = 100)
    @Column(name = "PRODUCT_TYPE")
    @JsonView(JsonViews.Root.class)
    private String productType;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Column(name = "ID_PROVIDER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;
    
    @Column(name = "ID_BUDGET_SUBCATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;
    
    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private Providers provider;
    
    @JoinColumn(name = "ID_BUDGET_SUBCATEGORY", referencedColumnName = "ID_BUDGET_SUBCATEGORY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBudgetSubcategories budgetSubcategory;
    
    @OneToMany(mappedBy = "productType")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestTypesProduct> requestTypesProductList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productType")
    @JsonView(JsonViews.Embedded.class)
    private List<ProductTypesProduct> productTypesProductList;

    public CProductTypes() {
    }

    public CProductTypes(Integer idProductType) {
        this.idProductType = idProductType;
    }

    public Integer getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(Integer idProductType) {
        this.idProductType = idProductType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public CBudgetSubcategories getBudgetSubcategory() {
        return budgetSubcategory;
    }

    public void setBudgetSubcategory(CBudgetSubcategories budgetSubcategory) {
        this.budgetSubcategory = budgetSubcategory;
    }

    public List<RequestTypesProduct> getRequestTypesProductList() {
        return requestTypesProductList;
    }

    public void setRequestTypesProductList(List<RequestTypesProduct> requestTypesProductList) {
        this.requestTypesProductList = requestTypesProductList;
    }

    public List<ProductTypesProduct> getProductTypesProductList() {
        return productTypesProductList;
    }

    public void setProductTypesProductList(List<ProductTypesProduct> productTypesProductList) {
        this.productTypesProductList = productTypesProductList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductType != null ? idProductType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CProductTypes)) {
            return false;
        }
        CProductTypes other = (CProductTypes) object;
        if ((this.idProductType == null && other.idProductType != null) || (this.idProductType != null && !this.idProductType.equals(other.idProductType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CProductTypes[ idProductType=" + idProductType + " ]";
    }
    
}
