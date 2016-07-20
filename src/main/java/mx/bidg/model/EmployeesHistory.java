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
    @Column(name = "ID_EH")
    @JsonView(JsonViews.Root.class)
    private Integer idEh;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private int idEmployee;

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

    @Column(name = "EMPLOYEE_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer employeeType;

    @Column(name = "CONTRACT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer contractType;

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

    @Column(name = "GENDER")
    @JsonView(JsonViews.Root.class)
    private Integer gender;

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

    @Column(name = "ID_ACTION_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idActionType;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public EmployeesHistory() {
    }

    public EmployeesHistory(Integer idEh) {
        this.idEh = idEh;
    }

    public EmployeesHistory(Integer idEh, Integer idEmployee, String rfc, LocalDateTime creationDate, Integer status) {
        this.idEh = idEh;
        this.idEmployee = idEmployee;
        this.rfc = rfc;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Integer getIdEh() {
        return idEh;
    }

    public void setIdEh(Integer idEh) {
        this.idEh = idEh;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIdStatusMarital() {
        return idStatusMarital;
    }

    public void setIdStatusMarital(Integer idStatusMarital) {
        this.idStatusMarital = idStatusMarital;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
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

    public DateFormatsPojo getBirthDayFormats() {
        if (birthday == null) {
            return null;
        }
        return new DateFormatsPojo(birthday);
    }

    public DateFormatsPojo getCreationDateFormats() {
        if (creationDate == null) {
            return null;
        }
        return new DateFormatsPojo(creationDate);
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
