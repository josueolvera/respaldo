/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "C_CUSTOMERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CCustomers implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final CCustomers GMT= new CCustomers(1);
    private static final CCustomers AMER= new CCustomers(2);
    private static final CCustomers AMERMEDIA= new CCustomers(3);
    private static final CCustomers EMCOFIN= new CCustomers(4);
    private static final CCustomers ABSOL= new CCustomers(5);
    private static final CCustomers CHERAMA= new CCustomers(6);
    private static final CCustomers RCUBICA= new CCustomers(7);
    private static final CCustomers BID_ENERGY= new CCustomers(8);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CUSTOMER")
    @JsonView(JsonViews.Root.class)
    private Integer idCustomer;

    @Size(max = 30)
    @Column(name = "CUSTOMER_NAME")
    @JsonView(JsonViews.Root.class)
    private String customerName;

    public CCustomers() {
    }

    public CCustomers(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCustomer != null ? idCustomer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CCustomers)) {
            return false;
        }
        CCustomers other = (CCustomers) object;
        if ((this.idCustomer == null && other.idCustomer != null) || (this.idCustomer != null && !this.idCustomer.equals(other.idCustomer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CCustomers[ idCustomer=" + idCustomer + " ]";
    }
    
}
