package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Rafael Viveros
 */
@Entity
@Table(name = "C_SQL_DICTIONARY")
public class CSqlDictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DICTIONARY")
    @JsonView(JsonViews.Root.class)
    private Integer idDictionary;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "USER_TEXT")
    @JsonView(JsonViews.Root.class)
    private String userText;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "SQL_TEXT")
    @JsonView(JsonViews.Root.class)
    private String sqlText;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;
    
    @Column(name = "PARAMETERS_NUM")
    @JsonView(JsonViews.Root.class)
    private Integer parametersNum;
    
    @Column(name = "ID_SQL_FUNCTIONS_CATEGORIES", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idSqlFunctionsCategories;
    
    @JoinColumn(name = "ID_SQL_FUNCTIONS_CATEGORIES", referencedColumnName = "ID_CATEGORY")
    @ManyToOne()
    @JsonView(JsonViews.Embedded.class)
    private CSqlFunctionsCategories sqlFunctionsCategories;
    
    @Column(name = "ID_TASK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTask;
    
    @JoinColumn(name= "ID_TASK", referencedColumnName = "ID_TASK")
    @ManyToOne()
    @JsonView(JsonViews.Embedded.class)
    private CTasks cTasks;
    
    

    public CSqlDictionary() {
    }

    public CSqlDictionary(Integer idDictionary) {
        this.idDictionary = idDictionary;
    }

    public CSqlDictionary(Integer idDictionary, String userText, String sqlText, int status) {
        this.idDictionary = idDictionary;
        this.userText = userText;
        this.sqlText = sqlText;
        this.status = status;
    }

    public Integer getIdDictionary() {
        return idDictionary;
    }

    public void setIdDictionary(Integer idDictionary) {
        this.idDictionary = idDictionary;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getParametersNum() {
        return parametersNum;
    }

    public void setParametersNum(Integer parametersNum) {
        this.parametersNum = parametersNum;
    }

    public CSqlFunctionsCategories getSqlFunctionsCategories() {
        return sqlFunctionsCategories;
    }

    public void setSqlFunctionsCategories(CSqlFunctionsCategories sqlFunctionsCategories) {
        this.sqlFunctionsCategories = sqlFunctionsCategories;
    }
    
    public Integer getIdSqlFunctionsCategories() {
        return idSqlFunctionsCategories;
    }

    public void setIdSqlFunctionsCategories(Integer idSqlFunctionsCategories) {
        this.idSqlFunctionsCategories = idSqlFunctionsCategories;
    }
    
    public CTasks getcTasks() {
        return cTasks;
    }

    public void setcTasks(CTasks cTasks) {
        this.cTasks = cTasks;
    }
    
    @Override
    @JsonProperty("hashCode")
    public int hashCode() {
        int hash = 0;
        hash += (idDictionary != null ? idDictionary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSqlDictionary)) {
            return false;
        }
        CSqlDictionary other = (CSqlDictionary) object;
        if ((this.idDictionary == null && other.idDictionary != null) || (this.idDictionary != null && !this.idDictionary.equals(other.idDictionary))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CSqlDictionary[ idDictionary=" + idDictionary + " ]";
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

}
