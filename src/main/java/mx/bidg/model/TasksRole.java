/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "TASKS_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TasksRole.findAll", query = "SELECT t FROM TasksRole t")})
public class TasksRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TASK_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idTaskRole;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @JoinColumn(name = "ID_TASK", referencedColumnName = "ID_TASK")
    @ManyToOne(optional = false)
//    @JsonView(JsonViews.Embedded.class)
    @JsonIgnore
    private CTasks idTask;

    @JoinColumn(name = "ID_SYSTEM_ROLE", referencedColumnName = "ID_SYSTEM_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private SystemRoles idSystemRole;

    public TasksRole() {
    }

    public TasksRole(Integer idTaskRole) {
        this.idTaskRole = idTaskRole;
    }

    public TasksRole(Integer idTaskRole, Date creationDate) {
        this.idTaskRole = idTaskRole;
        this.creationDate = creationDate;
    }

    public Integer getIdTaskRole() {
        return idTaskRole;
    }

    public void setIdTaskRole(Integer idTaskRole) {
        this.idTaskRole = idTaskRole;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CTasks getIdTask() {
        return idTask;
    }

    public void setIdTask(CTasks idTask) {
        this.idTask = idTask;
    }

    public SystemRoles getIdSystemRole() {
        return idSystemRole;
    }

    public void setIdSystemRole(SystemRoles idSystemRole) {
        this.idSystemRole = idSystemRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTaskRole != null ? idTaskRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TasksRole)) {
            return false;
        }
        TasksRole other = (TasksRole) object;
        if ((this.idTaskRole == null && other.idTaskRole != null) || (this.idTaskRole != null && !this.idTaskRole.equals(other.idTaskRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TasksRole[ idTaskRole=" + idTaskRole + " ]";
    }
    
}
