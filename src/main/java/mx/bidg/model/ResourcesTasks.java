package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "RESOURCES_TASKS")
public class ResourcesTasks implements Serializable {

    public static final int STOCK = 1;
    public static final int REQUEST_COTIZABLE = 2;
    public static final int REQUEST_PERIODICA = 3;
    public static final int REQUEST_DIRECTA = 4;
    public static final int ACCOUNT_PAYABLE = 5;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESOURCE_TASK")
    @JsonView(JsonViews.Root.class)
    private Integer idResourceTask;

    @Size(max = 200)
    @Column(name = "RESOURCE_NAME")
    @JsonView(JsonViews.Root.class)
    private String resourceName;

    @Column(name = "ID_TASK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTask;

    @Column(name = "ID_VIEW", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idView;

    @JoinColumn(name = "ID_TASK", referencedColumnName = "ID_TASK")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private CTasks tasks;

    @JoinColumn(name = "ID_VIEW", referencedColumnName = "ID_VIEW")
    @JsonView(JsonViews.Embedded.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private CViews view;


    public ResourcesTasks() {}

    public ResourcesTasks(Integer idResourceTask) {
        this.idResourceTask = idResourceTask;
    }

    public Integer getIdResourceTask() {
        return idResourceTask;
    }

    public void setIdResourceTask(Integer idResourceTask) {
        this.idResourceTask = idResourceTask;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

    public CTasks getTasks() {
        return tasks;
    }

    public void setTasks(CTasks tasks) {
        this.tasks = tasks;
    }

    public CViews getView() {
        return view;
    }

    public void setView(CViews view) {
        this.view = view;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResourceTask != null ? idResourceTask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourcesTasks)) {
            return false;
        }
        ResourcesTasks other = (ResourcesTasks) object;
        if ((this.idResourceTask == null && other.idResourceTask != null) || (this.idResourceTask != null && !this.idResourceTask.equals(other.idResourceTask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ResourcesTasks[ idResourceTask=" + idResourceTask + " ]";
    }
    
}
