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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@Table(name = "C_TYPE_POLICY")
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CTypePolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final  CTypePolicy polizas_pagadas = new CTypePolicy(1);
    private static final  CTypePolicy auxilio_vial  = new CTypePolicy(2);
    private static final  CTypePolicy pagos_camionero = new CTypePolicy(3);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TYPE_POLICY")
    @JsonView(JsonViews.Root.class)
    private Integer idTypePolicy;

    @Size(max = 30)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    public CTypePolicy() {
    }

    public CTypePolicy(Integer idTypePolicy) {
        this.idTypePolicy = idTypePolicy;
    }

    public Integer getIdTypePolicy() {
        return idTypePolicy;
    }

    public void setIdTypePolicy(Integer idTypePolicy) {
        this.idTypePolicy = idTypePolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypePolicy != null ? idTypePolicy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTypePolicy)) {
            return false;
        }
        CTypePolicy other = (CTypePolicy) object;
        if ((this.idTypePolicy == null && other.idTypePolicy != null) || (this.idTypePolicy != null && !this.idTypePolicy.equals(other.idTypePolicy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTypePolicy[ idTypePolicy=" + idTypePolicy + " ]";
    }
    
}
