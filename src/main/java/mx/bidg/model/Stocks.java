package mx.bidg.model;

/**
 *
 * @author Rafael Viveros
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.AccessLevelFiltered;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@DynamicUpdate
@Table(name = "STOCKS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Stocks implements AccessLevelFiltered, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_STOCK")
    @JsonView(JsonViews.Root.class)
    private Integer idStock;

    @Column(name = "SERIAL_NUMBER")
    @Basic(optional = true)
    @JsonView(JsonViews.Root.class)
    private String serialNumber;

    @Size(max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Size(max = 2048)
    @Column(name = "STOCK_FOLIO")
    @JsonView(JsonViews.Root.class)
    private String stockFolio;

    @Basic(optional = false)
    @Column(name = "CREATION_DATE", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "PURCHASE_PRICE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal purchasePrice;

    @Column(name = "ID_ARTICLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idArticle;

    @Column(name = "ID_DW_ENTERPRISE", insertable = false, updatable = false)
    private Integer idDwEnterprises;

    @Column(name = "ID_ARTICLE_STATUS", insertable = false, updatable = false)
    private Integer idArticleStatus;

    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;

    @JoinColumn(name = "ID_ARTICLE_STATUS", referencedColumnName = "ID_ARTICLE_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CArticleStatus articleStatus;

    @JoinColumn(name = "ID_ARTICLE", referencedColumnName = "ID_ARTICLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CArticles article;

    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView(JsonViews.Embedded.class)
    private DwEnterprises dwEnterprises;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stocks")
    @JsonView(JsonViews.Embedded.class)
    private List<Properties> propertiesList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock")
    @JsonView(JsonViews.Embedded.class)
    private List<StockDocuments> stockDocumentsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stocks")
    private List<StockEmployeeAssignments> stockEmployeeAssignmentsList;

    public Stocks() {
    }

    public Stocks(Integer idStock) {
        this.idStock = idStock;
    }

    public Stocks(Integer idStock, LocalDateTime creationDate) {
        this.idStock = idStock;
        this.creationDate = creationDate;
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getStockFolio() {
        return stockFolio;
    }

    public void setStockFolio(String stockFolio) {
        this.stockFolio = stockFolio;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getIdArticleStatus() {
        return idArticleStatus;
    }

    public void setIdArticleStatus(Integer idArticleStatus) {
        this.idArticleStatus = idArticleStatus;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdDwEnterprises() {
        return idDwEnterprises;
    }

    public void setIdDwEnterprises(Integer idDwEnterprises) {
        this.idDwEnterprises = idDwEnterprises;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(CArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }

    public CArticles getArticle() {
        return article;
    }

    public void setArticle(CArticles cArticles) {
        this.article = cArticles;
    }

    public DwEnterprises getDwEnterprises() {
        return dwEnterprises;
    }

    public void setDwEnterprises(DwEnterprises dwEnterprises) {
        this.dwEnterprises = dwEnterprises;
    }

    public List<Properties> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(List<Properties> propertiesList) {
        this.propertiesList = propertiesList;
    }

    public List<StockDocuments> getStockDocumentsList() {
        return stockDocumentsList;
    }

    public void setStockDocumentsList(List<StockDocuments> stockDocumentsList) {
        this.stockDocumentsList = stockDocumentsList;
    }

    public DateFormatsPojo getCreationDateFormats() {
        return new DateFormatsPojo(creationDate);
    }

    public List<StockEmployeeAssignments> getStockEmployeeAssignmentsList() {
        return stockEmployeeAssignmentsList;
    }

    public void setStockEmployeeAssignmentsList(List<StockEmployeeAssignments> stockEmployeeAssignmentsList) {
        this.stockEmployeeAssignmentsList = stockEmployeeAssignmentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStock != null ? idStock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stocks)) {
            return false;
        }
        Stocks other = (Stocks) object;
        if ((this.idStock == null && other.idStock != null) || (this.idStock != null && !this.idStock.equals(other.idStock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Stocks[ idStock=" + idStock + " ]";
    }
}
