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

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author josue
 */
@Entity
@DynamicUpdate
@Table(name = "CALCULATION_REPORT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CalculationReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_CALCULATION_REPORT")
    private Integer idCalculationReport;

    @Size(max = 200)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String fileName;

    @Size(max = 200)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
    private String filePath;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CALCULATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime calculationDate;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer status;

    @Column(name = "SHOW_WINDOW")
    @JsonView(JsonViews.Root.class)
    private Integer showWindow;

    @Column(name = "SEND")
    @JsonView(JsonViews.Root.class)
    private Integer send;

    @Column(name = "ID_QUERY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idQuery;

    @JoinColumn(name = "ID_QUERY", referencedColumnName = "ID_QUERY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private SqlQueries sqlQueries;

    public CalculationReport() {
    }

    public CalculationReport(LocalDateTime calculationDate, Integer idCalculationReport) {
        this.calculationDate = calculationDate;
        this.idCalculationReport = idCalculationReport;
    }

    public Integer getIdCalculationReport() {
        return idCalculationReport;
    }

    public void setIdCalculationReport(Integer idCalculationReport) {
        this.idCalculationReport = idCalculationReport;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(LocalDateTime calculationDate) {
        this.calculationDate = calculationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(Integer idQuery) {
        this.idQuery = idQuery;
    }

    public SqlQueries getSqlQueries() {
        return sqlQueries;
    }

    public void setSqlQueries(SqlQueries sqlQueries) {
        this.sqlQueries = sqlQueries;
    }

    public CalculationReport(Integer idCalculationReport) {
        this.idCalculationReport = idCalculationReport;
    }

    public DateFormatsPojo getCalculationDateFormats() {
        if (calculationDate == null) {
            return null;
        }
        return new DateFormatsPojo(calculationDate);
    }

    public Integer getShowWindow() {
        return showWindow;
    }

    public void setShowWindow(Integer showWindow) {
        this.showWindow = showWindow;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalculationReport != null ? idCalculationReport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalculationReport)) {
            return false;
        }
        CalculationReport other = (CalculationReport) object;
        if ((this.idCalculationReport == null && other.idCalculationReport != null) || (this.idCalculationReport != null && !this.idCalculationReport.equals(other.idCalculationReport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CalculationReport[ idCalculationReport=" + idCalculationReport + " ]";
    }
    
}
