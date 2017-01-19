/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@Table(name = "C_TYPE_METHOD_PAY")
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CTypeMethodPay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_METHOD_PAY")
    @JsonView(JsonViews.Root.class)
    private Integer idMethodPay;
    @Size(max = 30)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERCENTAGE_PAYMENT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal percentagePayment;
    @Size(max = 30)
    @Column(name = "TYPE_METHOD")
    @JsonView(JsonViews.Root.class)
    private String typeMethod;

    public CTypeMethodPay() {
    }

    public CTypeMethodPay(Integer idMethodPay) {
        this.idMethodPay = idMethodPay;
    }

    public Integer getIdMethodPay() {
        return idMethodPay;
    }

    public void setIdMethodPay(Integer idMethodPay) {
        this.idMethodPay = idMethodPay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPercentagePayment() {
        return percentagePayment;
    }

    public void setPercentagePayment(BigDecimal percentagePayment) {
        this.percentagePayment = percentagePayment;
    }

    public String getTypeMethod() {
        return typeMethod;
    }

    public void setTypeMethod(String typeMethod) {
        this.typeMethod = typeMethod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMethodPay != null ? idMethodPay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTypeMethodPay)) {
            return false;
        }
        CTypeMethodPay other = (CTypeMethodPay) object;
        if ((this.idMethodPay == null && other.idMethodPay != null) || (this.idMethodPay != null && !this.idMethodPay.equals(other.idMethodPay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTypeMethodPay[ idMethodPay=" + idMethodPay + " ]";
    }
    
}
