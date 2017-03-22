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
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "C_AMOUNTS_SECURE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CAmountsSecure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AMOUNTS_SECURE")
    @JsonView(JsonViews.Root.class)
    private Integer idAmountsSecure;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RODE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rode;

    public CAmountsSecure() {
    }

    public CAmountsSecure(Integer idAmountsSecure) {
        this.idAmountsSecure = idAmountsSecure;
    }

    public Integer getIdAmountsSecure() {
        return idAmountsSecure;
    }

    public void setIdAmountsSecure(Integer idAmountsSecure) {
        this.idAmountsSecure = idAmountsSecure;
    }

    public BigDecimal getRode() {
        return rode;
    }

    public void setRode(BigDecimal rode) {
        this.rode = rode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmountsSecure != null ? idAmountsSecure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAmountsSecure)) {
            return false;
        }
        CAmountsSecure other = (CAmountsSecure) object;
        if ((this.idAmountsSecure == null && other.idAmountsSecure != null) || (this.idAmountsSecure != null && !this.idAmountsSecure.equals(other.idAmountsSecure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAmountsSecure[ idAmountsSecure=" + idAmountsSecure + " ]";
    }
    
}
