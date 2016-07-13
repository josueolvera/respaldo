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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "PASSENGERS")
public class Passengers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PASSENGER")
    @JsonView(JsonViews.Root.class)
    private Integer idPassenger;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BIRTHDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Root.class)
    private Date birthdate;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "COMPANY")
    @JsonView(JsonViews.Root.class)
    private String company;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FREQUENT_PARTNER")
    @JsonView(JsonViews.Root.class)
    private String frequentPartner;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FULL_NAME")
    @JsonView(JsonViews.Root.class)
    private String fullName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "JOB")
    @JsonView(JsonViews.Root.class)
    private String job;

    @Size(max = 15)
    @Column(name = "PHONE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String phoneNumber;

    @Column(name = "ID_PLANE_TICKET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneTicket;

    @Column(name = "ID_PLANE_SEAT_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPlaneSeatType;

    @JoinColumn(name = "ID_PLANE_TICKET", referencedColumnName = "ID_PLANE_TICKET")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private PlaneTickets planeTicket;

    @JoinColumn(name = "ID_PLANE_SEAT_TYPE", referencedColumnName = "ID_PLANE_SEAT_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CPlaneSeatsTypes planeSeatType;

    @OneToMany(mappedBy = "passenger")
    private List<PassengerDocuments> passengerDocuments;

    public Passengers() {
    }

    public Passengers(Integer idPassenger) {
        this.idPassenger = idPassenger;
    }

    public Passengers(Integer idPassenger, Date birthdate, String company, String frequentPartner, String fullName, String job) {
        this.idPassenger = idPassenger;
        this.birthdate = birthdate;
        this.company = company;
        this.frequentPartner = frequentPartner;
        this.fullName = fullName;
        this.job = job;
    }

    public Integer getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(Integer idPassenger) {
        this.idPassenger = idPassenger;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFrequentPartner() {
        return frequentPartner;
    }

    public void setFrequentPartner(String frequentPartner) {
        this.frequentPartner = frequentPartner;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getIdPlaneTicket() {
        return idPlaneTicket;
    }

    public void setIdPlaneTicket(Integer idPlaneTicket) {
        this.idPlaneTicket = idPlaneTicket;
    }

    public Integer getIdPlaneSeatType() {
        return idPlaneSeatType;
    }

    public void setIdPlaneSeatType(Integer idPlaneSeatType) {
        this.idPlaneSeatType = idPlaneSeatType;
    }

    public PlaneTickets getPlaneTicket() {
        return planeTicket;
    }

    public void setPlaneTicket(PlaneTickets planeTicket) {
        this.planeTicket = planeTicket;
    }

    public CPlaneSeatsTypes getPlaneSeatType() {
        return planeSeatType;
    }

    public void setPlaneSeatType(CPlaneSeatsTypes planeSeatType) {
        this.planeSeatType = planeSeatType;
    }

    public List<PassengerDocuments> getPassengerDocuments() {
        return passengerDocuments;
    }

    public void setPassengerDocuments(List<PassengerDocuments> passengerDocuments) {
        this.passengerDocuments = passengerDocuments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPassenger != null ? idPassenger.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passengers)) {
            return false;
        }
        Passengers other = (Passengers) object;
        if ((this.idPassenger == null && other.idPassenger != null) || (this.idPassenger != null && !this.idPassenger.equals(other.idPassenger))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Passengers[ idPassenger=" + idPassenger + " ]";
    }
    
}
