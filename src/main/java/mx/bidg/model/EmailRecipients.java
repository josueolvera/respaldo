package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "EMAIL_RECIPIENTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class EmailRecipients implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int TO = 1;
    public static final int CC = 2;
    public static final int BCC = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMAIL_RECIPIENT")
    @JsonView(JsonViews.Root.class)
    private Integer idEmailRecipient;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "EMAIL_ADDRESS")
    @JsonView(JsonViews.Root.class)
    private String emailAddress;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "RECIPIENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String recipientName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RECIPIENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private int recipientType;

    @JoinTable(name = "TEMPLATE_RECIPIENTS", joinColumns = {
        @JoinColumn(name = "ID_EMAIL_RECIPIENT", referencedColumnName = "ID_EMAIL_RECIPIENT")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_EMAIL_TEMPLATE", referencedColumnName = "ID_EMAIL_TEMPLATE")})
    @ManyToMany
    @JsonView(JsonViews.Embedded.class)
    private List<EmailTemplates> emailTemplatesList;

    public EmailRecipients() {
    }

    public EmailRecipients(Integer idEmailRecipient) {
        this.idEmailRecipient = idEmailRecipient;
    }

    public EmailRecipients(Integer idEmailRecipient, String emailAddress, String recipientName, int recipientType) {
        this.idEmailRecipient = idEmailRecipient;
        this.emailAddress = emailAddress;
        this.recipientName = recipientName;
        this.recipientType = recipientType;
    }

    public EmailRecipients(String emailAddress, String recipientName, int recipientType) {
        this.emailAddress = emailAddress;
        this.recipientName = recipientName;
        this.recipientType = recipientType;
    }

    public Integer getIdEmailRecipient() {
        return idEmailRecipient;
    }

    public void setIdEmailRecipient(Integer idEmailRecipient) {
        this.idEmailRecipient = idEmailRecipient;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public int getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(int recipientType) {
        this.recipientType = recipientType;
    }

    public List<EmailTemplates> getEmailTemplatesList() {
        return emailTemplatesList;
    }

    public void setEmailTemplatesList(List<EmailTemplates> emailTemplatesList) {
        this.emailTemplatesList = emailTemplatesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmailRecipient != null ? idEmailRecipient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailRecipients)) {
            return false;
        }
        EmailRecipients other = (EmailRecipients) object;
        if ((this.idEmailRecipient == null && other.idEmailRecipient != null) || (this.idEmailRecipient != null && !this.idEmailRecipient.equals(other.idEmailRecipient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.EmailRecipients[ idEmailRecipient=" + idEmailRecipient + " ]";
    }
    
}
