package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "REQUEST_PRODUCTS", schema = "BIDG_DBA", catalog = "")
public class RequestProducts {
    private int idRequestProduct;
    private int idRequest;
    private Integer idRoleProductRequest;
    private Timestamp quantity;
    private int idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Requests requestsByIdRequest;
    private RoleProductRequest roleProductRequestByIdRoleProductRequest;

    @Id
    @Column(name = "ID_REQUEST_PRODUCT", nullable = false)
    public int getIdRequestProduct() {
        return idRequestProduct;
    }

    public void setIdRequestProduct(int idRequestProduct) {
        this.idRequestProduct = idRequestProduct;
    }

    @Basic
    @Column(name = "ID_REQUEST", nullable = false)
    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    @Basic
    @Column(name = "ID_ROLE_PRODUCT_REQUEST", nullable = true)
    public Integer getIdRoleProductRequest() {
        return idRoleProductRequest;
    }

    public void setIdRoleProductRequest(Integer idRoleProductRequest) {
        this.idRoleProductRequest = idRoleProductRequest;
    }

    @Basic
    @Column(name = "QUANTITY", nullable = false)
    public Timestamp getQuantity() {
        return quantity;
    }

    public void setQuantity(Timestamp quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = false)
    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "CREATION_DATE", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestProducts that = (RequestProducts) o;

        if (idRequestProduct != that.idRequestProduct) return false;
        if (idRequest != that.idRequest) return false;
        if (idAccessLevel != that.idAccessLevel) return false;
        if (idRoleProductRequest != null ? !idRoleProductRequest.equals(that.idRoleProductRequest) : that.idRoleProductRequest != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRequestProduct;
        result = 31 * result + idRequest;
        result = 31 * result + (idRoleProductRequest != null ? idRoleProductRequest.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + idAccessLevel;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST", nullable = false)
    public Requests getRequestsByIdRequest() {
        return requestsByIdRequest;
    }

    public void setRequestsByIdRequest(Requests requestsByIdRequest) {
        this.requestsByIdRequest = requestsByIdRequest;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ROLE_PRODUCT_REQUEST", referencedColumnName = "ID_ROLE_PRODUCT_REQUEST")
    public RoleProductRequest getRoleProductRequestByIdRoleProductRequest() {
        return roleProductRequestByIdRoleProductRequest;
    }

    public void setRoleProductRequestByIdRoleProductRequest(RoleProductRequest roleProductRequestByIdRoleProductRequest) {
        this.roleProductRequestByIdRoleProductRequest = roleProductRequestByIdRoleProductRequest;
    }
}
