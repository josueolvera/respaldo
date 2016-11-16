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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Cristhian de la cruz
 */
@Entity
@DynamicUpdate
@Table(name = "COMMISSION_EMCOFIN")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CommissionEmcofin implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COM_EMCOFIN")
    @JsonView(JsonViews.Root.class)
    private Integer idComEmcofin;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COMMISSION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commission;
    
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "APPLICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime applicationDate;
    
    @Size(max = 30)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;
    
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private Boolean status;
    
    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;
    
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;

    public CommissionEmcofin() {
    }

    public CommissionEmcofin(Integer idComEmcofin) {
        this.idComEmcofin = idComEmcofin;
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
    
    public Integer getIdComEmcofin() {
        return idComEmcofin;
    }

    public void setIdComEmcofin(Integer idComEmcofin) {
        this.idComEmcofin = idComEmcofin;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComEmcofin != null ? idComEmcofin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommissionEmcofin)) {
            return false;
        }
        CommissionEmcofin other = (CommissionEmcofin) object;
        if ((this.idComEmcofin == null && other.idComEmcofin != null) || (this.idComEmcofin != null && !this.idComEmcofin.equals(other.idComEmcofin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CommisionEmcofin[ idComEmcofin=" + idComEmcofin + " ]";
    }
    
}

