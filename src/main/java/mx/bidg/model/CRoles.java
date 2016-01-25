/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_ROLES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CRoles implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idRole;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String roleName;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    @JsonView(JsonViews.Embedded.class)
    private List<DwEmployees> dwEmployeesList;

    public CRoles() {
    }

    public CRoles(Integer idRole) {
        this.idRole = idRole;
    }

    public CRoles(Integer idRole, String roleName) {
        this.idRole = idRole;
        this.roleName = roleName;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<DwEmployees> getDwEmployeesList() {
        return dwEmployeesList;
    }

    public void setDwEmployeesList(List<DwEmployees> dwEmployeesList) {
        this.dwEmployeesList = dwEmployeesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRole != null ? idRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CRoles)) {
            return false;
        }
        CRoles other = (CRoles) object;
        if ((this.idRole == null && other.idRole != null) || (this.idRole != null && !this.idRole.equals(other.idRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CRoles[ idRole=" + idRole + " ]";
    }
    
}
