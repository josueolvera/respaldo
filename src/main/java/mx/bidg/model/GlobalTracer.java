/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "GLOBAL_TRACER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class GlobalTracer implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GLOBAL_TRACER")
    @JsonView(JsonViews.Root.class)
    private Integer idGlobalTracer;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "OPERATION_TYPE")
    @JsonView(JsonViews.Root.class)
    private String operationType;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TABLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String tableName;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "INFO")
    @JsonView(JsonViews.Root.class)
    private String info;
    
    @Size(max = 200)
    @Column(name = "TASK")
    @JsonView(JsonViews.Root.class)
    private String task;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "ID_USER")
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    public GlobalTracer() {
    }

    public GlobalTracer(Integer idGlobalTracer) {
        this.idGlobalTracer = idGlobalTracer;
    }

    public GlobalTracer(Integer idGlobalTracer, String username, String operationType, LocalDateTime creationDate) {
        this.idGlobalTracer = idGlobalTracer;
        this.username = username;
        this.operationType = operationType;
        this.creationDate = creationDate;
    }

    public Integer getIdGlobalTracer() {
        return idGlobalTracer;
    }

    public void setIdGlobalTracer(Integer idGlobalTracer) {
        this.idGlobalTracer = idGlobalTracer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGlobalTracer != null ? idGlobalTracer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlobalTracer)) {
            return false;
        }
        GlobalTracer other = (GlobalTracer) object;
        if ((this.idGlobalTracer == null && other.idGlobalTracer != null) || (this.idGlobalTracer != null && !this.idGlobalTracer.equals(other.idGlobalTracer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.GlobalTracer[ idGlobalTracer=" + idGlobalTracer + " ]";
    }
    
}
