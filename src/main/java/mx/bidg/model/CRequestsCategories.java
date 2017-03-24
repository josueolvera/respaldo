/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
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

    public CRequestsCategories(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CRequestsCategories that = (CRequestsCategories) o;

        return idRequestCategory != null ? idRequestCategory.equals(that.idRequestCategory) : that.idRequestCategory == null;

    }

    @Override
    public int hashCode() {
        return idRequestCategory != null ? idRequestCategory.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CRequestsCategories{" +
                "idRequestCategory=" + idRequestCategory +
                ", category='" + category + '\'' +
                ", periodic=" + periodic +
                ", information='" + information + '\'' +
                ", idView=" + idView +
                ", idResourceTask=" + idResourceTask +
                ", idAccessLevel=" + idAccessLevel +
                ", view=" + view +
                ", resourcesTasks=" + resourcesTasks +
                '}';
    }
}
