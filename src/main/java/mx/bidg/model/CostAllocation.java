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
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "COST_ALLOCATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CostAllocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_COST_ALLOCATION")
    private Integer idCostAllocation;
    
    @Column(name = "PERCENTAGE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal percentage;
    
    @Size(max = 30)
    @JsonView(JsonViews.Root.class)
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;
    
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;
    
    @Column(name = "ID_CUSTOMERS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCustomers;
    
    @JoinColumn(name = "ID_CUSTOMERS", referencedColumnName = "ID_CUSTOMER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCustomers customer;

    public CostAllocation() {
    }

    public CostAllocation(Integer idCostAllocation) {
        this.idCostAllocation = idCostAllocation;
    }

    public CostAllocation(Integer idCostAllocation, LocalDateTime creationDate) {
        this.idCostAllocation = idCostAllocation;
        this.creationDate = creationDate;
    }

    public Integer getIdCostAllocation() {
        return idCostAllocation;
    }

    public void setIdCostAllocation(Integer idCostAllocation) {
        this.idCostAllocation = idCostAllocation;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Integer getIdCustomers() {
        return idCustomers;
    }

    public void setIdCustomers(Integer idCustomers) {
        this.idCustomers = idCustomers;
    }

    public CCustomers getCustomer() {
        return customer;
    }

    public void setCustomer(CCustomers customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCostAllocation != null ? idCostAllocation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostAllocation)) {
            return false;
        }
        CostAllocation other = (CostAllocation) object;
        if ((this.idCostAllocation == null && other.idCostAllocation != null) || (this.idCostAllocation != null && !this.idCostAllocation.equals(other.idCostAllocation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CostAllocation[ idCostAllocation=" + idCostAllocation + " ]";
    }

}
