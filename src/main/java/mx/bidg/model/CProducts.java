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
@Table(name = "C_PRODUCTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CProducts implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idProduct;

    @Size(max = 100)
    @Column(name = "PRODUCT")
    @JsonView(JsonViews.Root.class)
    private String product;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestProducts> requestProductsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonView(JsonViews.Embedded.class)
    private List<ProductTypesProduct> productTypesProductList;

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
    
    public List<RequestProducts> getRequestProductsList() {
        return requestProductsList;
    }

    public void setRequestProductsList(List<RequestProducts> requestProductsList) {
        this.requestProductsList = requestProductsList;
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
