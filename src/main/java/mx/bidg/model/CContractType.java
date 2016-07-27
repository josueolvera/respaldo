/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_CONTRACT_TYPE")

public class CContractType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONTRACT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idContractType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONTRACT_TYPE_NAME")
    @JsonView(JsonViews.Root.class)
    private String contractTypeName;

    public CContractType() {
    }

    public CContractType(Integer idContractType) {
        this.idContractType = idContractType;
    }

    public CContractType(Integer idContractType, String contractTypeName) {
        this.idContractType = idContractType;
        this.contractTypeName = contractTypeName;
    }

    public Integer getIdContractType() {
        return idContractType;
    }

    public void setIdContractType(Integer idContractType) {
        this.idContractType = idContractType;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContractType != null ? idContractType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CContractType)) {
            return false;
        }
        CContractType other = (CContractType) object;
        if ((this.idContractType == null && other.idContractType != null) || (this.idContractType != null && !this.idContractType.equals(other.idContractType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CContractType[ idContractType=" + idContractType + " ]";
    }
    
}
