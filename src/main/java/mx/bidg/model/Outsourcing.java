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
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.pojos.DateFormatsPojo;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "OUTSOURCING")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Outsourcing implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OUTSOURCING")
    @JsonView(JsonViews.Root.class)
    private Integer idOutsourcing;
    
     @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARY")
    @JsonView(JsonViews.Root.class)
    private BigDecimal salary;
    
    @Column(name = "SUBSIDY")
    @JsonView(JsonViews.Root.class)
    private BigDecimal subsidy;
    
    @Column(name = "IMSS_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal imssEmployee;
    
    @Column(name = "ISR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal isr;
    
    @Column(name = "ADJUSTMENT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal adjustment;
    
    @Column(name = "TOTAL_DEDUCTIONS")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalDeductions;
    
    @Column(name = "NET_ASSET_TAX")
    @JsonView(JsonViews.Root.class)
    private BigDecimal netAssetTax;

    @Column(name = "IMSS")
    @JsonView(JsonViews.Root.class)
    private BigDecimal imss;
    
    @Column(name = "RCV")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rcv;
    
    @Column(name = "SUBTOTAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal subtotal;
    
    @Column(name = "PAYROLL_TAX")
    @JsonView(JsonViews.Root.class)
    private BigDecimal payrollTax;
    
    @Column(name = "TOTAL_SOCIAL_SECURITY")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalSocialSecurity;
    
    @Column(name = "COMMISSION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commission;
    
    @Column(name = "ENTERPRISE_INFONAVIT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal enterpriseInfonavit;
    
    @Column(name = "TOTAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal total;

    @Column(name = "IVA")
    @JsonView(JsonViews.Root.class)
    private BigDecimal iva;
    
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "APPLICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime applicationDate;
    
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;
    
    @Column(name = "USERNAME", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String username;
    
    @Column(name = "ID_DW_ENTERPRISE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDwEnterprise;
    
    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private DwEnterprises dwEnterprises;
    
    public Outsourcing() {
    }

    public Outsourcing(Integer idOutsourcing) {
        this.idOutsourcing = idOutsourcing;
    }

    public Outsourcing(Integer idOutsourcing, Integer idEmployee) {
        this.idOutsourcing = idOutsourcing;
        this.idEmployee = idEmployee;
    }

    public Integer getIdOutsourcing() {
        return idOutsourcing;
    }

    public void setIdOutsourcing(Integer idOutsourcing) {
        this.idOutsourcing = idOutsourcing;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public BigDecimal getImssEmployee() {
        return imssEmployee;
    }

    public void setImssEmployee(BigDecimal imssEmployee) {
        this.imssEmployee = imssEmployee;
    }

    public BigDecimal getIsr() {
        return isr;
    }

    public void setIsr(BigDecimal isr) {
        this.isr = isr;
    }

    public BigDecimal getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(BigDecimal adjustment) {
        this.adjustment = adjustment;
    }

    public BigDecimal getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(BigDecimal totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public BigDecimal getNetAssetTax() {
        return netAssetTax;
    }

    public void setNetAssetTax(BigDecimal netAssetTax) {
        this.netAssetTax = netAssetTax;
    }

    public BigDecimal getRcv() {
        return rcv;
    }

    public void setRcv(BigDecimal rcv) {
        this.rcv = rcv;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getPayrollTax() {
        return payrollTax;
    }

    public void setPayrollTax(BigDecimal payrollTax) {
        this.payrollTax = payrollTax;
    }

    public BigDecimal getTotalSocialSecurity() {
        return totalSocialSecurity;
    }

    public void setTotalSocialSecurity(BigDecimal totalSocialSecurity) {
        this.totalSocialSecurity = totalSocialSecurity;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getEnterpriseInfonavit() {
        return enterpriseInfonavit;
    }

    public void setEnterpriseInfonavit(BigDecimal enterpriseInfonavit) {
        this.enterpriseInfonavit = enterpriseInfonavit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public BigDecimal getImss() {
        return imss;
    }

    public void setImss(BigDecimal imss) {
        this.imss = imss;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public DateFormatsPojo getCreationDateFormats() {
        if (creationDate == null) {
            return null;
        }
        return new DateFormatsPojo(creationDate);
    }
    
    public DateFormatsPojo getApplicationDateFormats() {
        if (applicationDate == null) {
            return null;
        }
        return new DateFormatsPojo(applicationDate);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Integer getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(Integer idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public DwEnterprises getDwEnterprises() {
        return dwEnterprises;
    }

    public void setDwEnterprises(DwEnterprises dwEnterprises) {
        this.dwEnterprises = dwEnterprises;
    }
    
    @Override
    public String toString() {
        return "Outsourcing{" + "idOutsourcing=" + idOutsourcing + ", idEmployee=" + idEmployee + ", salary=" + salary + ", subsidy=" + subsidy + ", imssEmployee=" + imssEmployee + ", isr=" + isr + ", adjustment=" + adjustment + ", totalDeductions=" + totalDeductions + ", netAssetTax=" + netAssetTax + ", rcv=" + rcv + ", subtotal=" + subtotal + ", payrollTax=" + payrollTax + ", totalSocialSecurity=" + totalSocialSecurity + ", commission=" + commission + ", enterpriseInfonavit=" + enterpriseInfonavit + ", total=" + total + ", creationDate=" + creationDate + ", applicationDate=" + applicationDate + ", employee=" + employee + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idOutsourcing);
        hash = 23 * hash + Objects.hashCode(this.idEmployee);
        hash = 23 * hash + Objects.hashCode(this.salary);
        hash = 23 * hash + Objects.hashCode(this.subsidy);
        hash = 23 * hash + Objects.hashCode(this.imssEmployee);
        hash = 23 * hash + Objects.hashCode(this.isr);
        hash = 23 * hash + Objects.hashCode(this.adjustment);
        hash = 23 * hash + Objects.hashCode(this.totalDeductions);
        hash = 23 * hash + Objects.hashCode(this.netAssetTax);
        hash = 23 * hash + Objects.hashCode(this.rcv);
        hash = 23 * hash + Objects.hashCode(this.subtotal);
        hash = 23 * hash + Objects.hashCode(this.payrollTax);
        hash = 23 * hash + Objects.hashCode(this.totalSocialSecurity);
        hash = 23 * hash + Objects.hashCode(this.commission);
        hash = 23 * hash + Objects.hashCode(this.enterpriseInfonavit);
        hash = 23 * hash + Objects.hashCode(this.total);
        hash = 23 * hash + Objects.hashCode(this.creationDate);
        hash = 23 * hash + Objects.hashCode(this.applicationDate);
        hash = 23 * hash + Objects.hashCode(this.employee);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Outsourcing other = (Outsourcing) obj;
        if (!Objects.equals(this.idOutsourcing, other.idOutsourcing)) {
            return false;
        }
        if (!Objects.equals(this.idEmployee, other.idEmployee)) {
            return false;
        }
        if (!Objects.equals(this.salary, other.salary)) {
            return false;
        }
        if (!Objects.equals(this.subsidy, other.subsidy)) {
            return false;
        }
        if (!Objects.equals(this.imssEmployee, other.imssEmployee)) {
            return false;
        }
        if (!Objects.equals(this.isr, other.isr)) {
            return false;
        }
        if (!Objects.equals(this.adjustment, other.adjustment)) {
            return false;
        }
        if (!Objects.equals(this.totalDeductions, other.totalDeductions)) {
            return false;
        }
        if (!Objects.equals(this.netAssetTax, other.netAssetTax)) {
            return false;
        }
        if (!Objects.equals(this.rcv, other.rcv)) {
            return false;
        }
        if (!Objects.equals(this.subtotal, other.subtotal)) {
            return false;
        }
        if (!Objects.equals(this.payrollTax, other.payrollTax)) {
            return false;
        }
        if (!Objects.equals(this.totalSocialSecurity, other.totalSocialSecurity)) {
            return false;
        }
        if (!Objects.equals(this.commission, other.commission)) {
            return false;
        }
        if (!Objects.equals(this.enterpriseInfonavit, other.enterpriseInfonavit)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.applicationDate, other.applicationDate)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        return true;
    }
    
    
    
}
