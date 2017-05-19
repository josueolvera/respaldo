package mx.bidg.model.siad;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "PRICE_ESTIMATIONS", schema = "BIDG_DBA", catalog = "")
public class PriceEstimations {
    private int idPriceEstimation;
    private int idRequest;
    private int idAccount;
    private int idEstimationStatus;
    private int idCurrency;
    private BigDecimal amount;
    private String filePath;
    private String fileName;
    private Timestamp authorizationDate;
    private int idAccessLevel;
    private String username;
    private Timestamp creationDate;
    private Requests requestsByIdRequest;
    private CEstimationStatus cEstimationStatusByIdEstimationStatus;
    private Collection<RequestOrderDocuments> requestOrderDocumentsByIdPriceEstimation;

    @Id
    @Column(name = "ID_PRICE_ESTIMATION", nullable = false)
    public int getIdPriceEstimation() {
        return idPriceEstimation;
    }

    public void setIdPriceEstimation(int idPriceEstimation) {
        this.idPriceEstimation = idPriceEstimation;
    }

    @Basic
    @Column(name = "ID_REQUEST", nullable = false)
    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    @Basic
    @Column(name = "ID_ACCOUNT", nullable = false)
    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    @Basic
    @Column(name = "ID_ESTIMATION_STATUS", nullable = false)
    public int getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(int idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    @Basic
    @Column(name = "ID_CURRENCY", nullable = false)
    public int getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(int idCurrency) {
        this.idCurrency = idCurrency;
    }

    @Basic
    @Column(name = "AMOUNT", nullable = false, precision = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "FILE_PATH", nullable = true, length = 100)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "FILE_NAME", nullable = true, length = 45)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "AUTHORIZATION_DATE", nullable = true)
    public Timestamp getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(Timestamp authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    @Basic
    @Column(name = "ID_ACCESS_LEVEL", nullable = false)
    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "CREATION_DATE", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceEstimations that = (PriceEstimations) o;

        if (idPriceEstimation != that.idPriceEstimation) return false;
        if (idRequest != that.idRequest) return false;
        if (idAccount != that.idAccount) return false;
        if (idEstimationStatus != that.idEstimationStatus) return false;
        if (idCurrency != that.idCurrency) return false;
        if (idAccessLevel != that.idAccessLevel) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (authorizationDate != null ? !authorizationDate.equals(that.authorizationDate) : that.authorizationDate != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPriceEstimation;
        result = 31 * result + idRequest;
        result = 31 * result + idAccount;
        result = 31 * result + idEstimationStatus;
        result = 31 * result + idCurrency;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (authorizationDate != null ? authorizationDate.hashCode() : 0);
        result = 31 * result + idAccessLevel;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST", nullable = false)
    public Requests getRequestsByIdRequest() {
        return requestsByIdRequest;
    }

    public void setRequestsByIdRequest(Requests requestsByIdRequest) {
        this.requestsByIdRequest = requestsByIdRequest;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ESTIMATION_STATUS", referencedColumnName = "ID_ESTIMATION_STATUS", nullable = false)
    public CEstimationStatus getcEstimationStatusByIdEstimationStatus() {
        return cEstimationStatusByIdEstimationStatus;
    }

    public void setcEstimationStatusByIdEstimationStatus(CEstimationStatus cEstimationStatusByIdEstimationStatus) {
        this.cEstimationStatusByIdEstimationStatus = cEstimationStatusByIdEstimationStatus;
    }

    @OneToMany(mappedBy = "priceEstimationsByIdPriceEstimation")
    public Collection<RequestOrderDocuments> getRequestOrderDocumentsByIdPriceEstimation() {
        return requestOrderDocumentsByIdPriceEstimation;
    }

    public void setRequestOrderDocumentsByIdPriceEstimation(Collection<RequestOrderDocuments> requestOrderDocumentsByIdPriceEstimation) {
        this.requestOrderDocumentsByIdPriceEstimation = requestOrderDocumentsByIdPriceEstimation;
    }
}
