package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Entity
@DynamicUpdate
@Table(name = "SAP_SALES")
public class SapSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SAP_SALE")
    @JsonView(JsonViews.Root.class)
    private Integer idSapSale;

    @Basic(optional = false)
    @Column(name = "ID_SALE")
    @JsonView(JsonViews.Root.class)
    private String idSale;

    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @Column(name = "INTERLOC_COM")
    private String interlocCom;

    @Column(name = "CLIENT_PARENT_LAST")
    @JsonView(JsonViews.Root.class)
    private String clientParentLast;

    @Column(name = "CLIENT_MOTHER_LAST")
    @JsonView(JsonViews.Root.class)
    private String clientMotherLast;

    @Column(name = "CLIENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String clientName;

    @Column(name = "CLIENT_SEC_NAME")
    @JsonView(JsonViews.Root.class)
    private String clientSecName;

    @Column(name = "CLIENT_SINGLE_LAST")
    @JsonView(JsonViews.Root.class)
    private String clientSingleLast;

    @Column(name = "CLIENT_ID")
    @JsonView(JsonViews.Root.class)
    private String clientId;

    @Column(name = "IMSS_NUM")
    @JsonView(JsonViews.Root.class)
    private String imssNum;

    @Column(name = "PRODUCT")
    @JsonView(JsonViews.Root.class)
    private String product;

    @Column(name = "DEPENDENCY")
    @JsonView(JsonViews.Root.class)
    private String dependency;

    @Column(name = "STATUS_SALE")
    @JsonView(JsonViews.Root.class)
    private String statusSale;

    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date lastUpdate;

    @Column(name = "APPROVAL_DATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date approvalDate;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "REQUESTED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal requestedAmount;

    @Column(name = "PAYMENTS")
    @JsonView(JsonViews.Root.class)
    private String payments;

    @Column(name = "DEPOSIT_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal depositAmount;

    @Column(name = "COMISSIONABLE_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal comissionableAmount;

    @Column(name = "PURCHASE_DATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date purchaseDate;

    @Column(name = "COMPANY_NAME")
    @JsonView(JsonViews.Root.class)
    private String companyName;

    @Column(name = "CLAVE_SAP")
    @JsonView(JsonViews.Root.class)
    private String claveSap;

    @Column(name = "BONIFICATION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal bonification;

    @Column(name = "LIQUIDATION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal liquidation;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    @Column(name = "ID_DW_ENTERPRISE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDwEnterprise;

    @Column(name = "ID_AGREEMENT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAgreement;

    @Column(name = "ID_BRANCH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;

    @Column(name = "ID_REGION", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRegion;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "ID_ZONAS", insertable = false, updatable = false, nullable = true)
    @JsonView(JsonViews.Root.class)
    private Integer idZonas;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne()
    private DwEnterprises dwEnterprise;

    @JoinColumn(name = "ID_AGREEMENT", referencedColumnName = "ID_AGREEMENT")
    @ManyToOne()
    private CAgreements agreement;

    @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH")
    @ManyToOne()
    private CBranchs branch;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne()
    private CDistributors distributor;

    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")
    @ManyToOne()
    private CRegions region;

    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    @ManyToOne()
    private Employees employee;

    @JoinColumn(name = "ID_ZONAS", referencedColumnName = "ID_ZONAS")
    @ManyToOne(optional = true)
    private CZonas zona;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne(optional = true)
    private CRoles role;

    public SapSale() {
    }

    public SapSale(Integer idSapSale) {
        this.idSapSale = idSapSale;
    }

    public SapSale(Integer idSapSale, String idSale) {
        this.idSapSale = idSapSale;
        this.idSale = idSale;
    }

    public Integer getIdSapSale() {
        return idSapSale;
    }

    public void setIdSapSale(Integer idSapSale) {
        this.idSapSale = idSapSale;
    }

    public String getIdSale() {
        return idSale;
    }

    public void setIdSale(String idSale) {
        this.idSale = idSale;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getInterlocCom() {
        return interlocCom;
    }

    public void setInterlocCom(String interlocCom) {
        this.interlocCom = interlocCom;
    }

    public String getClientParentLast() {
        return clientParentLast;
    }

    public void setClientParentLast(String clientParentLast) {
        this.clientParentLast = clientParentLast;
    }

    public String getClientMotherLast() {
        return clientMotherLast;
    }

    public void setClientMotherLast(String clientMotherLast) {
        this.clientMotherLast = clientMotherLast;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecName() {
        return clientSecName;
    }

    public void setClientSecName(String clientSecName) {
        this.clientSecName = clientSecName;
    }

    public String getClientSingleLast() {
        return clientSingleLast;
    }

    public void setClientSingleLast(String clientSingleLast) {
        this.clientSingleLast = clientSingleLast;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getImssNum() {
        return imssNum;
    }

    public void setImssNum(String imssNum) {
        this.imssNum = imssNum;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getStatusSale() {
        return statusSale;
    }

    public void setStatusSale(String statusSale) {
        this.statusSale = statusSale;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getComissionableAmount() {
        return comissionableAmount;
    }

    public void setComissionableAmount(BigDecimal comissionableAmount) {
        this.comissionableAmount = comissionableAmount;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public BigDecimal getBonification() {
        return bonification;
    }

    public void setBonification(BigDecimal bonification) {
        this.bonification = bonification;
    }

    public BigDecimal getLiquidation() {
        return liquidation;
    }

    public void setLiquidation(BigDecimal liquidation) {
        this.liquidation = liquidation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DwEnterprises getDwEnterprise() {
        return dwEnterprise;
    }

    public void setDwEnterprise(DwEnterprises dwEnterprise) {
        this.dwEnterprise = dwEnterprise;
    }

    public CAgreements getAgreement() {
        return agreement;
    }

    public void setAgreement(CAgreements agreement) {
        this.agreement = agreement;
    }

    public CBranchs getBranch() {
        return branch;
    }

    public void setBranch(CBranchs branch) {
        this.branch = branch;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    public CRegions getRegion() {
        return region;
    }

    public void setRegion(CRegions region) {
        this.region = region;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Integer getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(Integer idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }

    public Integer getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(Integer idAgreement) {
        this.idAgreement = idAgreement;
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

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getIdZonas() {
        return idZonas;
    }

    public void setIdZonas(Integer idZonas) {
        this.idZonas = idZonas;
    }

    public CZonas getZona() {
        return zona;
    }

    public void setZona(CZonas zona) {
        this.zona = zona;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public CRoles getRole() {
        return role;
    }

    public void setRole(CRoles role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSapSale != null ? idSapSale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SapSale)) {
            return false;
        }
        SapSale other = (SapSale) object;
        if ((this.idSapSale == null && other.idSapSale != null) || (this.idSapSale != null && !this.idSapSale.equals(other.idSapSale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SapSale{" +
                "idSapSale=" + idSapSale +
                ", idSale='" + idSale + '\'' +
                ", creationDate=" + creationDate +
                ", interlocCom='" + interlocCom + '\'' +
                ", clientParentLast='" + clientParentLast + '\'' +
                ", clientMotherLast='" + clientMotherLast + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientSecName='" + clientSecName + '\'' +
                ", clientSingleLast='" + clientSingleLast + '\'' +
                ", clientId='" + clientId + '\'' +
                ", imssNum='" + imssNum + '\'' +
                ", product='" + product + '\'' +
                ", dependency='" + dependency + '\'' +
                ", statusSale='" + statusSale + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", approvalDate=" + approvalDate +
                ", requestedAmount=" + requestedAmount +
                ", payments='" + payments + '\'' +
                ", depositAmount=" + depositAmount +
                ", comissionableAmount=" + comissionableAmount +
                ", purchaseDate=" + purchaseDate +
                ", companyName='" + companyName + '\'' +
                ", bonification=" + bonification +
                ", liquidation=" + liquidation +
                ", status=" + status +
                ", dwEnterprise=" + dwEnterprise +
                ", agreement=" + agreement +
                ", branch=" + branch +
                ", distributor=" + distributor +
                ", region=" + region +
                ", employee=" + employee +
                '}';
    }
}

