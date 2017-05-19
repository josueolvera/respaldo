package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CProductsRequest;
import mx.bidg.model.DistributorCostCenter;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Table(name = "C_PRODUCTS_REQUEST")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RoleProductRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "ID_ROLE_PRODUCT_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer idRoleProductRequest;
	
	@Column(name = "ID_DISTRIBUTOR_COST_CENTER", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idDistributorCostCenter;
	
	@Column(name = "ID_PRODUCT_REQUEST", insertable = false, updatable = false)
	@JsonView(JsonViews.Root.class)
	private int idProductRequest;
	
	@Size(max = 30)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;
	
	//Relaciones
	@JoinColumn(name = "ID_DISTRIBUTOR_COST_CENTER", referencedColumnName = "ID_DISTRIBUTOR_COST_CENTER")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private DistributorCostCenter distributorCostCenter;
	
	@JoinColumn(name = "ID_PRODUCT_REQUEST", referencedColumnName = "ID_PRODUCT_REQUEST")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CProductsRequest cProductsRequest;
	
	//Constructores
	public RoleProductRequest(){
	}
	
	public RoleProductRequest(Integer idRoleProductRequest){
		this.idRoleProductRequest = idRoleProductRequest;
	}

	//Getters and Setters
    public Integer getIdRoleProductRequest() {
        return idRoleProductRequest;
    }

    public void setIdRoleProductRequest(Integer idRoleProductRequest) {
        this.idRoleProductRequest = idRoleProductRequest;
    }

    public int getIdDistributorCostCenter() {
        return idDistributorCostCenter;
    }

    public void setIdDistributorCostCenter(int idDistributorCostCenter) {
        this.idDistributorCostCenter = idDistributorCostCenter;
    }

    public int getIdProductRequest() {
        return idProductRequest;
    }

    public void setIdProductRequest(int idProductRequest) {
        this.idProductRequest = idProductRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DistributorCostCenter getDistributorCostCenter() {
        return distributorCostCenter;
    }

    public void setDistributorCostCenter(DistributorCostCenter distributorCostCenter) {
        this.distributorCostCenter = distributorCostCenter;
    }

    public CProductsRequest getcProductsRequest() {
        return cProductsRequest;
    }

    public void setcProductsRequest(CProductsRequest cProductsRequest) {
        this.cProductsRequest = cProductsRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleProductRequest that = (RoleProductRequest) o;

        if (idDistributorCostCenter != that.idDistributorCostCenter) return false;
        if (idProductRequest != that.idProductRequest) return false;
        if (!idRoleProductRequest.equals(that.idRoleProductRequest)) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (distributorCostCenter != null ? !distributorCostCenter.equals(that.distributorCostCenter) : that.distributorCostCenter != null)
            return false;
        return cProductsRequest != null ? cProductsRequest.equals(that.cProductsRequest) : that.cProductsRequest == null;
    }

    @Override
    public int hashCode() {
        int result = idRoleProductRequest.hashCode();
        result = 31 * result + idDistributorCostCenter;
        result = 31 * result + idProductRequest;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (distributorCostCenter != null ? distributorCostCenter.hashCode() : 0);
        result = 31 * result + (cProductsRequest != null ? cProductsRequest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleProductRequest{" +
                "idRoleProductRequest=" + idRoleProductRequest +
                ", idDistributorCostCenter=" + idDistributorCostCenter +
                ", idProductRequest=" + idProductRequest +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", distributorCostCenter=" + distributorCostCenter +
                ", cProductsRequest=" + cProductsRequest +
                '}';
    }
}