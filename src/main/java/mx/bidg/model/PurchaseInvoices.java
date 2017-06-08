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

    @Column(name = "ID_REQUEST_ORDER_DOCUMENT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestOrderDocument;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

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

    @Basic
    @NotNull
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Size(min = 1, max = 150)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
    private String filePath;

    @Size(min = 1, max = 100)
    @Column(name = "FILE_NAME")
    @JsonView(JsonViews.Root.class)
    private String fileName;

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

    @JoinColumn(name = "ID_REQUEST_ORDER_DOCUMENT", referencedColumnName = "ID_REQUEST_ORDER_DOCUMENT")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private  RequestOrderDocuments requestOrderDocument;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private  CCurrencies currency;

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

    public Integer getIdRequestOrderDocument() {
        return idRequestOrderDocument;
    }

    public void setIdRequestOrderDocument(Integer idRequestOrderDocument) {
        this.idRequestOrderDocument = idRequestOrderDocument;
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

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public RequestOrderDocuments getRequestOrderDocument() {
        return requestOrderDocument;
    }

    public void setRequestOrderDocument(RequestOrderDocuments requestOrderDocument) {
        this.requestOrderDocument = requestOrderDocument;
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

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseInvoices)) return false;

        PurchaseInvoices that = (PurchaseInvoices) o;

        if (idPurchaseInvoices != null ? !idPurchaseInvoices.equals(that.idPurchaseInvoices) : that.idPurchaseInvoices != null)
            return false;
        if (idProvider != null ? !idProvider.equals(that.idProvider) : that.idProvider != null) return false;
        return idRequestOrderDocument != null ? idRequestOrderDocument.equals(that.idRequestOrderDocument) : that.idRequestOrderDocument == null;
    }

    @Override
    public int hashCode() {
        int result = idPurchaseInvoices != null ? idPurchaseInvoices.hashCode() : 0;
        result = 31 * result + (idProvider != null ? idProvider.hashCode() : 0);
        result = 31 * result + (idRequestOrderDocument != null ? idRequestOrderDocument.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseInvoices{" +
                "idPurchaseInvoices=" + idPurchaseInvoices +
                ", idProvider=" + idProvider +
                ", idRequestOrderDocument=" + idRequestOrderDocument +
                ", conceptName='" + conceptName + '\'' +
                ", amount=" + amount +
                ", amountWithIva=" + amountWithIva +
                ", totalAmount=" + totalAmount +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", provider=" + provider +
                ", requestOrderDocument=" + requestOrderDocument +
                '}';
    }
}
