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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "GROUP_APPORTIONMENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class GroupApportionment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GROUP_APPORTIONMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idGroupApportionment;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERCENTAGE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal percentage;

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

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributor;

    public GroupApportionment() {
    }

    public GroupApportionment(Integer idGroupApportionment) {
        this.idGroupApportionment = idGroupApportionment;
    }

    public Integer getIdGroupApportionment() {
        return idGroupApportionment;
    }

    public void setIdGroupApportionment(Integer idGroupApportionment) {
        this.idGroupApportionment = idGroupApportionment;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGroupApportionment != null ? idGroupApportionment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupApportionment)) {
            return false;
        }
        GroupApportionment other = (GroupApportionment) object;
        if ((this.idGroupApportionment == null && other.idGroupApportionment != null) || (this.idGroupApportionment != null && !this.idGroupApportionment.equals(other.idGroupApportionment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.GroupApportionment[ idGroupApportionment=" + idGroupApportionment + " ]";
    }
    
}
