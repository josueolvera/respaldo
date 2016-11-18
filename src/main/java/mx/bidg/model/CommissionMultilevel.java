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
@Table(name = "COMMISSION_MULTILEVEL")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CommissionMultilevel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMISSION_M")
    @JsonView(JsonViews.Root.class)
    private Integer idComissionM;
    
    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RFC")
    @JsonView(JsonViews.Root.class)
    private String rfc;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal monto;
    
    @Column(name = "APLICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime applicationDate;
    
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Size(max = 50)
    @Column(name = "USER_NAME")
    @JsonView(JsonViews.Root.class)
    private String userName;
    
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;

    public CommissionMultilevel() {
    }

    public CommissionMultilevel(Integer idComissionM) {
        this.idComissionM = idComissionM;
    }

    public CommissionMultilevel(Integer idComissionM, Integer idEmployee) {
        this.idComissionM = idComissionM;
        this.idEmployee = idEmployee;
    }
    
    public CommissionMultilevel(Integer idComissionM, String rfc, LocalDateTime applicationDate, LocalDateTime creationDate) {
        this.idComissionM = idComissionM;
        this.rfc = rfc;
        this.applicationDate = applicationDate;
        this.creationDate = creationDate;
    }

    public Integer getIdComissionM() {
        return idComissionM;
    }

    public void setIdComissionM(Integer idComissionM) {
        this.idComissionM = idComissionM;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComissionM != null ? idComissionM.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommissionMultilevel)) {
            return false;
        }
        CommissionMultilevel other = (CommissionMultilevel) object;
        if ((this.idComissionM == null && other.idComissionM != null) || (this.idComissionM != null && !this.idComissionM.equals(other.idComissionM))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CommissionMultilevel[ idComissionM=" + idComissionM + " ]";
    }
    
}
