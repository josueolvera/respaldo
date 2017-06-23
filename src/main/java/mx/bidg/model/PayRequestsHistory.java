package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by User on 21/06/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "PAY_REQUESTS_HISTORY")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PayRequestsHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAY_REQUESTS_HISTORY")
    @JsonView(JsonViews.Root.class)
    private Integer idPayRequestsHistory;

    @Column(name = "ID_REQUEST", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_COST_CENTER", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;

    @Column(name = "ID_REQUEST_CATEGORY", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestCategory;

    @Column(name = "ID_PROVIDER", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @Column(name = "ID_ACCOUNT", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idAccount;

    @Column(name = "ID_PURCHASE_INVOICES", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idPurchaseInvoices;

    @Column(name = "ID_REQUESTS_DATES", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestsDates;

    @Column(name = "ID_DISTRIBUTOR_DETAIL_BANK", nullable=true)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorDetailBank;

    @Size(max = 50)
    @Column(name = "FOLIO", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Size(max = 100)
    @Column(name = "COST_CENTER", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String costCenter;

    @Size(max = 50)
    @Column(name = "REQUEST_CATEGORY", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String requestCategory;

    @Size(max = 100)
    @Column(name = "PROVIDER", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String provider;

    @Size(max = 100)
    @Column(name = "BANK_PROVIDER", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String bankProvider;

    @Size(max = 100)
    @Column(name = "ACCOUNT_NUMBER", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String accountNumber;

    @Size(max = 100)
    @Column(name = "ACCOUNT_CLABE", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String accountClabe;

    @Size(max = 100)
    @Column(name = "PURCHASE_INVOICE_FOLIO", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String purchaseInvoiceFolio;

    @Column(name = "AMOUNT_WITH_IVA", nullable=true)
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountWithIva;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUEST_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime requestDate;

    @Size(max = 100)
    @Column(name = "BANK_DISTRIBUTOR", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String bankDistributor;

    @Column(name = "USERNAME", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE", nullable=true)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    public PayRequestsHistory(){}

    public PayRequestsHistory(Integer idPayRequestsHistory) {
        this.idPayRequestsHistory = idPayRequestsHistory;
    }

    public PayRequestsHistory(Integer idRequest, Integer idCostCenter, Integer idRequestCategory, Integer idDistributor, Integer idAccount, Integer idPurchaseInvoices, Integer idRequestsDates, Integer idDistributorDetailBank, Integer idDistributor1, String folio, String mail, String costCenter, String requestCategory, String provider, String bankProvider, String accountNumber, String accountClabe, String purchaseInvoiceFolio, BigDecimal amountWithIva, LocalDateTime requestDate, String bankDistributor, String username, LocalDateTime creationDate) {
        this.idRequest = idRequest;
        this.idCostCenter = idCostCenter;
        this.idRequestCategory = idRequestCategory;
        this.idProvider = idProvider;
        this.idAccount = idAccount;
        this.idPurchaseInvoices = idPurchaseInvoices;
        this.idRequestsDates = idRequestsDates;
        this.idDistributorDetailBank = idDistributorDetailBank;
        this.folio = folio;
        this.costCenter = costCenter;
        this.requestCategory = requestCategory;
        this.provider = provider;
        this.bankProvider = bankProvider;
        this.accountNumber = accountNumber;
        this.accountClabe = accountClabe;
        this.purchaseInvoiceFolio = purchaseInvoiceFolio;
        this.amountWithIva = amountWithIva;
        this.requestDate = requestDate;
        this.bankDistributor = bankDistributor;
        this.username = username;
        this.creationDate = creationDate;
    }

    public Integer getIdPayRequestsHistory() {
        return idPayRequestsHistory;
    }

    public void setIdPayRequestsHistory(Integer idPayRequestsHistory) {
        this.idPayRequestsHistory = idPayRequestsHistory;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdRequestCategory() {
        return idRequestCategory;
    }

    public void setIdRequestCategory(Integer idRequestCategory) {
        this.idRequestCategory = idRequestCategory;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public Integer getIdPurchaseInvoices() {
        return idPurchaseInvoices;
    }

    public void setIdPurchaseInvoices(Integer idPurchaseInvoices) {
        this.idPurchaseInvoices = idPurchaseInvoices;
    }

    public Integer getIdRequestsDates() {
        return idRequestsDates;
    }

    public void setIdRequestsDates(Integer idRequestsDates) {
        this.idRequestsDates = idRequestsDates;
    }

    public Integer getIdDistributorDetailBank() {
        return idDistributorDetailBank;
    }

    public void setIdDistributorDetailBank(Integer idDistributorDetailBank) {
        this.idDistributorDetailBank = idDistributorDetailBank;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(String requestCategory) {
        this.requestCategory = requestCategory;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getBankProvider() {
        return bankProvider;
    }

    public void setBankProvider(String bankProvider) {
        this.bankProvider = bankProvider;
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

    public String getPurchaseInvoiceFolio() {
        return purchaseInvoiceFolio;
    }

    public void setPurchaseInvoiceFolio(String purchaseInvoiceFolio) {
        this.purchaseInvoiceFolio = purchaseInvoiceFolio;
    }

    public BigDecimal getAmountWithIva() {
        return amountWithIva;
    }

    public void setAmountWithIva(BigDecimal amountWithIva) {
        this.amountWithIva = amountWithIva;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getBankDistributor() {
        return bankDistributor;
    }

    public void setBankDistributor(String bankDistributor) {
        this.bankDistributor = bankDistributor;
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

    public DateFormatsPojo getJoinDateFormats() {
        if (requestDate == null) {
            return null;
        }
        return new DateFormatsPojo(requestDate);
    }

    public DateFormatsPojo getCrationDateDateFormats() {
        if (creationDate == null) {
            return null;
        }
        return new DateFormatsPojo(creationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayRequestsHistory that = (PayRequestsHistory) o;

        if (idRequest != null ? !idRequest.equals(that.idRequest) : that.idRequest != null) return false;
        if (idCostCenter != null ? !idCostCenter.equals(that.idCostCenter) : that.idCostCenter != null) return false;
        if (idRequestCategory != null ? !idRequestCategory.equals(that.idRequestCategory) : that.idRequestCategory != null)
            return false;
        if (idProvider != null ? !idProvider.equals(that.idProvider) : that.idProvider != null)
            return false;
        if (idAccount != null ? !idAccount.equals(that.idAccount) : that.idAccount != null) return false;
        if (idPurchaseInvoices != null ? !idPurchaseInvoices.equals(that.idPurchaseInvoices) : that.idPurchaseInvoices != null)
            return false;
        if (idRequestsDates != null ? !idRequestsDates.equals(that.idRequestsDates) : that.idRequestsDates != null)
            return false;
        if (idDistributorDetailBank != null ? !idDistributorDetailBank.equals(that.idDistributorDetailBank) : that.idDistributorDetailBank != null)
            return false;
        if (folio != null ? !folio.equals(that.folio) : that.folio != null) return false;
        if (costCenter != null ? !costCenter.equals(that.costCenter) : that.costCenter != null) return false;
        if (requestCategory != null ? !requestCategory.equals(that.requestCategory) : that.requestCategory != null)
            return false;
        if (provider != null ? !provider.equals(that.provider) : that.provider != null) return false;
        if (bankProvider != null ? !bankProvider.equals(that.bankProvider) : that.bankProvider != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (accountClabe != null ? !accountClabe.equals(that.accountClabe) : that.accountClabe != null) return false;
        if (purchaseInvoiceFolio != null ? !purchaseInvoiceFolio.equals(that.purchaseInvoiceFolio) : that.purchaseInvoiceFolio != null)
            return false;
        if (amountWithIva != null ? !amountWithIva.equals(that.amountWithIva) : that.amountWithIva != null)
            return false;
        if (requestDate != null ? !requestDate.equals(that.requestDate) : that.requestDate != null) return false;
        if (bankDistributor != null ? !bankDistributor.equals(that.bankDistributor) : that.bankDistributor != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return creationDate != null ? creationDate.equals(that.creationDate) : that.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = idRequest != null ? idRequest.hashCode() : 0;
        result = 31 * result + (idCostCenter != null ? idCostCenter.hashCode() : 0);
        result = 31 * result + (idRequestCategory != null ? idRequestCategory.hashCode() : 0);
        result = 31 * result + (idProvider != null ? idProvider.hashCode() : 0);
        result = 31 * result + (idAccount != null ? idAccount.hashCode() : 0);
        result = 31 * result + (idPurchaseInvoices != null ? idPurchaseInvoices.hashCode() : 0);
        result = 31 * result + (idRequestsDates != null ? idRequestsDates.hashCode() : 0);
        result = 31 * result + (idDistributorDetailBank != null ? idDistributorDetailBank.hashCode() : 0);
        result = 31 * result + (folio != null ? folio.hashCode() : 0);
        result = 31 * result + (costCenter != null ? costCenter.hashCode() : 0);
        result = 31 * result + (requestCategory != null ? requestCategory.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (bankProvider != null ? bankProvider.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (accountClabe != null ? accountClabe.hashCode() : 0);
        result = 31 * result + (purchaseInvoiceFolio != null ? purchaseInvoiceFolio.hashCode() : 0);
        result = 31 * result + (amountWithIva != null ? amountWithIva.hashCode() : 0);
        result = 31 * result + (requestDate != null ? requestDate.hashCode() : 0);
        result = 31 * result + (bankDistributor != null ? bankDistributor.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayRequestsHistory{" +
                "idPayRequestsHistory=" + idPayRequestsHistory +
                ", idRequest=" + idRequest +
                ", idCostCenter=" + idCostCenter +
                ", idRequestCategory=" + idRequestCategory +
                ", idProvider=" + idProvider +
                ", idAccount=" + idAccount +
                ", idPurchaseInvoices=" + idPurchaseInvoices +
                ", idRequestsDates=" + idRequestsDates +
                ", idDistributorDetailBank=" + idDistributorDetailBank +
                ", folio='" + folio + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", requestCategory='" + requestCategory + '\'' +
                ", provider='" + provider + '\'' +
                ", bankProvider='" + bankProvider + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountClabe='" + accountClabe + '\'' +
                ", purchaseInvoiceFolio='" + purchaseInvoiceFolio + '\'' +
                ", amountWithIva=" + amountWithIva +
                ", requestDate=" + requestDate +
                ", bankDistributor='" + bankDistributor + '\'' +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
