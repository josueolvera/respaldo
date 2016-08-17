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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "REQUEST_TYPES_PRODUCT")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestTypesProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_TYPE_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestTypeProduct;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTIMATIONS_QUANTITY")
    @JsonView(JsonViews.Root.class)
    private Integer estimationsQuantity;
    
    @Column(name = "ID_REQUEST_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategory;
    
    @JoinColumn(name = "ID_REQUEST_CATEGORY", referencedColumnName = "ID_REQUEST_CATEGORY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRequestsCategories requestCategory;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Embedded.class)
    private Integer idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestTypeProduct")
    @JsonView(JsonViews.Embedded.class)
    private List<Requests> requestsList;

    public RequestTypesProduct() {
    }

    public RequestTypesProduct(Integer idRequestTypeProduct) {
        this.idRequestTypeProduct = idRequestTypeProduct;
    }

    public Integer getIdRequestTypeProduct() {
        return idRequestTypeProduct;
    }

    public void setIdRequestTypeProduct(Integer idRequestTypeProduct) {
        this.idRequestTypeProduct = idRequestTypeProduct;
    }

    public Integer getEstimationsQuantity() {
        return estimationsQuantity;
    }

    public void setEstimationsQuantity(Integer estimationsQuantity) {
        this.estimationsQuantity = estimationsQuantity;
    }

    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }
    
    public CRequestsCategories getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(CRequestsCategories requestCategory) {
        this.requestCategory = requestCategory;
    }
    
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestTypeProduct != null ? idRequestTypeProduct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestTypesProduct)) {
            return false;
        }
        RequestTypesProduct other = (RequestTypesProduct) object;
        if ((this.idRequestTypeProduct == null && other.idRequestTypeProduct != null) || (this.idRequestTypeProduct != null && !this.idRequestTypeProduct.equals(other.idRequestTypeProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RequestTypesProduct[ idRequestTypeProduct=" + idRequestTypeProduct + " ]";
    }
    
}
