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
@Table(name = "APPORTIONMENT_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class ApportionmentRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_APPORTIONMENT_ROLE")
    private Integer idApportionmentRole;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_GROUP_APPORTIONMENT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idGroupApportionment;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @JoinColumn(name = "ID_GROUP_APPORTIONMENT", referencedColumnName = "ID_GROUP_APPORTIONMENT")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private GroupApportionment groupApportionment;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRoles role;

    public ApportionmentRole() {
    }

    public ApportionmentRole(Integer idApportionmentRole) {
        this.idApportionmentRole = idApportionmentRole;
    }

    public Integer getIdApportionmentRole() {
        return idApportionmentRole;
    }

    public void setIdApportionmentRole(Integer idApportionmentRole) {
        this.idApportionmentRole = idApportionmentRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DateFormatsPojo getCrationDateDateFormats() {
        if (creationDate == null) {
            return null;
        }
        return new DateFormatsPojo(creationDate);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdGroupApportionment() {
        return idGroupApportionment;
    }

    public void setIdGroupApportionment(Integer idGroupApportionment) {
        this.idGroupApportionment = idGroupApportionment;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public GroupApportionment getGroupApportionment() {
        return groupApportionment;
    }

    public void setGroupApportionment(GroupApportionment groupApportionment) {
        this.groupApportionment = groupApportionment;
    }

    public CRoles getRole() {
        return role;
    }

    public void setRole(CRoles role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idApportionmentRole != null ? idApportionmentRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApportionmentRole)) {
            return false;
        }
        ApportionmentRole other = (ApportionmentRole) object;
        if ((this.idApportionmentRole == null && other.idApportionmentRole != null) || (this.idApportionmentRole != null && !this.idApportionmentRole.equals(other.idApportionmentRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.ApportionmentRole[ idApportionmentRole=" + idApportionmentRole + " ]";
    }
    
}
