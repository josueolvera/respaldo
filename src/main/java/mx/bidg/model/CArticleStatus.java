/**
 *
 * @author rafael
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "C_ARTICLE_STATUS")
public class CArticleStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARTICLE_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idArticleStatus;

    @Size(max = 100)
    @Column(name = "ARTICLE_STATUS")
    @JsonView(JsonViews.Root.class)
    private String articleStatus;

    public CArticleStatus() {
    }

    public CArticleStatus(Integer idArticleStatus) {
        this.idArticleStatus = idArticleStatus;
    }

    public Integer getIdArticleStatus() {
        return idArticleStatus;
    }

    public void setIdArticleStatus(Integer idArticleStatus) {
        this.idArticleStatus = idArticleStatus;
    }

    public String getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticleStatus != null ? idArticleStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CArticleStatus)) {
            return false;
        }
        CArticleStatus other = (CArticleStatus) object;
        if ((this.idArticleStatus == null && other.idArticleStatus != null) || (this.idArticleStatus != null && !this.idArticleStatus.equals(other.idArticleStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CArticleStatus[ idArticleStatus=" + idArticleStatus + " ]";
    }
    
}
