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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_REQUESTS_CATEGORIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CRequestsCategories implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategorie;

    @Size(max = 100)
    @Column(name = "CATEGORY")
    @JsonView(JsonViews.Root.class)
    private String category;

    @Column(name = "PERIODIC")
    @JsonView(JsonViews.Root.class)
    private Integer periodic;
    
    @Size(max = 1000)
    @Column(name = "INFORMATION")
    @JsonView(JsonViews.Root.class)
    private String information;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @OneToMany(mappedBy = "idRequestCategory")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestTypesProduct> requestTypesProductList;

    public CRequestsCategories() {
    }

    public CRequestsCategories(Integer idRequestCategorie) {
        this.idRequestCategorie = idRequestCategorie;
    }

    public Integer getIdRequestCategorie() {
        return idRequestCategorie;
    }

    public void setIdRequestCategorie(Integer idRequestCategorie) {
        this.idRequestCategorie = idRequestCategorie;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPeriodic() {
        return periodic;
    }

    public void setPeriodic(Integer periodic) {
        this.periodic = periodic;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }
    
    public List<RequestTypesProduct> getRequestTypesProductList() {
        return requestTypesProductList;
    }

    public void setRequestTypesProductList(List<RequestTypesProduct> requestTypesProductList) {
        this.requestTypesProductList = requestTypesProductList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestCategorie != null ? idRequestCategorie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CRequestsCategories)) {
            return false;
        }
        CRequestsCategories other = (CRequestsCategories) object;
        if ((this.idRequestCategorie == null && other.idRequestCategorie != null) || (this.idRequestCategorie != null && !this.idRequestCategorie.equals(other.idRequestCategorie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CRequestsCategories[ idRequestCategorie=" + idRequestCategorie + " ]";
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
    
}
