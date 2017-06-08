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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_DATE_CALCULATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CDateCalculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DATE_CALCULATION")
    @JsonView(JsonViews.Root.class)
    private Integer idDateCalculation;

    @Size(max = 50)
    @Column(name = "NAME_DATE")
    @JsonView(JsonViews.Root.class)
    private String nameDate;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer status;

    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;


    public CDateCalculation() {
    }

    public CDateCalculation(Integer idDateCalculation) {
        this.idDateCalculation = idDateCalculation;
    }

    public Integer getIdDateCalculation() {
        return idDateCalculation;
    }

    public void setIdDateCalculation(Integer idDateCalculation) {
        this.idDateCalculation = idDateCalculation;
    }

    public String getNameDate() {
        return nameDate;
    }

    public void setNameDate(String nameDate) {
        this.nameDate = nameDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CDateCalculation)) return false;

        CDateCalculation that = (CDateCalculation) o;

        return idDateCalculation != null ? idDateCalculation.equals(that.idDateCalculation) : that.idDateCalculation == null;
    }

    @Override
    public int hashCode() {
        return idDateCalculation != null ? idDateCalculation.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CDateCalculation{" +
                "idDateCalculation=" + idDateCalculation +
                '}';
    }
}
