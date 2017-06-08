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
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "REQUEST_PRODUCTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_PRODUCT")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestProduct;

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_ROLE_PRODUCT_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRoleProductRequest;

    @Column(name = "QUANTITY")
    @JsonView(JsonViews.Root.class)
    private Integer quantity;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @JoinColumn(name = "ID_ROLE_PRODUCT_REQUEST", referencedColumnName = "ID_ROLE_PRODUCT_REQUEST")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private RoleProductRequest roleProductRequest;

    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Requests request;


    public RequestProducts() {
    }


    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Requests getRequest() {
        return request;
    }

    public void setRequest(Requests request) {
        this.request = request;
    }

    public Integer getIdRequestProduct() {
        return idRequestProduct;
    }

    public void setIdRequestProduct(Integer idRequestProduct) {
        this.idRequestProduct = idRequestProduct;
    }

    public Integer getIdRoleProductRequest() {
        return idRoleProductRequest;
    }

    public void setIdRoleProductRequest(Integer idRoleProductRequest) {
        this.idRoleProductRequest = idRoleProductRequest;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public RoleProductRequest getRoleProductRequest() {
        return roleProductRequest;
    }

    public void setRoleProductRequest(RoleProductRequest roleProductRequest) {
        this.roleProductRequest = roleProductRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestProducts)) return false;

        RequestProducts that = (RequestProducts) o;

        if (idRequestProduct != null ? !idRequestProduct.equals(that.idRequestProduct) : that.idRequestProduct != null)
            return false;
        if (idRequest != null ? !idRequest.equals(that.idRequest) : that.idRequest != null) return false;
        return idRoleProductRequest != null ? idRoleProductRequest.equals(that.idRoleProductRequest) : that.idRoleProductRequest == null;
    }

    @Override
    public int hashCode() {
        int result = idRequestProduct != null ? idRequestProduct.hashCode() : 0;
        result = 31 * result + (idRequest != null ? idRequest.hashCode() : 0);
        result = 31 * result + (idRoleProductRequest != null ? idRoleProductRequest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RequestProducts[ idRequestProduct=" + idRequestProduct + " ]";
    }


}
