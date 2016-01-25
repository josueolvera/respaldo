/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_COMPONENTS")
public class CComponents implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMPONENT")
    @JsonView(JsonViews.Root.class)
    private Integer idComponent;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "COMPONENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String componentName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public CComponents() {
    }

    public CComponents(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public CComponents(Integer idComponent, String componentName, LocalDateTime creationDate) {
        this.idComponent = idComponent;
        this.componentName = componentName;
        this.creationDate = creationDate;
    }

    public Integer getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponent != null ? idComponent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CComponents)) {
            return false;
        }
        CComponents other = (CComponents) object;
        if ((this.idComponent == null && other.idComponent != null) || (this.idComponent != null && !this.idComponent.equals(other.idComponent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CComponents[ idComponent=" + idComponent + " ]";
    }
    
}
