/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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
 * @author gerardo8
 */

@Entity
@DynamicUpdate
@Table(name = "C_SAP_FILE")
public class CSapFile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SAP_FILE")
    @JsonView(JsonViews.Root.class)
    private Integer idSapFile;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SAP_FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String sapFileName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String fileName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "LAYOUT_FILE_URL")
    @JsonView(JsonViews.Root.class)
    private String layoutFileUrl;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_UPLOADED_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime lastUploadedDate;

    public CSapFile() {
    }

    public CSapFile(Integer idSapFile) {
        this.idSapFile = idSapFile;
    }

    public CSapFile(String sapFileName, String fileName, LocalDateTime lastUploadedDate) {
        this.sapFileName = sapFileName;
        this.fileName = fileName;
        this.lastUploadedDate = lastUploadedDate;
    }

    public Integer getIdSapFile() {
        return idSapFile;
    }

    public void setIdSapFile(Integer idSapFile) {
        this.idSapFile = idSapFile;
    }

    public String getSapFileName() {
        return sapFileName;
    }

    public void setSapFileName(String sapFileName) {
        this.sapFileName = sapFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getLastUploadedDate() {
        return lastUploadedDate;
    }

    public void setLastUploadedDate(LocalDateTime lastUploadedDate) {
        this.lastUploadedDate = lastUploadedDate;
    }

    public DateFormatsPojo getLastUploadedDateFormats() {
        return new DateFormatsPojo(lastUploadedDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSapFile != null ? idSapFile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSapFile)) {
            return false;
        }
        CSapFile other = (CSapFile) object;
        if ((this.idSapFile == null && other.idSapFile != null) || (this.idSapFile != null && !this.idSapFile.equals(other.idSapFile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CSapFile[ idSapFile=" + idSapFile + " ]";
    }
    
}
