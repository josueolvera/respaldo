/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.TimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "DW_EMPLOYEES")
@NamedQueries({
    @NamedQuery(name = "DwEmployees.findAll", query = "SELECT d FROM DwEmployees d")})
public class DwEmployees implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DW_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idDwEmployee;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = TimeConverter.class)
    private LocalTime creationDate;
    
    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idemployee;
    
    @Column(name = "ID_DW_ENTERPRISE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer iddwemployee;
    
    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idrole;
    
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Employees idEmployee;
    
    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private DwEnterprises idDwEnterprise;
    
    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRoles idRole;
    
    @OneToMany(mappedBy = "idDwEmployee")
    @JsonView(JsonViews.Embedded.class)
    private List<Users> usersList;

    public DwEmployees() {
    }

    public DwEmployees(Integer idDwEmployee) {
        this.idDwEmployee = idDwEmployee;
    }

    public Integer getIdDwEmployee() {
        return idDwEmployee;
    }

    public void setIdDwEmployee(Integer idDwEmployee) {
        this.idDwEmployee = idDwEmployee;
    }

    public LocalTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public Employees getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employees idEmployee) {
        this.idEmployee = idEmployee;
    }

    public DwEnterprises getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(DwEnterprises idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }
    
    public CRoles getIdRole() {
        return idRole;
    }

    public void setIdRole(CRoles idRole) {
        this.idRole = idRole;
    }

    public Integer getIdemployee() {
        return idemployee;
    }

    public void setIdemployee(Integer idemployee) {
        this.idemployee = idemployee;
    }

    public Integer getIddwemployee() {
        return iddwemployee;
    }

    public void setIddwemployee(Integer iddwemployee) {
        this.iddwemployee = iddwemployee;
    }

    public Integer getIdrole() {
        return idrole;
    }

    public void setIdrole(Integer idrole) {
        this.idrole = idrole;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDwEmployee != null ? idDwEmployee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DwEmployees)) {
            return false;
        }
        DwEmployees other = (DwEmployees) object;
        if ((this.idDwEmployee == null && other.idDwEmployee != null) || (this.idDwEmployee != null && !this.idDwEmployee.equals(other.idDwEmployee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.DwEmployees[ idDwEmployee=" + idDwEmployee + " ]";
    }
    
}
