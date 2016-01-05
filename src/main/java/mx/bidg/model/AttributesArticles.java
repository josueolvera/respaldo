/**
 *
 * @author rafael
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ATTRIBUTES_ARTICLES")
public class AttributesArticles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATTRIBUTE_ARTICLE")
    private Integer idAttributeArticle;

    @Column(name = "ID_ARTICLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idArticle;

    @Column(name = "ID_ATTRIBUTE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAttribute;

    @Column(name = "ID_DATA_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDataType;

    @JoinColumn(name = "ID_DATA_TYPE", referencedColumnName = "ID_DATA_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CDataTypes dataTypes;

    @JoinColumn(name = "ID_ARTICLE", referencedColumnName = "ID_ARTICLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CArticles articles;

    @JoinColumn(name = "ID_ATTRIBUTE", referencedColumnName = "ID_ATTRIBUTE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CAttributes attributes;

    public AttributesArticles() {
    }

    public AttributesArticles(Integer idAttributeArticle) {
        this.idAttributeArticle = idAttributeArticle;
    }

    public Integer getIdAttributeArticle() {
        return idAttributeArticle;
    }

    public void setIdAttributeArticle(Integer idAttributeArticle) {
        this.idAttributeArticle = idAttributeArticle;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdAttribute() {
        return idAttribute;
    }

    public void setIdAttribute(Integer idAttribute) {
        this.idAttribute = idAttribute;
    }

    public Integer getIdDataType() {
        return idDataType;
    }

    public void setIdDataType(Integer idDataType) {
        this.idDataType = idDataType;
    }

    public CDataTypes getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(CDataTypes cDataTypes) {
        this.dataTypes = cDataTypes;
    }

    public CArticles getArticles() {
        return articles;
    }

    public void setArticles(CArticles cArticles) {
        this.articles = cArticles;
    }

    public CAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(CAttributes cAttributes) {
        this.attributes = cAttributes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttributeArticle != null ? idAttributeArticle.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttributesArticles)) {
            return false;
        }
        AttributesArticles other = (AttributesArticles) object;
        if ((this.idAttributeArticle == null && other.idAttributeArticle != null) || (this.idAttributeArticle != null && !this.idAttributeArticle.equals(other.idAttributeArticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AttributesArticles[ idAttributeArticle=" + idAttributeArticle + " ]";
    }
}
