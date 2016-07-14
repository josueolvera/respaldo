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
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "REQUEST_CONCEPT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RequestConcept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REQUEST_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idRequestConcept;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_TRAVEL_EXPENCE_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpenceConcept;

    @JoinColumn(name = "ID_TRAVEL_EXPENCE_CONCEPT", referencedColumnName = "ID_TRAVEL_EXPENCE_CONCEPT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelExpensesConcepts travelExpensesConcepts;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo creationDateFormats;

    public RequestConcept() {
    }

    public RequestConcept(Integer idRequestConcept) {
        this.idRequestConcept = idRequestConcept;
    }

    public RequestConcept(Integer idRequestConcept, BigDecimal amount, LocalDateTime creationDate) {
        this.idRequestConcept = idRequestConcept;
        this.amount = amount;
        this.creationDate = creationDate;
    }

    public Integer getIdRequestConcept() {
        return idRequestConcept;
    }

    public void setIdRequestConcept(Integer idRequestConcept) {
        this.idRequestConcept = idRequestConcept;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdTravelExpenceConcept() {
        return idTravelExpenceConcept;
    }

    public void setIdTravelExpenceConcept(Integer idTravelExpenceConcept) {
        this.idTravelExpenceConcept = idTravelExpenceConcept;
    }

    public CTravelExpensesConcepts getTravelExpensesConcepts() {
        return travelExpensesConcepts;
    }

    public void setTravelExpensesConcepts(CTravelExpensesConcepts travelExpensesConcepts) {
        this.travelExpensesConcepts = travelExpensesConcepts;
    }

    public DateFormatsPojo getCreationDateFormats() {
        this.creationDateFormats = (creationDate == null) ? null : new DateFormatsPojo(creationDate);
        return this.creationDateFormats;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestConcept != null ? idRequestConcept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestConcept)) {
            return false;
        }
        RequestConcept other = (RequestConcept) object;
        if ((this.idRequestConcept == null && other.idRequestConcept != null) || (this.idRequestConcept != null && !this.idRequestConcept.equals(other.idRequestConcept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RequestConcept[ idRequestConcept=" + idRequestConcept + " ]";
    }
    
}
