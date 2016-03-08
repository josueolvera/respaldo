package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "EMAIL_TEMPLATES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class EmailTemplates implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMAIL_TEMPLATE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmailTemplate;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TEMPLATE_NAME")
    @JsonView(JsonViews.Root.class)
    private String templateName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "TEMPLATE_FILE")
    @JsonView(JsonViews.Root.class)
    private String templateFile;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "SENDER_ADDRESS")
    @JsonView(JsonViews.Root.class)
    private String senderAddress;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "SENDER_NAME")
    @JsonView(JsonViews.Root.class)
    private String senderName;

    @Lob
    @Size(max = 65535)
    @Column(name = "EMAIL_SUBJECT")
    @JsonView(JsonViews.Root.class)
    private String emailSubject;

    @Size(max = 300)
    @Column(name = "MESSAGE_TITLE")
    @JsonView(JsonViews.Root.class)
    private String messageTitle;

    @Lob
    @Size(max = 65535)
    @Column(name = "MESSAGE")
    @JsonView(JsonViews.Root.class)
    private String message;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "SMTP_USER")
    @JsonView(JsonViews.Root.class)
    private String smtpUser;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "SMTP_PASSWORD")
    @JsonView(JsonViews.Root.class)
    private String smtpPassword;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "SMTP_HOST")
    @JsonView(JsonViews.Root.class)
    private String smtpHost;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SMTP_PORT")
    @JsonView(JsonViews.Root.class)
    private int smtpPort;

    @ManyToMany(mappedBy = "emailTemplatesList")
    @JsonView(JsonViews.Embedded.class)
    private List<EmailRecipients> emailRecipientsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emailTemplate")
    @JsonView(JsonViews.Embedded.class)
    private List<EmailTemplateFiles> emailTemplateFilesList;

    @Transient
    @JsonView(JsonViews.Private.class)
    private HashMap<String, Object> properties;

    @Transient
    @JsonView(JsonViews.Private.class)
    private List<EmailTemplateFiles> aditionalFiles;

    public EmailTemplates() {
    }

    public EmailTemplates(Integer idEmailTemplate) {
        this.idEmailTemplate = idEmailTemplate;
    }

    public EmailTemplates(Integer idEmailTemplate, String templateName, String templateFile, String senderAddress, String smtpUser, String smtpPassword, String smtpHost, int smtpPort) {
        this.idEmailTemplate = idEmailTemplate;
        this.templateName = templateName;
        this.templateFile = templateFile;
        this.senderAddress = senderAddress;
        this.smtpUser = smtpUser;
        this.smtpPassword = smtpPassword;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public Integer getIdEmailTemplate() {
        return idEmailTemplate;
    }

    public void setIdEmailTemplate(Integer idEmailTemplate) {
        this.idEmailTemplate = idEmailTemplate;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public List<EmailRecipients> getEmailRecipientsList() {
        return emailRecipientsList;
    }

    public void setEmailRecipientsList(List<EmailRecipients> emailRecipientsList) {
        this.emailRecipientsList = emailRecipientsList;
    }

    public List<EmailTemplateFiles> getEmailTemplateFilesList() {
        return emailTemplateFilesList;
    }

    public void setEmailTemplateFilesList(List<EmailTemplateFiles> emailTemplateFilesList) {
        this.emailTemplateFilesList = emailTemplateFilesList;
    }

    public List<EmailTemplateFiles> getAditionalFiles() {
        return aditionalFiles;
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public void addRecipient(EmailRecipients recipient) {
        if (this.emailRecipientsList == null) {
            this.emailRecipientsList = new ArrayList<>();
        }
        this.emailRecipientsList.add(recipient);
    }

    public void addFile(EmailTemplateFiles file) {
        if (this.aditionalFiles == null) {
            this.aditionalFiles = new ArrayList<>();
        }
        this.aditionalFiles.add(file);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmailTemplate != null ? idEmailTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailTemplates)) {
            return false;
        }
        EmailTemplates other = (EmailTemplates) object;
        if ((this.idEmailTemplate == null && other.idEmailTemplate != null) || (this.idEmailTemplate != null && !this.idEmailTemplate.equals(other.idEmailTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.EmailTemplates[ idEmailTemplate=" + idEmailTemplate + " ]";
    }
    
}
