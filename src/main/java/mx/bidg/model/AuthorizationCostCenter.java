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
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "AUTHORIZATION_COST_CENTER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AuthorizationCostCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUTHORIZATION_COST_CENTER")
    @JsonView(JsonViews.Root.class)
    private Integer idAuthorizationCostCenter;

    @Column(name = "ID_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;

    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @Size(max = 255)
    @Column(name = "REASON")
    @JsonView(JsonViews.Root.class)
    private String reason;

    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private Integer year;

    @Column(name = "VALIDATION")
    @JsonView(JsonViews.Root.class)
    private Boolean validation;

    @Column(name = "AUTHORIZATION")
    @JsonView(JsonViews.Root.class)
    private Boolean authorization;

    @Column(name = "MODIFY")
    @JsonView(JsonViews.Root.class)
    private Integer modify;

    @JoinColumn(name = "ID_COST_CENTER", referencedColumnName = "ID_COST_CENTER")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CCostCenter costCenter;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private Users users;

    public AuthorizationCostCenter() {
    }

    public AuthorizationCostCenter(Integer idAuthorizationCostCenter) {
        this.idAuthorizationCostCenter = idAuthorizationCostCenter;
    }

    public Integer getIdAuthorizationCostCenter() {
        return idAuthorizationCostCenter;
    }

    public void setIdAuthorizationCostCenter(Integer idAuthorizationCostCenter) {
        this.idAuthorizationCostCenter = idAuthorizationCostCenter;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getValidation() {
        return validation;
    }

    public void setValidation(Boolean validation) {
        this.validation = validation;
    }

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }

    public Integer getModify() {
        return modify;
    }

    public void setModify(Integer modify) {
        this.modify = modify;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CCostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthorizationCostCenter != null ? idAuthorizationCostCenter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthorizationCostCenter)) {
            return false;
        }
        AuthorizationCostCenter other = (AuthorizationCostCenter) object;
        if ((this.idAuthorizationCostCenter == null && other.idAuthorizationCostCenter != null) || (this.idAuthorizationCostCenter != null && !this.idAuthorizationCostCenter.equals(other.idAuthorizationCostCenter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AuthorizationCostCenter[ idAuthorizationCostCenter=" + idAuthorizationCostCenter + " ]";
    }

}
