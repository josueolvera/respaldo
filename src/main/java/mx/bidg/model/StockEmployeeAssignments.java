package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Rafael Viveros
 */
@Entity
@DynamicUpdate
@Table(name = "STOCK_EMPLOYEE_ASSIGNMENTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class StockEmployeeAssignments implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASSIGNMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idAssignment;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSIGNMENT_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime assignmentDate;

    @Column(name = "CURRENT_ASSIGNMENT")
    @JsonView(JsonViews.Root.class)
    private Integer currentAssignment;

    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "ID_DW_ENTERPRISE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDwEnterprise;

    @Column(name = "ID_STOCK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idStock;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private DwEnterprises dwEnterprises;

    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Employees employee;

    @JoinColumn(name = "ID_STOCK", referencedColumnName = "ID_STOCK")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private Stocks stocks;

    public StockEmployeeAssignments() {
    }

    public StockEmployeeAssignments(Integer idAssignment) {
        this.idAssignment = idAssignment;
    }

    public StockEmployeeAssignments(Integer idAssignment, LocalDateTime assignmentDate) {
        this.idAssignment = idAssignment;
        this.assignmentDate = assignmentDate;
    }

    public Integer getIdAssignment() {
        return idAssignment;
    }

    public void setIdAssignment(Integer idAssignment) {
        this.idAssignment = idAssignment;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public Integer getCurrentAssignment() {
        return currentAssignment;
    }

    public void setCurrentAssignment(Integer currentAssignment) {
        this.currentAssignment = currentAssignment;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(Integer idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public DwEnterprises getDwEnterprises() {
        return dwEnterprises;
    }

    public void setDwEnterprises(DwEnterprises dwEnterprises) {
        this.dwEnterprises = dwEnterprises;
    }

    public DateFormatsPojo getAssignmentDateFormats() {
        return (assignmentDate == null) ? null : new DateFormatsPojo(assignmentDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAssignment != null ? idAssignment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockEmployeeAssignments)) {
            return false;
        }
        StockEmployeeAssignments other = (StockEmployeeAssignments) object;
        if ((this.idAssignment == null && other.idAssignment != null) || (this.idAssignment != null && !this.idAssignment.equals(other.idAssignment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.StockEmployeeAssignments[ idAssignment=" + idAssignment + " ]";
    }
    
}
