/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_TASKS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CTasks implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_TASK")
    private Integer idTask;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @JsonView(JsonViews.Root.class)
    @Column(name = "TASK_NAME")
    private String taskName;

    @Size(max = 10)
    @Column(name = "HTTP_METHOD")
    @JsonView(JsonViews.Root.class)
    private String httpMethod;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTask")
    @JsonView(JsonViews.Embedded.class)
    private List<TasksRole> tasksRoleList;

    public CTasks() {
    }

    public CTasks(Integer idTask) {
        this.idTask = idTask;
    }

    public CTasks(Integer idTask, String taskName, LocalDateTime creationDate) {
        this.idTask = idTask;
        this.taskName = taskName;
        this.creationDate = creationDate;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<TasksRole> getTasksRoleList() {
        return tasksRoleList;
    }

    public void setTasksRoleList(List<TasksRole> tasksRoleList) {
        this.tasksRoleList = tasksRoleList;
    }
    
    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTask != null ? idTask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTasks)) {
            return false;
        }
        CTasks other = (CTasks) object;
        if ((this.idTask == null && other.idTask != null) || (this.idTask != null && !this.idTask.equals(other.idTask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTasks[ idTask=" + idTask + " ]";
    }
    
}
