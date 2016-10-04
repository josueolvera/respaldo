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
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "PERCEPTIONS_DEDUCTIONS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PerceptionsDeductions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERCEPTION_DEDUCTION")
    @JsonView(JsonViews.Root.class)
    private Integer idPerceptionDeduction;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Size(max = 500)
    @Column(name = "REASON")
    @JsonView(JsonViews.Root.class)
    private String reason;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "APPLICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime applicationDate;


    @Column(name = "STATUS", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean status;

    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "ID_C_PD", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCPd;

    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;

    @JoinColumn(name = "ID_C_PD", referencedColumnName = "ID_C_PD")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CPerceptionsDeductions cPerceptionsDeductions;

    public PerceptionsDeductions() {
    }

    public PerceptionsDeductions(Integer idPerceptionDeduction) {
        this.idPerceptionDeduction = idPerceptionDeduction;
    }

    public Integer getIdPerceptionDeduction() {
        return idPerceptionDeduction;
    }

    public void setIdPerceptionDeduction(Integer idPerceptionDeduction) {
        this.idPerceptionDeduction = idPerceptionDeduction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DateFormatsPojo getCrationDateDateFormats() {
        if (creationDate == null) {
            return null;
        }
        return new DateFormatsPojo(creationDate);
    }

    public DateFormatsPojo getAplicationDateFormats() {
        if (applicationDate == null) {
            return null;
        }
        return new DateFormatsPojo(applicationDate);
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

    public Integer getIdCPd() {
        return idCPd;
    }

    public void setIdCPd(Integer idCPd) {
        this.idCPd = idCPd;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public CPerceptionsDeductions getcPerceptionsDeductions() {
        return cPerceptionsDeductions;
    }

    public void setcPerceptionsDeductions(CPerceptionsDeductions cPerceptionsDeductions) {
        this.cPerceptionsDeductions = cPerceptionsDeductions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerceptionDeduction != null ? idPerceptionDeduction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerceptionsDeductions)) {
            return false;
        }
        PerceptionsDeductions other = (PerceptionsDeductions) object;
        if ((this.idPerceptionDeduction == null && other.idPerceptionDeduction != null) || (this.idPerceptionDeduction != null && !this.idPerceptionDeduction.equals(other.idPerceptionDeduction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.PerceptionsDeductions[ idPerceptionDeduction=" + idPerceptionDeduction + " ]";
    }
    
}
