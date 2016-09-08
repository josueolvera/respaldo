package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "PROVIDERS_BUDGET_SUBCATEGORIES")
public class ProvidersBudgetSubcategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVIDER_BUDGET_SUBCATEGORY")
    @JsonView(JsonViews.Root.class)
    private int idProviderBudgetSubcategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Column(name = "ID_BUDGET_SUBCATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;

    @Column(name = "ID_PROVIDER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @JoinColumn(name = "ID_BUDGET_SUBCATEGORY", referencedColumnName = "ID_BUDGET_SUBCATEGORY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CBudgetSubcategories budgetSubcategory;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Providers provider;
    
    @Transient
    @JsonView(JsonViews.Embedded.class)
    private Providers providers;

    public ProvidersBudgetSubcategories() {
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @JsonBackReference
    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public int getIdProviderBudgetSubcategory() {
        return idProviderBudgetSubcategory;
    }

    public void setIdProviderBudgetSubcategory(int idProviderBudgetSubcategory) {
        this.idProviderBudgetSubcategory = idProviderBudgetSubcategory;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public CBudgetSubcategories getBudgetSubcategory() {
        return budgetSubcategory;
    }

    public void setBudgetSubcategory(CBudgetSubcategories budgetSubcategory) {
        this.budgetSubcategory = budgetSubcategory;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvidersBudgetSubcategories that = (ProvidersBudgetSubcategories) o;

        if (idProviderBudgetSubcategory != that.idProviderBudgetSubcategory) return false;
        if (idAccessLevel != that.idAccessLevel) return false;
        if (idBudgetSubcategory != null ? !idBudgetSubcategory.equals(that.idBudgetSubcategory) : that.idBudgetSubcategory != null)
            return false;
        if (idProvider != null ? !idProvider.equals(that.idProvider) : that.idProvider != null) return false;
        if (budgetSubcategory != null ? !budgetSubcategory.equals(that.budgetSubcategory) : that.budgetSubcategory != null)
            return false;
        return provider != null ? provider.equals(that.provider) : that.provider == null;

    }

    @Override
    public int hashCode() {
        int result = idProviderBudgetSubcategory;
        result = 31 * result + idAccessLevel;
        result = 31 * result + (idBudgetSubcategory != null ? idBudgetSubcategory.hashCode() : 0);
        result = 31 * result + (idProvider != null ? idProvider.hashCode() : 0);
        result = 31 * result + (budgetSubcategory != null ? budgetSubcategory.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProvidersBudgetSubcategories{" +
                "idProviderBudgetSubcategory=" + idProviderBudgetSubcategory +
                ", idAccessLevel=" + idAccessLevel +
                ", idBudgetSubcategory=" + idBudgetSubcategory +
                ", idProvider=" + idProvider +
                ", budgetSubcategory=" + budgetSubcategory +
                ", provider=" + provider +
                '}';
    }
}
