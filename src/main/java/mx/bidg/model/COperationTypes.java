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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_OPERATION_TYPES")
public class COperationTypes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OPERATION_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idOperationType;
    
    @Size(max = 45)
    @Column(name = "OPERATION_TYPE")
    @JsonView(JsonViews.Root.class)
    private String operationType;

    public COperationTypes() {
    }

    public COperationTypes(Integer idOperationType) {
        this.idOperationType = idOperationType;
    }

    public Integer getIdOperationType() {
        return idOperationType;
    }

    public void setIdOperationType(Integer idOperationType) {
        this.idOperationType = idOperationType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperationType != null ? idOperationType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof COperationTypes)) {
            return false;
        }
        COperationTypes other = (COperationTypes) object;
        if ((this.idOperationType == null && other.idOperationType != null) || (this.idOperationType != null && !this.idOperationType.equals(other.idOperationType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.COperationTypes[ idOperationType=" + idOperationType + " ]";
    }

}
