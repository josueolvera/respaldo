/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author Desarrollador
 */
@Entity
@DynamicUpdate
@Table(name = "CONCILIATION_FILE")
public class ConciliationFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CONCILIATION_FILE")
    @JsonView(JsonViews.Root.class)
    private Integer idConciliationFile;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String fileName;

    @Size(min = 1, max = 250)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
    private String filePath;

    @Basic(optional = true)
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = true)
    @Size(min = 1, max = 250)
    @Column(name = "REASON")
    @JsonView(JsonViews.Root.class)
    private String reason;

    @Basic(optional = true)
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    public ConciliationFile() {
    }

    public ConciliationFile(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public Integer getIdConciliationFile() {
        return idConciliationFile;
    }

    public void setIdConciliationFile(Integer idConciliationFile) {
        this.idConciliationFile = idConciliationFile;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConciliationFile that = (ConciliationFile) o;

        if (idConciliationFile != null ? !idConciliationFile.equals(that.idConciliationFile) : that.idConciliationFile != null)
            return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        return filePath != null ? filePath.equals(that.filePath) : that.filePath == null;

    }

    @Override
    public int hashCode() {
        int result = idConciliationFile != null ? idConciliationFile.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConciliationFile{" +
                "idConciliationFile=" + idConciliationFile +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

