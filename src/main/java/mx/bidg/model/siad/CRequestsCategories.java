package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "C_REQUESTS_CATEGORIES", schema = "BIDG_DBA", catalog = "")
public class CRequestsCategories {
    private int idRequestCategory;
    private String requestCategoryName;
    private Integer idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Collection<Requests> requestsByIdRequestCategory;

    @Id
    @Column(name = "ID_REQUEST_CATEGORY", nullable = false)
    public int getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(int idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    @Basic
    @Column(name = "REQUEST_CATEGORY_NAME", nullable = true, length = 100)
    public String getRequestCategoryName() {
        return requestCategoryName;
    }

    public void setRequestCategoryName(String requestCategoryName) {
        this.requestCategoryName = requestCategoryName;
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

        CRequestsCategories that = (CRequestsCategories) o;

        if (idRequestCategory != that.idRequestCategory) return false;
        if (requestCategoryName != null ? !requestCategoryName.equals(that.requestCategoryName) : that.requestCategoryName != null)
            return false;
        if (idAccessLevel != null ? !idAccessLevel.equals(that.idAccessLevel) : that.idAccessLevel != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRequestCategory;
        result = 31 * result + (requestCategoryName != null ? requestCategoryName.hashCode() : 0);
        result = 31 * result + (idAccessLevel != null ? idAccessLevel.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cRequestsCategoriesByIdRequestCategory")
    public Collection<Requests> getRequestsByIdRequestCategory() {
        return requestsByIdRequestCategory;
    }

    public void setRequestsByIdRequestCategory(Collection<Requests> requestsByIdRequestCategory) {
        this.requestsByIdRequestCategory = requestsByIdRequestCategory;
    }
}
