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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "PROVIDERS_CONTACT")

public class ProvidersContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROVIDER_CONTACT")
    @JsonView(JsonViews.Root.class)
    private Integer idProviderContact;

    @Size(max = 100)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    @Size(max = 30)
    @Column(name = "POST")
    @JsonView(JsonViews.Root.class)
    private String post;

    @Size(max = 100)
    @Column(name = "EMAIL")
    @JsonView(JsonViews.Root.class)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PHONE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String phoneNumber;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @Column(name="ID_PROVIDER", insertable=false, updatable=false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Providers provider;

    public ProvidersContact() {
    }

    public ProvidersContact(Integer idProviderContact) {
        this.idProviderContact = idProviderContact;
    }

    public ProvidersContact(Integer idProviderContact, String phoneNumber, int idAccessLevel) {
        this.idProviderContact = idProviderContact;
        this.phoneNumber = phoneNumber;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdProviderContact() {
        return idProviderContact;
    }

    public void setIdProviderContact(Integer idProviderContact) {
        this.idProviderContact = idProviderContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProviderContact != null ? idProviderContact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvidersContact)) {
            return false;
        }
        ProvidersContact other = (ProvidersContact) object;
        if ((this.idProviderContact == null && other.idProviderContact != null) || (this.idProviderContact != null && !this.idProviderContact.equals(other.idProviderContact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ProvidersContact[ idProviderContact=" + idProviderContact + " ]";
    }
    
}
