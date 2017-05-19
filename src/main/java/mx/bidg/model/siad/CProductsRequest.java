package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "C_PRODUCTS_REQUEST", schema = "BIDG_DBA", catalog = "")
public class CProductsRequest {
    private int idProductsRequest;
    private String productRequestName;
    private Integer idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Collection<RoleProductRequest> roleProductRequestsByIdProductsRequest;

    @Id
    @Column(name = "ID_PRODUCTS_REQUEST", nullable = false)
    public int getIdProductsRequest() {
        return idProductsRequest;
    }

    public void setIdProductsRequest(int idProductsRequest) {
        this.idProductsRequest = idProductsRequest;
    }

    @Basic
    @Column(name = "PRODUCT_REQUEST_NAME", nullable = true, length = 100)
    public String getProductRequestName() {
        return productRequestName;
    }

    public void setProductRequestName(String productRequestName) {
        this.productRequestName = productRequestName;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = true)
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
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
    @Column(name = "CREATION_DATE", nullable = false)
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

        CProductsRequest that = (CProductsRequest) o;

        if (idProductsRequest != that.idProductsRequest) return false;
        if (productRequestName != null ? !productRequestName.equals(that.productRequestName) : that.productRequestName != null)
            return false;
        if (idAccessLevel != null ? !idAccessLevel.equals(that.idAccessLevel) : that.idAccessLevel != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProductsRequest;
        result = 31 * result + (productRequestName != null ? productRequestName.hashCode() : 0);
        result = 31 * result + (idAccessLevel != null ? idAccessLevel.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cProductsRequestByIdProductRequest")
    public Collection<RoleProductRequest> getRoleProductRequestsByIdProductsRequest() {
        return roleProductRequestsByIdProductsRequest;
    }

    public void setRoleProductRequestsByIdProductsRequest(Collection<RoleProductRequest> roleProductRequestsByIdProductsRequest) {
        this.roleProductRequestsByIdProductsRequest = roleProductRequestsByIdProductsRequest;
    }
}
