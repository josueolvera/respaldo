/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_TRAVEL_EXPENSES_CONCEPTS")
public class CTravelExpensesConcepts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRAVEL_EXPENSE_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpenseConcept;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CONCEPT_NAME")
    @JsonView(JsonViews.Root.class)
    private String conceptName;

    public CTravelExpensesConcepts() {
    }

    public CTravelExpensesConcepts(Integer idTravelExpenseConcept) {
        this.idTravelExpenseConcept = idTravelExpenseConcept;
    }

    public CTravelExpensesConcepts(Integer idTravelExpenseConcept, String conceptName) {
        this.idTravelExpenseConcept = idTravelExpenseConcept;
        this.conceptName = conceptName;
    }

    public Integer getIdTravelExpenseConcept() {
        return idTravelExpenseConcept;
    }

    public void setIdTravelExpenseConcept(Integer idTravelExpenseConcept) {
        this.idTravelExpenseConcept = idTravelExpenseConcept;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTravelExpenseConcept != null ? idTravelExpenseConcept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTravelExpensesConcepts)) {
            return false;
        }
        CTravelExpensesConcepts other = (CTravelExpensesConcepts) object;
        if ((this.idTravelExpenseConcept == null && other.idTravelExpenseConcept != null) || (this.idTravelExpenseConcept != null && !this.idTravelExpenseConcept.equals(other.idTravelExpenseConcept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTravelExpensesConcepts[ idTravelExpenseConcept=" + idTravelExpenseConcept + " ]";
    }
    
}
