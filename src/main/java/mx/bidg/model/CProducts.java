package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_PRODUCTS")

public class CProducts implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idProduct;

    @Size(max = 100)
    @Column(name = "PRODUCT")
    @JsonView(JsonViews.Root.class)
    private String product;

    @Size(max = 20)
    @Column(name = "SKU")
    @JsonView(JsonViews.Root.class)
    private String sku;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @ManyToMany(mappedBy = "products")
    private Set<CBudgetSubcategories> budgetSubcategories;

    public CProducts() {
    }

    public CProducts(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Set<CBudgetSubcategories> getBudgetSubcategories() {
        return budgetSubcategories;
    }

    public void setBudgetSubcategories(Set<CBudgetSubcategories> budgetSubcategories) {
        this.budgetSubcategories = budgetSubcategories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduct != null ? idProduct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CProducts)) {
            return false;
        }
        CProducts other = (CProducts) object;
        if ((this.idProduct == null && other.idProduct != null) || (this.idProduct != null && !this.idProduct.equals(other.idProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CProductsDao[ idProduct=" + idProduct + " ]";
    }
    
}
