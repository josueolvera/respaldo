/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_AREAS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CAreas implements Serializable {
        
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AREA")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private Integer idArea;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "AREA_NAME")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private String areaName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AREA_ROL", joinColumns = {
            @JoinColumn(name = "ID_AREA", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ID_ROLE",
                    nullable = false) })
    @JsonView(JsonViews.Embedded.class)
    private Set<CRoles> roles;

    @Column(name = "SAEM_FLAG")
    @JsonView(JsonViews.Root.class)
    private int saemFlag;

    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "USERNAME")
    @Size(max = 100)
    @JsonView(JsonViews.Root.class)
    private String username;

    public CAreas() {
    }

    public CAreas(Integer idArea) {
        this.idArea = idArea;
    }

    public CAreas(Integer idArea, String areaName) {
        this.idArea = idArea;
        this.areaName = areaName;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Set<CRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<CRoles> roles) {
        this.roles = roles;
    }

    public int getSaemFlag() {
        return saemFlag;
    }

    public void setSaemFlag(int saemFlag) {
        this.saemFlag = saemFlag;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAreas)) {
            return false;
        }
        CAreas other = (CAreas) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAreas[ idArea=" + idArea + " ]";
    }

}
