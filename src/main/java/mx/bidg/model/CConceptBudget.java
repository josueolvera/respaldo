/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@Table(name = "C_CONCEPT_BUDGET")
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CConceptBudget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CONCEPT_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idConceptBudget;

    @Size(max = 40)
    @Column(name = "NAME_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private String nameConcept;

    @Size(max = 25)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public CConceptBudget() {
    }

    public CConceptBudget(Integer idConceptToBudget) {
        this.idConceptBudget = idConceptToBudget;
    }

    public CConceptBudget(Integer idConceptToBudget, LocalDateTime creationDate) {
        this.idConceptBudget = idConceptToBudget;
        this.creationDate = creationDate;
    }

    public Integer getIdConceptBudget() {
        return idConceptBudget;
    }

    public void setIdConceptBudget(Integer idConceptToBudget) {
        this.idConceptBudget = idConceptToBudget;
    }

    public String getNameConcept() {
        return nameConcept;
    }

    public void setNameConcept(String nameConcept) {
        this.nameConcept = nameConcept;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConceptBudget != null ? idConceptBudget.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CConceptBudget)) {
            return false;
        }
        CConceptBudget other = (CConceptBudget) object;
        if ((this.idConceptBudget == null && other.idConceptBudget != null) || (this.idConceptBudget != null && !this.idConceptBudget.equals(other.idConceptBudget))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CConceptBudget[ idConceptToBudget=" + idConceptBudget + " ]";
    }

}
