package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateConverter;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Serch on 29/06/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "CSB_PAY_COMMISSION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CsbPayCommission implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CSB_PAY_COMMISSION")
    @JsonView(JsonViews.Root.class)
    private Integer idCsbPayCommission;

    @Column(name = "ID_SAP_SALE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idSapSale;

    @Basic(optional = false)
    @Column(name = "ID_SALE")
    @JsonView(JsonViews.Root.class)
    private String idSale;

    @Column(name = "CLAVE_SAP")
    @JsonView(JsonViews.Root.class)
    private String claveSap;

    @Column(name = "PAYMENT_DATE")
    @Convert(converter = DateConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDate paymentDate;

    @Column(name = "PERCENTAGE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal percentage;

    @Column(name = "AMOUNT_COMMISSION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountCommission;

    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @JoinColumn(name = "ID_SAP_SALE", referencedColumnName = "ID_SAP_SALE")
    @OneToOne(optional = true)
    private SapSale sapSale;

    public CsbPayCommission() {
    }

    public CsbPayCommission(Integer idCsbPayCommission) {
        this.idCsbPayCommission = idCsbPayCommission;
    }

    public Integer getIdCsbPayCommission() {
        return idCsbPayCommission;
    }

    public void setIdCsbPayCommission(Integer idCsbPayCommission) {
        this.idCsbPayCommission = idCsbPayCommission;
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

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getAmountCommission() {
        return amountCommission;
    }

    public void setAmountCommission(BigDecimal amountCommission) {
        this.amountCommission = amountCommission;
    }

    public SapSale getSapSale() {
        return sapSale;
    }

    public void setSapSale(SapSale sapSale) {
        this.sapSale = sapSale;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CsbPayCommission that = (CsbPayCommission) o;

        return idCsbPayCommission != null ? idCsbPayCommission.equals(that.idCsbPayCommission) : that.idCsbPayCommission == null;
    }

    @Override
    public int hashCode() {
        return idCsbPayCommission != null ? idCsbPayCommission.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CsbPayCommission{" +
                "idCsbPayCommission=" + idCsbPayCommission +
                ", idSapSale=" + idSapSale +
                ", idSale='" + idSale + '\'' +
                ", claveSap='" + claveSap + '\'' +
                ", paymentDate=" + paymentDate +
                ", percentage=" + percentage +
                ", amountCommission=" + amountCommission +
                ", sapSale=" + sapSale +
                '}';
    }
}
