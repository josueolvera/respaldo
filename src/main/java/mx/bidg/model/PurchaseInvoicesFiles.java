package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by desarrollador on 14/06/17.
 */
@Entity
@DynamicUpdate
@Table(name = "PURCHASE_INVOICES_FILES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PurchaseInvoicesFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PURCHASE_INVOICES_FILES")
    @JsonView(JsonViews.Root.class)
    private Integer idPurchaseInvoicesFiles;

    @Column(name = "ID_PURCHASE_INVOICES", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idPurchaseInvoices;

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

    @JoinColumn(name = "ID_PURCHASE_INVOICES", referencedColumnName = "ID_PURCHASE_INVOICES")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private PurchaseInvoices purchaseInvoices;

    public PurchaseInvoicesFiles() {
    }

    public PurchaseInvoicesFiles(Integer idPurchaseInvoices) {
        this.idPurchaseInvoices = idPurchaseInvoices;
    }

    public Integer getIdPurchaseInvoicesFiles() {
        return idPurchaseInvoicesFiles;
    }

    public void setIdPurchaseInvoicesFiles(Integer idPurchaseInvoicesFiles) {
        this.idPurchaseInvoicesFiles = idPurchaseInvoicesFiles;
    }

    public Integer getIdPurchaseInvoices() {
        return idPurchaseInvoices;
    }

    public void setIdPurchaseInvoices(Integer idPurchaseInvoices) {
        this.idPurchaseInvoices = idPurchaseInvoices;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public PurchaseInvoices getPurchaseInvoices() {
        return purchaseInvoices;
    }

    public void setPurchaseInvoices(PurchaseInvoices purchaseInvoices) {
        this.purchaseInvoices = purchaseInvoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseInvoicesFiles)) return false;

        PurchaseInvoicesFiles that = (PurchaseInvoicesFiles) o;

        if (idPurchaseInvoicesFiles != null ? !idPurchaseInvoicesFiles.equals(that.idPurchaseInvoicesFiles) : that.idPurchaseInvoicesFiles != null)
            return false;
        return idPurchaseInvoices != null ? idPurchaseInvoices.equals(that.idPurchaseInvoices) : that.idPurchaseInvoices == null;
    }

    @Override
    public int hashCode() {
        int result = idPurchaseInvoicesFiles != null ? idPurchaseInvoicesFiles.hashCode() : 0;
        result = 31 * result + (idPurchaseInvoices != null ? idPurchaseInvoices.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseInvoicesFiles{" +
                "idPurchaseInvoicesFiles=" + idPurchaseInvoicesFiles +
                ", idPurchaseInvoices=" + idPurchaseInvoices +
                ", username='" + username + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", creationDate=" + creationDate +
                ", purchaseInvoices=" + purchaseInvoices +
                '}';
    }
}
