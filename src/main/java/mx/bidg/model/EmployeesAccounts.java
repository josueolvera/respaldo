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
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "EMPLOYEES_ACCOUNTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class EmployeesAccounts implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_EMPLOYEE_ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployeeAccount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private int idEmployee;
    
    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Accounts idAccount;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    public EmployeesAccounts() {
    }

    public EmployeesAccounts(Integer idEmployeeAccount) {
        this.idEmployeeAccount = idEmployeeAccount;
    }

    public EmployeesAccounts(Integer idEmployeeAccount, int idEmployee) {
        this.idEmployeeAccount = idEmployeeAccount;
        this.idEmployee = idEmployee;
    }

    public Integer getIdEmployeeAccount() {
        return idEmployeeAccount;
    }

    public void setIdEmployeeAccount(Integer idEmployeeAccount) {
        this.idEmployeeAccount = idEmployeeAccount;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Accounts getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Accounts idAccount) {
        this.idAccount = idAccount;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmployeeAccount != null ? idEmployeeAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeesAccounts)) {
            return false;
        }
        EmployeesAccounts other = (EmployeesAccounts) object;
        if ((this.idEmployeeAccount == null && other.idEmployeeAccount != null) || (this.idEmployeeAccount != null && !this.idEmployeeAccount.equals(other.idEmployeeAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.EmployeesAccounts[ idEmployeeAccount=" + idEmployeeAccount + " ]";
    }
    
}
