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
@Table(name = "REQUEST_PRODUCTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestProducts implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestProduct;
    
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Requests idRequest;
    
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CProducts idProduct;
    
    @JoinColumn(name = "ID_ACCESS_LEVEL", referencedColumnName = "ID_ACCESS_LEVEL")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AccessLevel idAccessLevel;

    public RequestProducts() {
    }

    public RequestProducts(Integer idRequestProduct) {
        this.idRequestProduct = idRequestProduct;
    }

    public Integer getIdRequestProduct() {
        return idRequestProduct;
    }

    public void setIdRequestProduct(Integer idRequestProduct) {
        this.idRequestProduct = idRequestProduct;
    }

    public Requests getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Requests idRequest) {
        this.idRequest = idRequest;
    }

    public CProducts getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(CProducts idProduct) {
        this.idProduct = idProduct;
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
        hash += (idRequestProduct != null ? idRequestProduct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestProducts)) {
            return false;
        }
        RequestProducts other = (RequestProducts) object;
        if ((this.idRequestProduct == null && other.idRequestProduct != null) || (this.idRequestProduct != null && !this.idRequestProduct.equals(other.idRequestProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RequestProducts[ idRequestProduct=" + idRequestProduct + " ]";
    }
    
}
