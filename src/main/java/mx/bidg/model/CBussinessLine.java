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
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC_YAIR
 */
@Entity
@Table(name = "C_BUSSINESS_LINE")
@DynamicUpdate

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CBussinessLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BUSINESS_LINE")
    private Integer idBusinessLine;

    @Size(max = 50)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    @Size(max = 20)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ACRONYM")
    private String acronym;

    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Size(max = 20)
    @Column(name = "USER_NAME")
    @JsonView(JsonViews.Root.class)
    private String userName;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private Boolean status;

    public CBussinessLine() {
    }

    public CBussinessLine(Integer idBusinessLine) {
        this.idBusinessLine = idBusinessLine;
    }

    public CBussinessLine(Integer idBusinessLine, LocalDateTime creationDate) {
        this.idBusinessLine = idBusinessLine;
        this.creationDate = creationDate;
    }

    public Integer getIdBusinessLine() {
        return idBusinessLine;
    }

    public void setIdBusinessLine(Integer idBusinessLine) {
        this.idBusinessLine = idBusinessLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBusinessLine != null ? idBusinessLine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBussinessLine)) {
            return false;
        }
        CBussinessLine other = (CBussinessLine) object;
        if ((this.idBusinessLine == null && other.idBusinessLine != null) || (this.idBusinessLine != null && !this.idBusinessLine.equals(other.idBusinessLine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBussinessLine[ idBusinessLine=" + idBusinessLine + " ]";
    }

}
