package mx.bidg.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_TICKETS_CATEGORIES")
public class CTicketsCategories implements Serializable {

    public static final CTicketsCategories DISEÑO = new CTicketsCategories(1);
    public static final CTicketsCategories SOPORTE_TECNICO = new CTicketsCategories(2);
    public static final CTicketsCategories DESARROLLO = new CTicketsCategories(3);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TICKET_CATEGORY")
    private Integer idTicketCategory;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "ID_EMAIL_TEMPLATE", updatable = false, insertable = false)
    private int idEmailTemplate;

    @NotNull
    @JoinColumn(name = "ID_EMAIL_TEMPLATE", referencedColumnName = "ID_EMAIL_TEMPLATE",
            foreignKey = @ForeignKey(name = "C_TICKETS_CATEGORIES_EMAIL_TEMPLATES"))
    @ManyToOne(optional = false)
    private EmailTemplates emailTemplate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketCategory")
    private Set<CIncidence> incidences;

    public CTicketsCategories() {
    }

    public CTicketsCategories(Integer idTicketCategory) {
        this.idTicketCategory = idTicketCategory;
    }

    public CTicketsCategories(Integer idTicketCategory, String categoryName) {
        this.idTicketCategory = idTicketCategory;
        this.categoryName = categoryName;
    }

    public Integer getIdTicketCategory() {
        return idTicketCategory;
    }

    public void setIdTicketCategory(Integer idTicketCategory) {
        this.idTicketCategory = idTicketCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<CIncidence> getIncidences() {
        return incidences;
    }

    public void setIncidences(Set<CIncidence> cIncidenceSet) {
        this.incidences = cIncidenceSet;
    }

    public int getIdEmailTemplate() {
        return idEmailTemplate;
    }

    public void setIdEmailTemplate(int idEmailTemplate) {
        this.idEmailTemplate = idEmailTemplate;
    }

    public EmailTemplates getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplates emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTicketCategory != null ? idTicketCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTicketsCategories)) {
            return false;
        }
        CTicketsCategories other = (CTicketsCategories) object;
        if ((this.idTicketCategory == null && other.idTicketCategory != null) || (this.idTicketCategory != null && !this.idTicketCategory.equals(other.idTicketCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTicketsCategories[ idTicketCategory=" + idTicketCategory + " ]";
    }
    
}
