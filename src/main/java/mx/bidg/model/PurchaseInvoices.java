package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */

@Entity
@DynamicUpdate
@Table(name = "PURCHASE_INVOICES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PurchaseInvoices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PURCHASE_INVOICES")
    @JsonView(JsonViews.Root.class)
    private Integer idPurchaseInvoices;

    @Column(name = "ID_PROVIDER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;

    @Column(name = "ID_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;

    @Column(name = "ID_ACCOUNT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccount;

    @Size(min = 1, max = 15)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Size(min = 1, max = 25)
    @Column(name = "CURRENCY_TYPE")
    @JsonView(JsonViews.Root.class)
    private String  currencyType;

    @Size(min = 1, max = 100)
    @Column(name = "CONCEPT_NAME")
    @JsonView(JsonViews.Root.class)
    private String conceptName;

    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Column(name = "AMOUNT_WITH_IVA")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountWithIva;

    @Column(name = "TOTAL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalAmount;

    @Column(name = "RATE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rate;

    @Basic
    @NotNull
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @JoinColumn(name = "ID_PROVIDER", referencedColumnName = "ID_PROVIDER")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private Providers provider;

    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private  Requests request;

    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private  Accounts account;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseInvoices")
    @JsonView(JsonViews.Embedded.class)
    private List<PurchaseInvoicesFiles> purchaseInvoicesFiles;

    @Transient
    @JsonView(JsonViews.Root.class)
    private LocalDateTime limitDay;

    public PurchaseInvoices() {
    }

    public PurchaseInvoices(Integer idPurchaseInvoices) {
        this.idPurchaseInvoices = idPurchaseInvoices;
    }

    public Integer getIdPurchaseInvoices() {
        return idPurchaseInvoices;
    }

    public void setIdPurchaseInvoices(Integer idPurchaseInvoices) {
        this.idPurchaseInvoices = idPurchaseInvoices;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountWithIva() {
        return amountWithIva;
    }

    public void setAmountWithIva(BigDecimal amountWithIva) {
        this.amountWithIva = amountWithIva;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public Requests getRequest() {
        return request;
    }

    public void setRequest(Requests request) {
        this.request = request;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public List<PurchaseInvoicesFiles> getPurchaseInvoicesFiles() {
        return purchaseInvoicesFiles;
    }

    public void setPurchaseInvoicesFiles(List<PurchaseInvoicesFiles> purchaseInvoicesFiles) {
        this.purchaseInvoicesFiles = purchaseInvoicesFiles;
    }

    public LocalDateTime getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(LocalDateTime limitDay) {
        this.limitDay = limitDay;
    }

    public DateFormatsPojo getPaydayLimitFormats() {
        if (limitDay == null) {
            return null;
        }
        return new DateFormatsPojo(limitDay);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseInvoices)) return false;

        PurchaseInvoices that = (PurchaseInvoices) o;

        if (idPurchaseInvoices != null ? !idPurchaseInvoices.equals(that.idPurchaseInvoices) : that.idPurchaseInvoices != null)
            return false;
        if (idProvider != null ? !idProvider.equals(that.idProvider) : that.idProvider != null) return false;
        if (idRequest != null ? !idRequest.equals(that.idRequest) : that.idRequest != null) return false;
        if (idAccount != null ? !idAccount.equals(that.idAccount) : that.idAccount != null) return false;
        return folio != null ? folio.equals(that.folio) : that.folio == null;
    }

    @Override
    public int hashCode() {
        int result = idPurchaseInvoices != null ? idPurchaseInvoices.hashCode() : 0;
        result = 31 * result + (idProvider != null ? idProvider.hashCode() : 0);
        result = 31 * result + (idRequest != null ? idRequest.hashCode() : 0);
        result = 31 * result + (idAccount != null ? idAccount.hashCode() : 0);
        result = 31 * result + (folio != null ? folio.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseInvoices{}";
    }
}
