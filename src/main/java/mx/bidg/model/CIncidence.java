package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "C_INCIDENCE")
public class CIncidence implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_INCIDENCE")
    @JsonView(JsonViews.Root.class)
    private Integer idIncidence;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "INCIDENCE_NAME")
    @JsonView(JsonViews.Root.class)
    private String incidenceName;

    @Column(name = "ID_TICKET_CATEGORY", updatable = false, insertable = false)
    @JsonView(JsonViews.Root.class)
    private int idTicketCategory;

    @JoinColumn(name = "ID_TICKET_CATEGORY", referencedColumnName = "ID_TICKET_CATEGORY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CTicketsCategories ticketCategory;

    public CIncidence() {
    }

    public CIncidence(Integer idIncidence) {
        this.idIncidence = idIncidence;
    }


    public Integer getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(Integer idIncidence) {
        this.idIncidence = idIncidence;
    }

    public String getIncidenceName() {
        return incidenceName;
    }

    public void setIncidenceName(String incidenceName) {
        this.incidenceName = incidenceName;
    }

    public CTicketsCategories getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(CTicketsCategories cTicketsCategories) {
        this.ticketCategory = cTicketsCategories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIncidence != null ? idIncidence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CIncidence)) {
            return false;
        }
        CIncidence other = (CIncidence) object;
        if ((this.idIncidence == null && other.idIncidence != null) || (this.idIncidence != null && !this.idIncidence.equals(other.idIncidence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CIncidence[ idIncidence=" + idIncidence + " ]";
    }
}
