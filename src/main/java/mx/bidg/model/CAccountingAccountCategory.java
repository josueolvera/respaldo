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
@Table(name = "C_ACCOUNTING_ACCOUNT_CATEGORY")

public class CAccountingAccountCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNTING_CATEGORY")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingCategory;

    @Size(max = 50)
    @Column(name = "CLASSIFICATION")
    @JsonView(JsonViews.Root.class)
    private String classification;

    public CAccountingAccountCategory() {
    }

    public CAccountingAccountCategory(Integer idAccountingCategory) {
        this.idAccountingCategory = idAccountingCategory;
    }

    public Integer getIdAccountingCategory() {
        return idAccountingCategory;
    }

    public void setIdAccountingCategory(Integer idAccountingCategory) {
        this.idAccountingCategory = idAccountingCategory;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountingCategory != null ? idAccountingCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAccountingAccountCategory)) {
            return false;
        }
        CAccountingAccountCategory other = (CAccountingAccountCategory) object;
        if ((this.idAccountingCategory == null && other.idAccountingCategory != null) || (this.idAccountingCategory != null && !this.idAccountingCategory.equals(other.idAccountingCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAccountingAccountCategory[ idAccountingCategory=" + idAccountingCategory + " ]";
    }
    
}
