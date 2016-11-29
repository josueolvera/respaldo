package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author Rafael Viveros
 */
@Entity
@DynamicUpdate
@Table(name = "SQL_QUERIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class SqlQueries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_QUERY")
    @JsonView(JsonViews.Root.class)
    private Integer idQuery;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "QUERY_NAME")
    @JsonView(JsonViews.Root.class)
    private String queryName;

    @Lob
    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 65535)
    @Column(name = "SQL_QUERY")
    @JsonView(JsonViews.Root.class)
    private String sqlQuery;

    @Lob
    @Basic(optional = true)
    @Size(min = 1, max = 65535)
    @Column(name = "HEADERS")
    @JsonView(JsonViews.Root.class)
    private String headers;

    @NotNull
    @Column(name = "SAVED")
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    private Boolean saved;

    @Column(name = "CALCULATE")
    @JsonView(JsonViews.Root.class)
    private Integer calculate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sqlQuery")
    @JsonView(JsonViews.Embedded.class)
    private Set<SqlQueryParameters> queryParameters;

    public SqlQueries() {
    }

    public SqlQueries(Integer idQuery) {
        this.idQuery = idQuery;
    }

    public SqlQueries(Integer idQuery, String queryName, String sqlQuery) {
        this.idQuery = idQuery;
        this.queryName = queryName;
        this.sqlQuery = sqlQuery;
    }

    public Integer getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(Integer idQuery) {
        this.idQuery = idQuery;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Set<SqlQueryParameters> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Set<SqlQueryParameters> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Boolean isSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Integer getCalculate() {
        return calculate;
    }

    public void setCalculate(Integer calculate) {
        this.calculate = calculate;
    }

    public Boolean getSaved() {
        return saved;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idQuery != null ? idQuery.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SqlQueries)) {
            return false;
        }
        SqlQueries other = (SqlQueries) object;
        if ((this.idQuery == null && other.idQuery != null) || (this.idQuery != null && !this.idQuery.equals(other.idQuery))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.SqlQueries[ idQuery=" + idQuery + " ]";
    }
    
}
