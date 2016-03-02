package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "EMAIL_TEMPLATE_FILES")
public class EmailTemplateFiles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMAIL_TEMPLATE_FILE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmailTemplateFile;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
    private String filePath;

    @Size(max = 2048)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String fileName;

    @Size(max = 1024)
    @Column(name = "CONTENT_ID")
    @JsonView(JsonViews.Root.class)
    private String contentId;

    @Column(name = "ID_EMAIL_TEMPLATE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmailTemplate;

    @JoinColumn(name = "ID_EMAIL_TEMPLATE", referencedColumnName = "ID_EMAIL_TEMPLATE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private EmailTemplates emailTemplate;

    public EmailTemplateFiles() {
    }

    public EmailTemplateFiles(Integer idEmailTemplateFile) {
        this.idEmailTemplateFile = idEmailTemplateFile;
    }

    public EmailTemplateFiles(Integer idEmailTemplateFile, String filePath) {
        this.idEmailTemplateFile = idEmailTemplateFile;
        this.filePath = filePath;
    }

    public EmailTemplateFiles(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public EmailTemplateFiles(String filePath, String fileName, String contentId) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.contentId = contentId;
    }

    public Integer getIdEmailTemplateFile() {
        return idEmailTemplateFile;
    }

    public void setIdEmailTemplateFile(Integer idEmailTemplateFile) {
        this.idEmailTemplateFile = idEmailTemplateFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getIdEmailTemplate() {
        return idEmailTemplate;
    }

    public void setIdEmailTemplate(Integer idEmailTemplate) {
        this.idEmailTemplate = idEmailTemplate;
    }

    public EmailTemplates getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplates emailTemplates) {
        this.emailTemplate = emailTemplates;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmailTemplateFile != null ? idEmailTemplateFile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailTemplateFiles)) {
            return false;
        }
        EmailTemplateFiles other = (EmailTemplateFiles) object;
        if ((this.idEmailTemplateFile == null && other.idEmailTemplateFile != null) || (this.idEmailTemplateFile != null && !this.idEmailTemplateFile.equals(other.idEmailTemplateFile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.EmailTemplateFiles[ idEmailTemplateFile=" + idEmailTemplateFile + " ]";
    }
    
}
