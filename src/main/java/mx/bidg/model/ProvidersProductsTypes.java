package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by jolvera on 30/05/16.
 */
@Entity
@DynamicUpdate
@Table(name = "PROVIDERS_PRODUCTS_TYPES")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ProvidersProductsTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVIDERS_PRODUCTS_TYPES")
    @JsonView(JsonViews.Root.class)
    private int idProvidersProductsTypes;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Column(name = "ID_PRODUCT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProductType;

    @Column(name = "ID_PROVIDER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @JoinColumn(name = "ID_PRODUCT_TYPE", referencedColumnName = "ID_PRODUCT_TYPE")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedAccounts.class})
    private CProductTypes cProductType;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Providers provider;

    public ProvidersProductsTypes() {
    }

    public ProvidersProductsTypes(int idProvidersProductsTypes){
        this.idProvidersProductsTypes = idProvidersProductsTypes;
    }

    public ProvidersProductsTypes(int idProvidersProductsTypes , int idAccessLevel ){
        this.idProvidersProductsTypes = idProvidersProductsTypes;
        this.idAccessLevel = idAccessLevel;
    }

    public int getIdProvidersProductsTypes() {
        return idProvidersProductsTypes;
    }

    public void setIdProvidersProductsTypes(int idProvidersProductsTypes) {
        this.idProvidersProductsTypes = idProvidersProductsTypes;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(Integer idProductType) {
        this.idProductType = idProductType;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public CProductTypes getcProductType() {
        return cProductType;
    }

    public void setcProductType(CProductTypes cProductType) {
        this.cProductType = cProductType;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "ProvidersProductsTypes{" +
                "idProvidersProductsTypes=" + idProvidersProductsTypes +
                ", idAccessLevel=" + idAccessLevel +
                ", idProductType=" + idProductType +
                ", idProvider=" + idProvider +
                ", cProductType=" + cProductType +
                ", provider=" + provider +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvidersProductsTypes that = (ProvidersProductsTypes) o;

        if (idProvidersProductsTypes != that.idProvidersProductsTypes) return false;
        if (idAccessLevel != that.idAccessLevel) return false;
        if (idProductType != null ? !idProductType.equals(that.idProductType) : that.idProductType != null)
            return false;
        if (idProvider != null ? !idProvider.equals(that.idProvider) : that.idProvider != null) return false;
        if (cProductType != null ? !cProductType.equals(that.cProductType) : that.cProductType != null) return false;
        return provider != null ? provider.equals(that.provider) : that.provider == null;

    }

    @Override
    public int hashCode() {
        int result = idProvidersProductsTypes;
        result = 31 * result + idAccessLevel;
        result = 31 * result + (idProductType != null ? idProductType.hashCode() : 0);
        result = 31 * result + (idProvider != null ? idProvider.hashCode() : 0);
        result = 31 * result + (cProductType != null ? cProductType.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }
}
