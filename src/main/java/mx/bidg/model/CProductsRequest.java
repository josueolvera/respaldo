package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
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
public class CProductsRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "ID_PRODUCT_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer idProductRequest;
	
	@Size(max = 45)
    @Column(name = "PRODUCT_REQUEST_NAME")
    @JsonView(JsonViews.Root.class)
	private String productRequestName;
	
	@Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
	private int idAccessLevel;
	
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
	
	//Constructores
	public CProductsRequest(){
	}
	
	public CProductsRequest(Integer idProductRequest){
		this.idProductRequest = idProductRequest;
	}

	//Getters and Setters
    public Integer getIdProductRequest() {
        return idProductRequest;
    }

    public void setIdProductRequest(Integer idProductRequest) {
        this.idProductRequest = idProductRequest;
    }

    public String getProductRequestName() {
        return productRequestName;
    }

    public void setProductRequestName(String productRequestName) {
        this.productRequestName = productRequestName;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CProductsRequest that = (CProductsRequest) o;

        if (idAccessLevel != that.idAccessLevel) return false;
        if (!idProductRequest.equals(that.idProductRequest)) return false;
        if (productRequestName != null ? !productRequestName.equals(that.productRequestName) : that.productRequestName != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return creationDate != null ? creationDate.equals(that.creationDate) : that.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = idProductRequest.hashCode();
        result = 31 * result + (productRequestName != null ? productRequestName.hashCode() : 0);
        result = 31 * result + idAccessLevel;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CProductsRequest{" +
                "idProductRequest=" + idProductRequest +
                ", productRequestName='" + productRequestName + '\'' +
                ", idAccessLevel=" + idAccessLevel +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}