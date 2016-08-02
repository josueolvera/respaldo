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
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.StringFormatter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@DynamicUpdate
@Table(name = "EMPLOYEES_HISTORY")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class EmployeesHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLOYEE_HISTORY")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployeeHistory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private int idEmployee;

    @Basic(optional = true)
    @Column(name = "ID_DW_ENTERPRISE", nullable=true)
    @JsonView(JsonViews.Root.class)
    private int idDwEnterprise;

    @Basic(optional = true)
    @Column(name = "ID_ACCOUNT", nullable=true)
    @JsonView(JsonViews.Root.class)
    private int idAccount;

    @Basic(optional = true)
    @Column(name = "ID_ROLE", nullable=true)
    @JsonView(JsonViews.Root.class)
    private int idRole;
    
    @Basic(optional = true)
    @Size(max = 10)
    @Column(name = "EMPLOYEE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String employeeNumber;

    @Basic(optional = true)
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

    @Size(max = 18)
    @Column(name = "IMSS")
    @JsonView(JsonViews.Root.class)
    private String imss;

    @Size(max = 15)
    @Column(name = "INFONAVIT_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String infonavitNumber;

    @Size(max = 50)
    @Column(name = "MAIL")
    @JsonView(JsonViews.Root.class)
    private String mail;
    
    @Basic
    @Column(name = "ID_EMPLOYEE_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployeeType;

    @Basic
    @Column(name = "ID_CONTRACT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idContractType;
    
    @Column(name = "SALARY")
    @JsonView(JsonViews.Root.class)
    private BigDecimal salary;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JOIN_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime joinDate;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer status;

    @Column(name = "ID_EDUCATION")
    @JsonView(JsonViews.Root.class)
    private Integer idEducation;

    @Column(name = "ID_GENDER")
    @JsonView(JsonViews.Root.class)
    private Integer idGender;

    @Column(name = "ID_STATUS_MARITAL")
    @JsonView(JsonViews.Root.class)
    private Integer idStatusMarital;

    @Size(max = 50)
    @Column(name = "BIRTHPLACE")
    @JsonView(JsonViews.Root.class)
    private String birthplace;

    @Column(name = "BIRTHDAY")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;

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
    @Column(name = "CITY")
    @JsonView(JsonViews.Root.class)
    private String city;

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

    @Column(name = "ID_SIZE")
    @JsonView(JsonViews.Root.class)
    private Integer idSize;

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

    @Column(name = "ID_ACTION_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idActionType;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "H_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer hStatus;

    @Column(name = "ID_DW_EMPLOYEE", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idDwEmployee;
    
    @Column(name = "ID_DISTRIBUTOR")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_REGION")
    @JsonView(JsonViews.Root.class)
    private Integer idRegion;
        
    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;

    @Column(name = "ID_AREA")
    @JsonView(JsonViews.Root.class)
    private Integer idArea;
    
    public EmployeesHistory() {
    }

    public EmployeesHistory(Integer idEmployeeHistory) {
        this.idEmployeeHistory = idEmployeeHistory;
    }

    public EmployeesHistory(int idEmployee, int idDwEnterprise, int idAccount, int idRole, String employeeNumber, String firstName, String middleName, String parentalLast, String motherLast, String rfc, String claveSap, String curp, String imss, String infonavitNumber, String mail, Integer idEmployeeType, Integer idContractType, BigDecimal salary, LocalDateTime joinDate, Integer status, Integer idEducation, Integer idGender, Integer idStatusMarital, String birthplace, LocalDate birthday, String state, String street, String exteriorNumber, String interiorNumber, String colonia, String city, String postcode, String cellPhone, String homePhone, Integer idSize, Integer sizeNumber, String fatherName, String motherName, Integer idActionType, LocalDateTime creationDate, Integer hStatus, Integer idDwEmployee, Integer idDistributor, Integer idRegion, Integer idBranch, Integer idArea) {
        this.idEmployee = idEmployee;
        this.idDwEnterprise = idDwEnterprise;
        this.idAccount = idAccount;
        this.idRole = idRole;
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.parentalLast = parentalLast;
        this.motherLast = motherLast;
        this.rfc = rfc;
        this.claveSap = claveSap;
        this.curp = curp;
        this.imss = imss;
        this.infonavitNumber = infonavitNumber;
        this.mail = mail;
        this.idEmployeeType = idEmployeeType;
        this.idContractType = idContractType;
        this.salary = salary;
        this.joinDate = joinDate;
        this.status = status;
        this.idEducation = idEducation;
        this.idGender = idGender;
        this.idStatusMarital = idStatusMarital;
        this.birthplace = birthplace;
        this.birthday = birthday;
        this.state = state;
        this.street = street;
        this.exteriorNumber = exteriorNumber;
        this.interiorNumber = interiorNumber;
        this.colonia = colonia;
        this.city = city;
        this.postcode = postcode;
        this.cellPhone = cellPhone;
        this.homePhone = homePhone;
        this.idSize = idSize;
        this.sizeNumber = sizeNumber;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.idActionType = idActionType;
        this.creationDate = creationDate;
        this.hStatus = hStatus;
        this.idDwEmployee = idDwEmployee;
        this.idDistributor = idDistributor;
        this.idRegion = idRegion;
        this.idBranch = idBranch;
        this.idArea = idArea;
    }

    public Integer getIdEmployeeHistory() {
        return idEmployeeHistory;
    }

    public void setIdEmployeeHistory(Integer idEmployeeHistory) {
        this.idEmployeeHistory = idEmployeeHistory;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(int idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
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

    public String getImss() {
        return imss;
    }

    public void setImss(String imss) {
        this.imss = imss;
    }

    public String getInfonavitNumber() {
        return infonavitNumber;
    }

    public void setInfonavitNumber(String infonavitNumber) {
        this.infonavitNumber = infonavitNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getIdEmployeeType() {
        return idEmployeeType;
    }

    public void setIdEmployeeType(Integer idEmployeeType) {
        this.idEmployeeType = idEmployeeType;
    }

    public Integer getIdContractType() {
        return idContractType;
    }

    public void setIdContractType(Integer idContractType) {
        this.idContractType = idContractType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdEducation() {
        return idEducation;
    }

    public void setIdEducation(Integer idEducation) {
        this.idEducation = idEducation;
    }

    public Integer getIdGender() {
        return idGender;
    }

    public void setIdGender(Integer idGender) {
        this.idGender = idGender;
    }

    public Integer getIdStatusMarital() {
        return idStatusMarital;
    }

    public void setIdStatusMarital(Integer idStatusMarital) {
        this.idStatusMarital = idStatusMarital;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Integer getIdSize() {
        return idSize;
    }

    public void setIdSize(Integer idSize) {
        this.idSize = idSize;
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

    public Integer getIdActionType() {
        return idActionType;
    }

    public void setIdActionType(Integer idActionType) {
        this.idActionType = idActionType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer gethStatus() {
        return hStatus;
    }

    public void sethStatus(Integer hStatus) {
        this.hStatus = hStatus;
    }

    public Integer getIdDwEmployee() {
        return idDwEmployee;
    }

    public void setIdDwEmployee(Integer idDwEmployee) {
        this.idDwEmployee = idDwEmployee;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeesHistory that = (EmployeesHistory) o;

        if (idEmployee != that.idEmployee) return false;
        if (idDwEnterprise != that.idDwEnterprise) return false;
        if (idAccount != that.idAccount) return false;
        if (idRole != that.idRole) return false;
        if (idEmployeeHistory != null ? !idEmployeeHistory.equals(that.idEmployeeHistory) : that.idEmployeeHistory != null)
            return false;
        if (employeeNumber != null ? !employeeNumber.equals(that.employeeNumber) : that.employeeNumber != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (parentalLast != null ? !parentalLast.equals(that.parentalLast) : that.parentalLast != null) return false;
        if (motherLast != null ? !motherLast.equals(that.motherLast) : that.motherLast != null) return false;
        if (rfc != null ? !rfc.equals(that.rfc) : that.rfc != null) return false;
        if (claveSap != null ? !claveSap.equals(that.claveSap) : that.claveSap != null) return false;
        if (curp != null ? !curp.equals(that.curp) : that.curp != null) return false;
        if (imss != null ? !imss.equals(that.imss) : that.imss != null) return false;
        if (infonavitNumber != null ? !infonavitNumber.equals(that.infonavitNumber) : that.infonavitNumber != null)
            return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (idEmployeeType != null ? !idEmployeeType.equals(that.idEmployeeType) : that.idEmployeeType != null)
            return false;
        if (idContractType != null ? !idContractType.equals(that.idContractType) : that.idContractType != null)
            return false;
        if (salary != null ? !salary.equals(that.salary) : that.salary != null) return false;
        if (joinDate != null ? !joinDate.equals(that.joinDate) : that.joinDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (idEducation != null ? !idEducation.equals(that.idEducation) : that.idEducation != null) return false;
        if (idGender != null ? !idGender.equals(that.idGender) : that.idGender != null) return false;
        if (idStatusMarital != null ? !idStatusMarital.equals(that.idStatusMarital) : that.idStatusMarital != null)
            return false;
        if (birthplace != null ? !birthplace.equals(that.birthplace) : that.birthplace != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (exteriorNumber != null ? !exteriorNumber.equals(that.exteriorNumber) : that.exteriorNumber != null)
            return false;
        if (interiorNumber != null ? !interiorNumber.equals(that.interiorNumber) : that.interiorNumber != null)
            return false;
        if (colonia != null ? !colonia.equals(that.colonia) : that.colonia != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        if (cellPhone != null ? !cellPhone.equals(that.cellPhone) : that.cellPhone != null) return false;
        if (homePhone != null ? !homePhone.equals(that.homePhone) : that.homePhone != null) return false;
        if (idSize != null ? !idSize.equals(that.idSize) : that.idSize != null) return false;
        if (sizeNumber != null ? !sizeNumber.equals(that.sizeNumber) : that.sizeNumber != null) return false;
        if (fatherName != null ? !fatherName.equals(that.fatherName) : that.fatherName != null) return false;
        if (motherName != null ? !motherName.equals(that.motherName) : that.motherName != null) return false;
        if (idActionType != null ? !idActionType.equals(that.idActionType) : that.idActionType != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (hStatus != null ? !hStatus.equals(that.hStatus) : that.hStatus != null) return false;
        if (idDwEmployee != null ? !idDwEmployee.equals(that.idDwEmployee) : that.idDwEmployee != null) return false;
        if (idDistributor != null ? !idDistributor.equals(that.idDistributor) : that.idDistributor != null)
            return false;
        if (idRegion != null ? !idRegion.equals(that.idRegion) : that.idRegion != null) return false;
        if (idBranch != null ? !idBranch.equals(that.idBranch) : that.idBranch != null) return false;
        return idArea != null ? idArea.equals(that.idArea) : that.idArea == null;

    }

    @Override
    public int hashCode() {
        int result = idEmployeeHistory != null ? idEmployeeHistory.hashCode() : 0;
        result = 31 * result + idEmployee;
        result = 31 * result + idDwEnterprise;
        result = 31 * result + idAccount;
        result = 31 * result + idRole;
        result = 31 * result + (employeeNumber != null ? employeeNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (parentalLast != null ? parentalLast.hashCode() : 0);
        result = 31 * result + (motherLast != null ? motherLast.hashCode() : 0);
        result = 31 * result + (rfc != null ? rfc.hashCode() : 0);
        result = 31 * result + (claveSap != null ? claveSap.hashCode() : 0);
        result = 31 * result + (curp != null ? curp.hashCode() : 0);
        result = 31 * result + (imss != null ? imss.hashCode() : 0);
        result = 31 * result + (infonavitNumber != null ? infonavitNumber.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (idEmployeeType != null ? idEmployeeType.hashCode() : 0);
        result = 31 * result + (idContractType != null ? idContractType.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (joinDate != null ? joinDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (idEducation != null ? idEducation.hashCode() : 0);
        result = 31 * result + (idGender != null ? idGender.hashCode() : 0);
        result = 31 * result + (idStatusMarital != null ? idStatusMarital.hashCode() : 0);
        result = 31 * result + (birthplace != null ? birthplace.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (exteriorNumber != null ? exteriorNumber.hashCode() : 0);
        result = 31 * result + (interiorNumber != null ? interiorNumber.hashCode() : 0);
        result = 31 * result + (colonia != null ? colonia.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (cellPhone != null ? cellPhone.hashCode() : 0);
        result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
        result = 31 * result + (idSize != null ? idSize.hashCode() : 0);
        result = 31 * result + (sizeNumber != null ? sizeNumber.hashCode() : 0);
        result = 31 * result + (fatherName != null ? fatherName.hashCode() : 0);
        result = 31 * result + (motherName != null ? motherName.hashCode() : 0);
        result = 31 * result + (idActionType != null ? idActionType.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (hStatus != null ? hStatus.hashCode() : 0);
        result = 31 * result + (idDwEmployee != null ? idDwEmployee.hashCode() : 0);
        result = 31 * result + (idDistributor != null ? idDistributor.hashCode() : 0);
        result = 31 * result + (idRegion != null ? idRegion.hashCode() : 0);
        result = 31 * result + (idBranch != null ? idBranch.hashCode() : 0);
        result = 31 * result + (idArea != null ? idArea.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeesHistory{" +
                "idEmployeeHistory=" + idEmployeeHistory +
                ", idEmployee=" + idEmployee +
                ", idDwEnterprise=" + idDwEnterprise +
                ", idAccount=" + idAccount +
                ", idRole=" + idRole +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", parentalLast='" + parentalLast + '\'' +
                ", motherLast='" + motherLast + '\'' +
                ", rfc='" + rfc + '\'' +
                ", claveSap='" + claveSap + '\'' +
                ", curp='" + curp + '\'' +
                ", imss='" + imss + '\'' +
                ", infonavitNumber='" + infonavitNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", idEmployeeType=" + idEmployeeType +
                ", idContractType=" + idContractType +
                ", salary=" + salary +
                ", joinDate=" + joinDate +
                ", status=" + status +
                ", idEducation=" + idEducation +
                ", idGender=" + idGender +
                ", idStatusMarital=" + idStatusMarital +
                ", birthplace='" + birthplace + '\'' +
                ", birthday=" + birthday +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", exteriorNumber='" + exteriorNumber + '\'' +
                ", interiorNumber='" + interiorNumber + '\'' +
                ", colonia='" + colonia + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", idSize=" + idSize +
                ", sizeNumber=" + sizeNumber +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", idActionType=" + idActionType +
                ", creationDate=" + creationDate +
                ", hStatus=" + hStatus +
                ", idDwEmployee=" + idDwEmployee +
                ", idDistributor=" + idDistributor +
                ", idRegion=" + idRegion +
                ", idBranch=" + idBranch +
                ", idArea=" + idArea +
                '}';
    }
}
