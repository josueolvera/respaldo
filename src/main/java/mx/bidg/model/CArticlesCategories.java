/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "C_ARTICLES_CATEGORIES", uniqueConstraints = {
        @UniqueConstraint(
                name = "UNIQUE_FOLIO_CATEGORY", columnNames = "FOLIO_CATEGORY"
        )
})
public class CArticlesCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ARTICLE_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idArticlesCategory;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ARTICLES_CATEGORY_NAME")
    @JsonView(JsonViews.Root.class)
    private String articlesCategoryName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUIRE_SERIAL_NUMBER")
    @JsonView(JsonViews.Root.class)
    private int requireSerialNumber;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 3)
    @Column(name = "FOLIO_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private String folioCategory;

    public CArticlesCategories() {
    }

    public CArticlesCategories(Integer idAticlesCategory) {
        this.idArticlesCategory = idAticlesCategory;
    }

    public CArticlesCategories(Integer idAticlesCategory, String articlesCategoryName, int requireSerialNumber) {
        this.idArticlesCategory = idAticlesCategory;
        this.articlesCategoryName = articlesCategoryName;
        this.requireSerialNumber = requireSerialNumber;
    }

    public Integer getIdArticlesCategory() {
        return idArticlesCategory;
    }

    public void setIdArticlesCategory(Integer idArticlesCategory) {
        this.idArticlesCategory = idArticlesCategory;
    }

    public String getArticlesCategoryName() {
        return articlesCategoryName;
    }

    public void setArticlesCategoryName(String articlesCategoryName) {
        this.articlesCategoryName = articlesCategoryName;
    }

    public int getRequireSerialNumber() {
        return requireSerialNumber;
    }

    public void setRequireSerialNumber(int requireSerialNumber) {
        this.requireSerialNumber = requireSerialNumber;
    }

    public String getFolioCategory() {
        return folioCategory;
    }

    public void setFolioCategory(String folioCategory) {
        this.folioCategory = folioCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticlesCategory != null ? idArticlesCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CArticlesCategories)) {
            return false;
        }
        CArticlesCategories other = (CArticlesCategories) object;
        if ((this.idArticlesCategory == null && other.idArticlesCategory != null) || (this.idArticlesCategory != null && !this.idArticlesCategory.equals(other.idArticlesCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CArticlesCategories[ idAticlesCategory=" + idArticlesCategory + " ]";
    }
    
}
