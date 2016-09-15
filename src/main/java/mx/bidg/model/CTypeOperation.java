/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author rubens
 */
@Entity
@Table(name = "C_TYPE_OPERATION")

public class CTypeOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYPE_OPERATION")
    @JsonView(JsonViews.Root.class)
    private Integer typeOperation;
    
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;
    

    public CTypeOperation() {
    }

    public CTypeOperation(Integer typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Integer getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(Integer typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeOperation != null ? typeOperation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTypeOperation)) {
            return false;
        }
        CTypeOperation other = (CTypeOperation) object;
        if ((this.typeOperation == null && other.typeOperation != null) || (this.typeOperation != null && !this.typeOperation.equals(other.typeOperation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTypeOperation[ typeOperation=" + typeOperation + " ]";
    }
    
}
