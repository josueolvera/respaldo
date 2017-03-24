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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josue
 */
@Entity
@DynamicUpdate
@Table(name = "C_ACCOUNTING_ACCOUNT_NATURE")

public class CAccountingAccountNature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNTING_NATURE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingNature;

    @Size(max = 50)
    @Column(name = "NATURE")
    @JsonView(JsonViews.Root.class)
    private String nature;

    public CAccountingAccountNature() {
    }

    public CAccountingAccountNature(Integer idAccountingNature) {
        this.idAccountingNature = idAccountingNature;
    }

    public Integer getIdAccountingNature() {
        return idAccountingNature;
    }

    public void setIdAccountingNature(Integer idAccountingNature) {
        this.idAccountingNature = idAccountingNature;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountingNature != null ? idAccountingNature.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAccountingAccountNature)) {
            return false;
        }
        CAccountingAccountNature other = (CAccountingAccountNature) object;
        if ((this.idAccountingNature == null && other.idAccountingNature != null) || (this.idAccountingNature != null && !this.idAccountingNature.equals(other.idAccountingNature))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAccountingAccountNature[ idAccountingNature=" + idAccountingNature + " ]";
    }

}
