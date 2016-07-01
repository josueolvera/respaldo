/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.StringFormatter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "EMPLOYEES")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Employees implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;
    
    @Size(max = 10)
    @Column(name = "EMPLOYEE_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String employeeNumber;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Size(max = 50)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    
    @Size(max = 40)
    @Column(name = "PARENTAL_LAST")
    private String parentalLast;
    
    @Size(max = 40)
    @Column(name = "MOTHER_LAST")
    private String motherLast;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RFC")
    private String rfc;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CLAVE_SAP")
    private String claveSap;
    
    @Size(max = 18)
    @Column(name = "CURP")
    private String curp;
    
    @Size(max = 18)
    @Column(name = "IMSS")
    private String imss;
    
    @Size(max = 15)
    @Column(name = "INFONAVIT_NUMBER")
    private String infonavitNumber;
    
    @Size(max = 50)
    @Column(name = "MAIL")
    private String mail;
    
    @Column(name = "EMPLOYEE_TYPE")
    private Integer employeeType;
    
    @Column(name = "CONTRACT_TYPE")
    private Integer contractType;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARY")
    private BigDecimal salary;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOIN_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime joinDate;
    
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    @Size(max = 50)
    @Column(name = "BIRTH_PLACE")
    @JsonView(JsonViews.Root.class)
    private String birthPlace;
    
    @Column(name = "BIRTHDAY")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;
    
    @Size(max = 50)
    @Column(name = "STATE")
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
    
    @Size(max = 150)
    @Column(name = "FATHER_NAME")
    @JsonView(JsonViews.Root.class)
    private String fatherName;
    
    @Size(max = 150)
    @Column(name = "MOTHER_NAME")
    @JsonView(JsonViews.Root.class)
    private String motherName;

    @Column(name = "ID_EDUCATION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEducation;

    @Column(name = "ID_STATUS_MARITAL", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idStatusMarital;

    @Size(max = 10)
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

    @Column(name = "GENDER")
    @JsonView(JsonViews.Root.class)
    private Integer gender;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonView(JsonViews.Embedded.class)
    private List<EmployeesAccounts> employeesAccountsList;

    @JoinColumn(name = "ID_EDUCATION", referencedColumnName = "ID_EDUCATION")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private CEducation education;

    @JoinColumn(name = "ID_STATUS_MARITAL", referencedColumnName = "ID_STATUS_MARITAL")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private CStatusMarital statusMarital;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonView(JsonViews.Embedded.class)
    private List<EmployeeDocuments> employeeDocumentsList;


    public Employees() {
    }

    public Employees(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Employees(Integer idEmployee, String firstName, String rfc, String claveSap, int status) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.rfc = rfc;
        this.claveSap = claveSap;
        this.status = status;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
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

    public void setJoinDate(LocalDateTime highDate) {
        this.joinDate = highDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthplace) {
        this.birthPlace = birthplace;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthdate) {
        this.birthday = birthdate;
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

    public void setCity(String delegationMunicipality) {
        this.city = delegationMunicipality;
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

    public List<EmployeesAccounts> getEmployeesAccountsList() {
        return employeesAccountsList;
    }

    public void setEmployeesAccountsList(List<EmployeesAccounts> employeesAccountsList) {
        this.employeesAccountsList = employeesAccountsList;
    }

    public DateFormatsPojo getJoinDateFormats() {
        if (joinDate == null) {
            return null;
        }
        return new DateFormatsPojo(joinDate);
    }

    public DateFormatsPojo getBirthDayFormats() {
        if (birthday == null) {
            return null;
        }
        return new DateFormatsPojo(birthday);
    }

    public Integer getIdEducation() {
        return idEducation;
    }

    public void setIdEducation(Integer idEducation) {
        this.idEducation = idEducation;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public Integer getSizeNumber() {
        return sizeNumber;
    }

    public void setSizeNumber(Integer sizeNumber) {
        this.sizeNumber = sizeNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public CEducation getEducation() {
        return education;
    }

    public void setEducation(CEducation education) {
        this.education = education;
    }

    public Integer getIdStatusMarital() {
        return idStatusMarital;
    }

    public void setIdStatusMarital(Integer idStatusMarital) {
        this.idStatusMarital = idStatusMarital;
    }

    public CStatusMarital getStatusMarital() {
        return statusMarital;
    }

    public void setStatusMarital(CStatusMarital statusMarital) {
        this.statusMarital = statusMarital;
    }

    public List<EmployeeDocuments> getEmployeeDocumentsList() {
        return employeeDocumentsList;
    }

    public void setEmployeeDocumentsList(List<EmployeeDocuments> employeeDocumentsList) {
        this.employeeDocumentsList = employeeDocumentsList;
    }

    public String getFullName() {
        return StringFormatter.concatWithoutNull(firstName, middleName, parentalLast, motherLast);
    }

    public String getFullNameReverse() {
        return StringFormatter.concatWithoutNull(parentalLast, motherLast, firstName, middleName);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmployee != null ? idEmployee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.idEmployee == null && other.idEmployee != null) || (this.idEmployee != null && !this.idEmployee.equals(other.idEmployee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Employees[ idEmployee=" + idEmployee + " ]";
    }
}
