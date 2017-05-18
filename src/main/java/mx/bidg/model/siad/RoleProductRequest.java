package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "ROLE_PRODUCT_REQUEST", schema = "BIDG_DBA", catalog = "")
public class RoleProductRequest {
    private int idRoleProductRequest;
    private Integer idDistributorCostCenter;
    private Integer idProductRequest;
    private String username;
    private Timestamp creationDate;
    private Collection<RequestProducts> requestProductsByIdRoleProductRequest;
    private DistributorCostCenter distributorCostCenterByIdDistributorCostCenter;
    private CProductsRequest cProductsRequestByIdProductRequest;

    @Id
    @Column(name = "ID_ROLE_PRODUCT_REQUEST", nullable = false)
    public int getIdRoleProductRequest() {
        return idRoleProductRequest;
    }

    public void setIdRoleProductRequest(int idRoleProductRequest) {
        this.idRoleProductRequest = idRoleProductRequest;
    }

    @Basic
    @Column(name = "ID_DISTRIBUTOR_COST_CENTER", nullable = true)
    public Integer getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(Integer idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    @Basic
    @Column(name = "ID_PRODUCT_REQUEST", nullable = true)
    public Integer getIdProductRequest() {
        return idProductRequest;
    }

    public void setIdProductRequest(Integer idProductRequest) {
        this.idProductRequest = idProductRequest;
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

        RoleProductRequest that = (RoleProductRequest) o;

        if (idRoleProductRequest != that.idRoleProductRequest) return false;
        if (idDistributorCostCenter != null ? !idDistributorCostCenter.equals(that.idDistributorCostCenter) : that.idDistributorCostCenter != null)
            return false;
        if (idProductRequest != null ? !idProductRequest.equals(that.idProductRequest) : that.idProductRequest != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRoleProductRequest;
        result = 31 * result + (idDistributorCostCenter != null ? idDistributorCostCenter.hashCode() : 0);
        result = 31 * result + (idProductRequest != null ? idProductRequest.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "roleProductRequestByIdRoleProductRequest")
    public Collection<RequestProducts> getRequestProductsByIdRoleProductRequest() {
        return requestProductsByIdRoleProductRequest;
    }

    public void setRequestProductsByIdRoleProductRequest(Collection<RequestProducts> requestProductsByIdRoleProductRequest) {
        this.requestProductsByIdRoleProductRequest = requestProductsByIdRoleProductRequest;
    }

    @ManyToOne
    @JoinColumn(name = "ID_DISTRIBUTOR_COST_CENTER", referencedColumnName = "ID_DISTRIBUTOR_COST_CENTER")
    public DistributorCostCenter getDistributorCostCenterByIdDistributorCostCenter() {
        return distributorCostCenterByIdDistributorCostCenter;
    }

    public void setDistributorCostCenterByIdDistributorCostCenter(DistributorCostCenter distributorCostCenterByIdDistributorCostCenter) {
        this.distributorCostCenterByIdDistributorCostCenter = distributorCostCenterByIdDistributorCostCenter;
    }

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT_REQUEST", referencedColumnName = "ID_PRODUCTS_REQUEST")
    public CProductsRequest getcProductsRequestByIdProductRequest() {
        return cProductsRequestByIdProductRequest;
    }

    public void setcProductsRequestByIdProductRequest(CProductsRequest cProductsRequestByIdProductRequest) {
        this.cProductsRequestByIdProductRequest = cProductsRequestByIdProductRequest;
    }
}
