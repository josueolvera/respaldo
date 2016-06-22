package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Rafael Viveros
 */
@Entity
@Table(name = "SQL_QUERY_PARAMETERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class SqlQueryParameters implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PARAMETER")
    private Integer idParameter;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "PARAMETER_NAME")
    private String parameterName;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "PARAMETER_VALUE")
    private String parameterValue;

    @Column(name = "ID_QUERY", insertable = false, updatable = false)
    private int idSqlQuery;

    @Column(name = "ID_DATA_TYPE", insertable = false, updatable = false)
    private int idDataType;

    @JoinColumn(name = "ID_QUERY", referencedColumnName = "ID_QUERY")
    @ManyToOne(optional = false)
    private SqlQueries sqlQuery;

    @JoinColumn(name = "ID_DATA_TYPE", referencedColumnName = "ID_DATA_TYPE")
    @ManyToOne(optional = false)
    private CDataTypes dataType;

    public SqlQueryParameters() {
    }

    public SqlQueryParameters(Integer idParameter) {
        this.idParameter = idParameter;
    }

    public SqlQueryParameters(Integer idParameter, String parameterName) {
        this.idParameter = idParameter;
        this.parameterName = parameterName;
    }

    public Integer getIdParameter() {
        return idParameter;
    }

    public void setIdParameter(Integer idParameter) {
        this.idParameter = idParameter;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public SqlQueries getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(SqlQueries sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public CDataTypes getDataTypes() {
        return dataType;
    }

    public void setDataTypes(CDataTypes cDataTypes) {
        this.dataType = cDataTypes;
    }

    public int getIdSqlQuery() {
        return idSqlQuery;
    }

    public void setIdSqlQuery(int idSqlQuery) {
        this.idSqlQuery = idSqlQuery;
    }

    public int getIdDataType() {
        return idDataType;
    }

    public void setIdDataType(int idDataType) {
        this.idDataType = idDataType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParameter != null ? idParameter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SqlQueryParameters)) {
            return false;
        }
        SqlQueryParameters other = (SqlQueryParameters) object;
        if ((this.idParameter == null && other.idParameter != null) || (this.idParameter != null && !this.idParameter.equals(other.idParameter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.SqlQueryParameters[ idParameter=" + idParameter + " ]";
    }
    
}
