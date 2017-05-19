package mx.bidg.model.siad;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sergio Rivero on 18/05/2017.
 */
@Entity
@Table(name = "REQUEST_ORDER_DOCUMENTS", schema = "BIDG_DBA", catalog = "")
public class RequestOrderDocuments {
    private int idRequestOrderDocument;
    private Integer idPriceEstimation;
    private String filePath;
    private Integer fileName;
    private String username;
    private Timestamp creationDate;
    private PriceEstimations priceEstimationsByIdPriceEstimation;

    @Id
    @Column(name = "ID_REQUEST_ORDER_DOCUMENT", nullable = false)
    public int getIdRequestOrderDocument() {
        return idRequestOrderDocument;
    }

    public void setIdRequestOrderDocument(int idRequestOrderDocument) {
        this.idRequestOrderDocument = idRequestOrderDocument;
    }

    @Basic
    @Column(name = "ID_PRICE_ESTIMATION", nullable = true)
    public Integer getIdPriceEstimation() {
        return idPriceEstimation;
    }

    public void setIdPriceEstimation(Integer idPriceEstimation) {
        this.idPriceEstimation = idPriceEstimation;
    }

    @Basic
    @Column(name = "FILE_PATH", nullable = true, length = 200)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "FILE_NAME", nullable = true)
    public Integer getFileName() {
        return fileName;
    }

    public void setFileName(Integer fileName) {
        this.fileName = fileName;
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
    @Column(name = "CREATION_DATE", nullable = true)
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

        RequestOrderDocuments that = (RequestOrderDocuments) o;

        if (idRequestOrderDocument != that.idRequestOrderDocument) return false;
        if (idPriceEstimation != null ? !idPriceEstimation.equals(that.idPriceEstimation) : that.idPriceEstimation != null)
            return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRequestOrderDocument;
        result = 31 * result + (idPriceEstimation != null ? idPriceEstimation.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_PRICE_ESTIMATION", referencedColumnName = "ID_PRICE_ESTIMATION")
    public PriceEstimations getPriceEstimationsByIdPriceEstimation() {
        return priceEstimationsByIdPriceEstimation;
    }

    public void setPriceEstimationsByIdPriceEstimation(PriceEstimations priceEstimationsByIdPriceEstimation) {
        this.priceEstimationsByIdPriceEstimation = priceEstimationsByIdPriceEstimation;
    }
}
