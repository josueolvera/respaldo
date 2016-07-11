/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_REQUESTS_CATEGORIES")
public class CRequestsCategories implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int COTIZABLE = 1;
    public static final int DIRECTA = 2;
    public static final int PERIODICA = 3;
    public static final int VIATICOS = 4;
    public static final int BOLETOS_AVION = 5;
    public static final int REMBOLSOS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @Column(name = "ID_VIEW", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idView;

    @Column(name = "ID_RESOURCE_TASK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idResourceTask;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @JoinColumn(name = "ID_VIEW", referencedColumnName = "ID_VIEW")
    @ManyToOne
    @JsonView(JsonViews.EmbeddedRequestCategory.class)
    private CViews view;

    @JoinColumn(name = "ID_RESOURCE_TASK", referencedColumnName = "ID_RESOURCE_TASK")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private ResourcesTasks resourcesTasks;

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

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

    public CViews getView() {
        return view;
    }

    public void setView(CViews view) {
        this.view = view;
    }

    public Integer getIdResourceTask() {
        return idResourceTask;
    }

    public void setIdResourceTask(Integer idResourceTask) {
        this.idResourceTask = idResourceTask;
    }

    public ResourcesTasks getResourcesTasks() {
        return resourcesTasks;
    }

    public void setResourcesTasks(ResourcesTasks resourcesTasks) {
        this.resourcesTasks = resourcesTasks;
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
