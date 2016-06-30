/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.StringFormatter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @Basic(optional = false)
    @Column(name = "ID_EH")
    @JsonView(JsonViews.Root.class)
    private Integer idEh;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private int idEmployee;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DISTRIBUTOR")
    @JsonView(JsonViews.Root.class)
    private int idDistributor;
    
    @Size(max = 25)
    @Column(name = "DISTRIBUTOR_NAME")
    @JsonView(JsonViews.Root.class)
    private String distributorName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_AREA")
    @JsonView(JsonViews.Root.class)
    private int idArea;

    @Size(max = 25)
    @Column(name = "AREA_NAME")
    @JsonView(JsonViews.Root.class)
    private String areaName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REGION")
    @JsonView(JsonViews.Root.class)
    private int idRegion;
    
    @Size(max = 30)
    @Column(name = "REGION_NAME")
    @JsonView(JsonViews.Root.class)
    private String regionName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Root.class)
    private int idBranch;
    
    @Size(max = 30)
    @Column(name = "BRANCH_SHORT")
    @JsonView(JsonViews.Root.class)
    private String branchShort;
    
    @Size(max = 50)
    @Column(name = "SAP_BRANCH")
    @JsonView(JsonViews.Root.class)
    private String sapBranch;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GROUP")
    @JsonView(JsonViews.Root.class)
    private int idGroup;
    
    @Size(max = 50)
    @Column(name = "GROUP_NAME")
    @JsonView(JsonViews.Root.class)
    private String groupName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Root.class)
    private int idRole;
    
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
    
    @Basic(optional = false)
    @NotNull
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
    
    @Size(max = 30)
    @Column(name = "EMPLOYEE_TYPE")
    @JsonView(JsonViews.Root.class)
    private String employeeType;
    
    @Column(name = "CONTRACT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer contractType;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARY")
    @JsonView(JsonViews.Root.class)
    private BigDecimal salary;
    
    @Column(name = "MOVEMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer movementType;
    
    @Column(name = "JOIN_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime joinDate;
    
    @Size(max = 30)
    @Column(name = "EDUCATION")
    @JsonView(JsonViews.Root.class)
    private String education;
    
    @Column(name = "SCORE_SISTARTH")
    @JsonView(JsonViews.Root.class)
    private Integer scoreSistarth;
    
    @Column(name = "GENDER")
    @JsonView(JsonViews.Root.class)
    private Integer gender;
    
    @Size(max = 15)
    @Column(name = "STATUS_MARITAL")
    @JsonView(JsonViews.Root.class)
    private String statusMarital;
    
    @Size(max = 50)
    @Column(name = "BIRTHPLACE")
    @JsonView(JsonViews.Root.class)
    private String birthplace;
    
    @Column(name = "BIRTHDATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime birthdate;
    
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
    
    @Size(max = 100)
    @Column(name = "IFE")
    @JsonView(JsonViews.Root.class)
    private String ife;
    
    @Size(max = 100)
    @Column(name = "BIRTH_CERTIFICATE")
    @JsonView(JsonViews.Root.class)
    private String birthCertificate;
    
    @Size(max = 100)
    @Column(name = "CURP_D")
    @JsonView(JsonViews.Root.class)
    private String curpD;
    
    @Size(max = 100)
    @Column(name = "RFC_D")
    @JsonView(JsonViews.Root.class)
    private String rfcD;
    
    @Size(max = 100)
    @Column(name = "PROOF_ADDRESS")
    @JsonView(JsonViews.Root.class)
    private String proofAddress;
    
    @Size(max = 100)
    @Column(name = "ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private String account;
    
    @Size(max = 100)
    @Column(name = "IMSS_D")
    @JsonView(JsonViews.Root.class)
    private String imssD;
    
    @Size(max = 100)
    @Column(name = "INFONAVIT")
    @JsonView(JsonViews.Root.class)
    private String infonavit;
    
    @Size(max = 100)
    @Column(name = "PHOTO")
    @JsonView(JsonViews.Root.class)
    private String photo;
    
    @Size(max = 45)
    @Column(name = "ACCOUNT_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String accountNumber;
    
    @Size(max = 45)
    @Column(name = "ACCOUNT_CLABE")
    @JsonView(JsonViews.Root.class)
    private String accountClabe;
    
    @Size(max = 45)
    @Column(name = "ACCOUNT_TYPE")
    @JsonView(JsonViews.Root.class)
    private String accountType;
    
    @Size(max = 45)
    @Column(name = "ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String acronyms;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ACTION_TYPE")
    @JsonView(JsonViews.Root.class)
    private String actionType;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "H_STATUS")
    @JsonView(JsonViews.Root.class)
    private boolean hStatus;

    public EmployeesHistory() {
    }

    public EmployeesHistory(Integer idEh) {
        this.idEh = idEh;
    }

    public EmployeesHistory(Integer idEh, int idEmployee, int idDistributor, int idRegion, int idBranch, int idGroup, int idRole, String rfc, String username, LocalDateTime creationDate, String actionType, boolean hStatus) {
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

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
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

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(int idBranch) {
        this.idBranch = idBranch;
    }

    public String getBranchShort() {
        return branchShort;
    }

    public void setBranchShort(String branchShort) {
        this.branchShort = branchShort;
    }

    public String getSapBranch() {
        return sapBranch;
    }

    public void setSapBranch(String sapBranch) {
        this.sapBranch = sapBranch;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
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

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
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

    public Integer getMovementType() {
        return movementType;
    }

    public void setMovementType(Integer movementType) {
        this.movementType = movementType;
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

    public Integer getScoreSistarth() {
        return scoreSistarth;
    }

    public void setScoreSistarth(Integer scoreSistarth) {
        this.scoreSistarth = scoreSistarth;
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

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
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

    public String getIfe() {
        return ife;
    }

    public void setIfe(String ife) {
        this.ife = ife;
    }

    public String getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public String getCurpD() {
        return curpD;
    }

    public void setCurpD(String curpD) {
        this.curpD = curpD;
    }

    public String getRfcD() {
        return rfcD;
    }

    public void setRfcD(String rfcD) {
        this.rfcD = rfcD;
    }

    public String getProofAddress() {
        return proofAddress;
    }

    public void setProofAddress(String proofAddress) {
        this.proofAddress = proofAddress;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImssD() {
        return imssD;
    }

    public void setImssD(String imssD) {
        this.imssD = imssD;
    }

    public String getInfonavit() {
        return infonavit;
    }

    public void setInfonavit(String infonavit) {
        this.infonavit = infonavit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public boolean getHStatus() {
        return hStatus;
    }

    public void setHStatus(boolean hStatus) {
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
