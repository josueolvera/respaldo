/**
 *
 * @author rafael
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "PROPERTIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Properties implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROPERTY")
    @JsonView(JsonViews.Root.class)
    private Integer idProperty;

    @Column(name = "ID_VALUE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idValue;

    @Column(name = "ID_STOCK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idStock;

    @Column(name = "ID_ATTRIBUTES_ARTICLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAttributesArticle;

    @NotNull
    @JoinColumn(name = "ID_VALUE", referencedColumnName = "ID_VALUE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CValues value;

    @NotNull
    @JoinColumn(name = "ID_STOCK", referencedColumnName = "ID_STOCK")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Stocks stocks;

    @NotNull
    @JoinColumn(name = "ID_ATTRIBUTES_ARTICLE", referencedColumnName = "ID_ATTRIBUTE_ARTICLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private AttributesArticles attributesArticles;

    public Properties() {
    }

    public Properties(@Min(1) Integer idProperty) {
        this.idProperty = idProperty;
    }

    public Properties(Integer idProperty, int idStock, int idAttributesArticle) {
        this.idProperty = idProperty;
        this.idStock = idStock;
        this.idAttributesArticle = idAttributesArticle;
    }

    public Integer getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(Integer idProperty) {
        this.idProperty = idProperty;
    }

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdAttributesArticle() {
        return idAttributesArticle;
    }

    public void setIdAttributesArticle(int idAttributesArticle) {
        this.idAttributesArticle = idAttributesArticle;
    }

    public Integer getIdValue() {
        return idValue;
    }

    public void setIdValue(Integer idValue) {
        this.idValue = idValue;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public AttributesArticles getAttributesArticles() {
        return attributesArticles;
    }

    public void setAttributesArticles(AttributesArticles attributesArticles) {
        this.attributesArticles = attributesArticles;
    }

    public CValues getValue() {
        return value;
    }

    public void setValue(CValues value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProperty != null ? idProperty.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Properties)) {
            return false;
        }
        Properties other = (Properties) object;
        if ((this.idProperty == null && other.idProperty != null) || (this.idProperty != null && !this.idProperty.equals(other.idProperty))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Properties[ idProperty=" + idProperty + " ]";
    }

}
