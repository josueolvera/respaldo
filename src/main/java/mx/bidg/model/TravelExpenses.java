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
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static java.time.temporal.ChronoUnit.DAYS;

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
    @Column(name = "ID_TRAVEL_EXPENSE")
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpense;

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

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Requests request;

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

    public TravelExpenses(Integer idTravelExpense) {
        this.idTravelExpense = idTravelExpense;
    }

    public TravelExpenses(Integer idTravelExpense, String destination, LocalDateTime endDate, int estimatedKm, LocalDateTime startDate, LocalDateTime creationDate) {
        this.idTravelExpense = idTravelExpense;
        this.destination = destination;
        this.endDate = endDate;
        this.estimatedKm = estimatedKm;
        this.startDate = startDate;
        this.creationDate = creationDate;
    }

    public Integer getIdTravelExpense() {
        return idTravelExpense;
    }

    public void setIdTravelExpense(Integer idTravelExpense) {
        this.idTravelExpense = idTravelExpense;
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

    public boolean getIsOutOfDateRequest() {

        if (startDate != null) {
            long daysBetween = DAYS.between(LocalDateTime.now().toLocalDate(), startDate.toLocalDate());

            return daysBetween < 3;
        } else {
            return false;
        }
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

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Requests getRequest() {
        return request;
    }

    public void setRequest(Requests request) {
        this.request = request;
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
        hash += (idTravelExpense != null ? idTravelExpense.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TravelExpenses)) {
            return false;
        }
        TravelExpenses other = (TravelExpenses) object;
        if ((this.idTravelExpense == null && other.idTravelExpense != null) || (this.idTravelExpense != null && !this.idTravelExpense.equals(other.idTravelExpense))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TravelExpenses[ idTravelExpense=" + idTravelExpense + " ]";
    }
    
}
