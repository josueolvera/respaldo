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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_DISTRIBUTORS")

public class CDistributors implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DISTRIBUTOR")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private Integer idDistributor;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "DISTRIBUTOR_NAME")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private String distributorName;
    
    @Size(max = 15)
    @Column(name = "ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String acronyms;

    @Column(name = "HAS_STOCK")
    @JsonView(JsonViews.Root.class)
    private Integer hasStock;

    @Column(name = "BUDGET_SHARE", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean budgetShare;

    public CDistributors() {
    }

    public CDistributors(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public CDistributors(Integer idDistributor, String distributorName) {
        this.idDistributor = idDistributor;
        this.distributorName = distributorName;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public Integer getHasStock() {
        return hasStock;
    }

    public void setHasStock(Integer hasStock) {
        this.hasStock = hasStock;
    }

    public Boolean getBudgetShare() {
        return budgetShare;
    }

    public void setBudgetShare(Boolean budgetShare) {
        this.budgetShare = budgetShare;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistributor != null ? idDistributor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CDistributors)) {
            return false;
        }
        CDistributors other = (CDistributors) object;
        if ((this.idDistributor == null && other.idDistributor != null) || (this.idDistributor != null && !this.idDistributor.equals(other.idDistributor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CDistributors[ idDistributor=" + idDistributor + " ]";
    }

}
