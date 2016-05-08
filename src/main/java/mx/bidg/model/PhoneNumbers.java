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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "PHONE_NUMBERS")


public class PhoneNumbers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PHONE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private Integer idPhoneNumber;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PHONE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private int phoneNumber;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;

    @Column(name="ID_PROVIDER", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Providers providers;

    public PhoneNumbers() {
    }

    public PhoneNumbers(Integer idPhoneNumber) {
        this.idPhoneNumber = idPhoneNumber;
    }

    public PhoneNumbers(Integer idPhoneNumber, int phoneNumber,int idAccessLevel) {
        this.idPhoneNumber = idPhoneNumber;
        this.phoneNumber = phoneNumber;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdPhoneNumber() {
        return idPhoneNumber;
    }

    public void setIdPhoneNumber(Integer idPhoneNumber) {
        this.idPhoneNumber = idPhoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Providers getIdProvider() {
        return providers;
    }

    public void setIdProvider(Providers providers) {
        this.providers = providers;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPhoneNumber != null ? idPhoneNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhoneNumbers)) {
            return false;
        }
        PhoneNumbers other = (PhoneNumbers) object;
        if ((this.idPhoneNumber == null && other.idPhoneNumber != null) || (this.idPhoneNumber != null && !this.idPhoneNumber.equals(other.idPhoneNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.PhoneNumbers[ idPhoneNumber=" + idPhoneNumber + " ]";
    }

}
