/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.StringFormatter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "EMPLOYEES_HISTORY")
public class EmployeesHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EH")
    @JsonView(JsonViews.Root.class)
    private Integer idEh;
    
    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "ID_DISTRIBUTOR")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Size(max = 25)
    @Column(name = "DISTRIBUTOR_NAME")
    @JsonView(JsonViews.Root.class)
    private String distributorName;

    @Column(name = "ID_AREA")
    @JsonView(JsonViews.Root.class)
    private Integer idArea;

    @Size(max = 25)
    @Column(name = "AREA_NAME")
    @JsonView(JsonViews.Root.class)
    private String areaName;

    @Column(name = "ID_REGION")
    @JsonView(JsonViews.Root.class)
    private Integer idRegion;

    @Size(max = 30)
    @Column(name = "REGION_NAME")
    @JsonView(JsonViews.Root.class)
    private String regionName;

    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;

    @Size(max = 30)
    @Column(name = "BRANCH_SHORT")
    @JsonView(JsonViews.Root.class)
    private String branchShort;

    @Size(max = 50)
    @Column(name = "BRANCH_NAME")
    @JsonView(JsonViews.Root.class)
    private String branchName;

    @Column(name = "ID_GROUP")
    @JsonView(JsonViews.Root.class)
    private Integer idGroup;

    @Size(max = 50)
    @Column(name = "GROUP_NAME")
    @JsonView(JsonViews.Root.class)
    private String groupName;

    @Size(max = 45)
    @Column(name = "GROUP_ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String groupAcronyms;

    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @Size(max = 50)
    @Column(name = "ROLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String roleName;

    @Size(max = 10)
    @Column(name = "EMPLOYEE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String employeeNumber;

    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    @JsonView(JsonViews.Root.class)
    private String firstName;

    @Size(max = 50)
    @Column(name = "MIDDLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String middleName;

    @Size(max = 40)
    @Column(name = "PARENTAL_LAST")
    @JsonView(JsonViews.Root.class)
    private String parentalLast;

    @Size(max = 40)
    @Column(name = "MOTHER_LAST")
    @JsonView(JsonViews.Root.class)
    private String motherLast;

    @Size(min = 1, max = 13)
    @Column(name = "RFC")
    @JsonView(JsonViews.Root.class)
    private String rfc;

    @Size(max = 15)
    @Column(name = "CLAVE_SAP")
    @JsonView(JsonViews.Root.class)
    private String claveSap;

    @Size(max = 18)
    @Column(name = "CURP")
    @JsonView(JsonViews.Root.class)
    private String curp;

    @Size(max = 50)
    @Column(name = "MAIL")
    @JsonView(JsonViews.Root.class)
    private String mail;

    @Column(name = "EMPLOYEE_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer employeeType;

    @Column(name = "CONTRACT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer contractType;

    @Column(name = "SALARY")
    @JsonView(JsonViews.Root.class)
    private BigDecimal salary;

    @Column(name = "JOIN_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime joinDate;

    @Column(name = "ID_EDUCATION")
    @JsonView(JsonViews.Root.class)
    private Integer idEducation;

    @Size(max = 30)
    @Column(name = "EDUCATION")
    @JsonView(JsonViews.Root.class)
    private String education;

    @Column(name = "GENDER")
    @JsonView(JsonViews.Root.class)
    private Integer gender;

    @Column(name = "ID_STATUS_MARITAL")
    @JsonView(JsonViews.Root.class)
    private Integer idStatusMarital;

    @Size(max = 15)
    @Column(name = "STATUS_MARITAL")
    @JsonView(JsonViews.Root.class)
    private String statusMarital;

    @Size(max = 50)
    @Column(name = "BIRTHPLACE")
    @JsonView(JsonViews.Root.class)
    private String birthplace;

    @Column(name = "BIRTHDATE")
    @Convert(converter = DateConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDate birthdate;

    @Size(max = 50)
    @Column(name = "STATE")
    @JsonView(JsonViews.Root.class)
    private String state;

    @Size(max = 80)
    @Column(name = "STREET")
    @JsonView(JsonViews.Root.class)
    private String street;

    @Size(max = 15)
    @Column(name = "EXTERIOR_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String exteriorNumber;

    @Size(max = 15)
    @Column(name = "INTERIOR_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String interiorNumber;

    @Size(max = 100)
    @Column(name = "COLONIA")
    @JsonView(JsonViews.Root.class)
    private String colonia;

    @Size(max = 50)
    @Column(name = "DELEGATION_MUNICIPALITY")
    @JsonView(JsonViews.Root.class)
    private String delegationMunicipality;

    @Size(max = 5)
    @Column(name = "POSTCODE")
    @JsonView(JsonViews.Root.class)
    private String postcode;

    @Size(max = 25)
    @Column(name = "CELL_PHONE")
    @JsonView(JsonViews.Root.class)
    private String cellPhone;

    @Size(max = 25)
    @Column(name = "HOME_PHONE")
    @JsonView(JsonViews.Root.class)
    private String homePhone;

    @Size(max = 3)
    @Column(name = "SIZE")
    @JsonView(JsonViews.Root.class)
    private String size;

    @Column(name = "SIZE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private Integer sizeNumber;

    @Size(max = 150)
    @Column(name = "FATHER_NAME")
    @JsonView(JsonViews.Root.class)
    private String fatherName;

    @Size(max = 150)
    @Column(name = "MOTHER_NAME")
    @JsonView(JsonViews.Root.class)
    private String motherName;

    @Size(max = 45)
    @Column(name = "ACCOUNT_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String accountNumber;

    @Size(max = 45)
    @Column(name = "ACCOUNT_CLABE")
    @JsonView(JsonViews.Root.class)
    private String accountClabe;

    @Column(name = "ACCOUNT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer accountType;

    @Size(max = 45)
    @Column(name = "BANK_ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String bankAcronyms;

    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "ID_ACTION_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idActionType;

    @Size(min = 1, max = 50)
    @Column(name = "ACTION_TYPE")
    @JsonView(JsonViews.Root.class)
    private String actionType;

    @Column(name = "H_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer hStatus;

    public EmployeesHistory() {
    }

    public EmployeesHistory(Integer idEh) {
        this.idEh = idEh;
    }

    public EmployeesHistory(Integer idEh, Integer idEmployee, Integer idDistributor, Integer idRegion, Integer idBranch, Integer idGroup, Integer idRole, String rfc, String username, LocalDateTime creationDate, String actionType, Integer hStatus) {
        this.idEh = idEh;
        this.idEmployee = idEmployee;
        this.idDistributor = idDistributor;
        this.idRegion = idRegion;
        this.idBranch = idBranch;
        this.idGroup = idGroup;
        this.idRole = idRole;
        this.rfc = rfc;
        this.username = username;
        this.creationDate = creationDate;
        this.actionType = actionType;
        this.hStatus = hStatus;
    }

    public Integer getIdEh() {
        return idEh;
    }

    public void setIdEh(Integer idEh) {
        this.idEh = idEh;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public Integer getIdEducation() {
        return idEducation;
    }

    public void setIdEducation(Integer idEducation) {
        this.idEducation = idEducation;
    }

    public Integer getIdStatusMarital() {
        return idStatusMarital;
    }

    public void setIdStatusMarital(Integer idStatusMarital) {
        this.idStatusMarital = idStatusMarital;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public String getBranchShort() {
        return branchShort;
    }

    public void setBranchShort(String branchShort) {
        this.branchShort = branchShort;
    }



    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getParentalLast() {
        return parentalLast;
    }

    public void setParentalLast(String parentalLast) {
        this.parentalLast = parentalLast;
    }

    public String getMotherLast() {
        return motherLast;
    }

    public void setMotherLast(String motherLast) {
        this.motherLast = motherLast;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getStatusMarital() {
        return statusMarital;
    }

    public void setStatusMarital(String statusMarital) {
        this.statusMarital = statusMarital;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getExteriorNumber() {
        return exteriorNumber;
    }

    public void setExteriorNumber(String exteriorNumber) {
        this.exteriorNumber = exteriorNumber;
    }

    public String getInteriorNumber() {
        return interiorNumber;
    }

    public void setInteriorNumber(String interiorNumber) {
        this.interiorNumber = interiorNumber;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegationMunicipality() {
        return delegationMunicipality;
    }

    public void setDelegationMunicipality(String delegationMunicipality) {
        this.delegationMunicipality = delegationMunicipality;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getSizeNumber() {
        return sizeNumber;
    }

    public void setSizeNumber(Integer sizeNumber) {
        this.sizeNumber = sizeNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountClabe() {
        return accountClabe;
    }

    public void setAccountClabe(String accountClabe) {
        this.accountClabe = accountClabe;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getGroupAcronyms() {
        return groupAcronyms;
    }

    public void setGroupAcronyms(String groupAcronyms) {
        this.groupAcronyms = groupAcronyms;
    }

    public String getBankAcronyms() {
        return bankAcronyms;
    }

    public void setBankAcronyms(String bankAcronyms) {
        this.bankAcronyms = bankAcronyms;
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

    public Integer getIdActionType() {
        return idActionType;
    }

    public void setIdActionType(Integer idActionType) {
        this.idActionType = idActionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Integer getHStatus() {
        return hStatus;
    }

    public void setHStatus(Integer hStatus) {
        this.hStatus = hStatus;
    }

    public String getFullName() {
        return StringFormatter.concatWithoutNull(firstName, middleName, parentalLast, motherLast);
    }

    public DateFormatsPojo getJoinDateFormats() {
        if (joinDate == null) {
            return null;
        }
        return new DateFormatsPojo(joinDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEh != null ? idEh.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeesHistory)) {
            return false;
        }
        EmployeesHistory other = (EmployeesHistory) object;
        if ((this.idEh == null && other.idEh != null) || (this.idEh != null && !this.idEh.equals(other.idEh))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.EmployeesHistory[ idEh=" + idEh + " ]";
    }
    
}
