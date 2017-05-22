/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_REQUESTS_CATEGORIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CRequestsCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final CRequestsCategories SOLICITUD = new CRequestsCategories(1);
    public static final CRequestsCategories PAGO_PROVEEDORES = new CRequestsCategories(2);
    public static final CRequestsCategories VIATICOS = new CRequestsCategories(4);
    public static final CRequestsCategories BOLETOS_DE_AVION = new CRequestsCategories(5);
    public static final CRequestsCategories REEMBOLSOS = new CRequestsCategories(6);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategory;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "REQUEST_CATEGORY_NAME")
    @JsonView(JsonViews.Root.class)
    private String requestCategoryName;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    public String getRequestCategoryName() {
        return requestCategoryName;
    }

    public void setRequestCategoryName(String requestCategoryName) {
        this.requestCategoryName = requestCategoryName;
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

    public CRequestsCategories() {
    }

    public CRequestsCategories(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CRequestsCategories)) return false;

        CRequestsCategories that = (CRequestsCategories) o;

        if (!getIdRequestCategory().equals(that.getIdRequestCategory())) return false;
        if (getRequestCategoryName() != null ? !getRequestCategoryName().equals(that.getRequestCategoryName()) : that.getRequestCategoryName() != null)
            return false;
        if (getIdAccessLevel() != null ? !getIdAccessLevel().equals(that.getIdAccessLevel()) : that.getIdAccessLevel() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        return getCreationDate() != null ? getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdRequestCategory().hashCode();
        result = 31 * result + (getRequestCategoryName() != null ? getRequestCategoryName().hashCode() : 0);
        result = 31 * result + (getIdAccessLevel() != null ? getIdAccessLevel().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CRequestsCategories{" +
                "idRequestCategory=" + idRequestCategory +
                ", idAccessLevel=" + idAccessLevel +
                '}';
    }
}
