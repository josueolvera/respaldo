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
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "TRAVEL_EXPENSES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class TravelExpenses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRAVEL_EXPENCE")
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpence;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESTINATION")
    @JsonView(JsonViews.Root.class)
    private String destination;

    @Basic(optional = false)
    @NotNull
    @Column(name = "END_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime endDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTIMATED_KM")
    @JsonView(JsonViews.Root.class)
    private int estimatedKm;

    @Basic(optional = false)
    @NotNull
    @Column(name = "START_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime startDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_TRAVEL_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelType;

    @JoinColumn(name = "ID_TRAVEL_TYPE", referencedColumnName = "ID_TRAVEL_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CTravelTypes travelType;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo creationDateFormats;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo startDateFormats;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private DateFormatsPojo endDateFormats;

    public TravelExpenses() {
    }

    public TravelExpenses(Integer idTravelExpence) {
        this.idTravelExpence = idTravelExpence;
    }

    public TravelExpenses(Integer idTravelExpence, String destination, LocalDateTime endDate, int estimatedKm, LocalDateTime startDate, LocalDateTime creationDate) {
        this.idTravelExpence = idTravelExpence;
        this.destination = destination;
        this.endDate = endDate;
        this.estimatedKm = estimatedKm;
        this.startDate = startDate;
        this.creationDate = creationDate;
    }

    public Integer getIdTravelExpence() {
        return idTravelExpence;
    }

    public void setIdTravelExpence(Integer idTravelExpence) {
        this.idTravelExpence = idTravelExpence;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getEstimatedKm() {
        return estimatedKm;
    }

    public void setEstimatedKm(int estimatedKm) {
        this.estimatedKm = estimatedKm;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdTravelType() {
        return idTravelType;
    }

    public void setIdTravelType(Integer idTravelType) {
        this.idTravelType = idTravelType;
    }

    public CTravelTypes getTravelType() {
        return travelType;
    }

    public void setTravelType(CTravelTypes travelType) {
        this.travelType = travelType;
    }

    public DateFormatsPojo getCreationDateFormats() {
        this.creationDateFormats = (creationDate == null) ? null : new DateFormatsPojo(creationDate);
        return this.creationDateFormats;
    }

    public DateFormatsPojo getStartDateFormats() {
        this.startDateFormats = (startDate == null) ? null : new DateFormatsPojo(startDate);
        return this.startDateFormats;
    }

    public DateFormatsPojo getEndDateFormats() {
        this.endDateFormats = (endDate == null) ? null : new DateFormatsPojo(endDate);
        return this.endDateFormats;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTravelExpence != null ? idTravelExpence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TravelExpenses)) {
            return false;
        }
        TravelExpenses other = (TravelExpenses) object;
        if ((this.idTravelExpence == null && other.idTravelExpence != null) || (this.idTravelExpence != null && !this.idTravelExpence.equals(other.idTravelExpence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TravelExpenses[ idTravelExpence=" + idTravelExpence + " ]";
    }
    
}
