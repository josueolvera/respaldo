/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "PRICE_ESTIMATIONS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PriceEstimations implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ESTIMATION")
    @JsonView(JsonViews.Root.class)
    private Integer idEstimation;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Size(max = 100)
    @Column(name = "FILE_PATH")
    @JsonView(JsonViews.Root.class)
    private String filePath;
    
    @Size(max = 45)
    @Column(name = "FILE_NAME")
    private String fileName;
    
    @Size(max = 45)
    @Column(name = "SKU")
    @JsonView(JsonViews.Root.class)
    private String sku;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID_REQUEST")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Requests idRequest;
    
    @JoinColumn(name = "ID_ESTIMATION_STATUS", referencedColumnName = "ID_ESTIMATION_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CEstimationStatus idEstimationStatus;
    
    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Accounts idAccount;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currency;

    public PriceEstimations() {
    }

    public PriceEstimations(Integer idEstimation) {
        this.idEstimation = idEstimation;
    }

    public PriceEstimations(Integer idEstimation, BigDecimal amount, int idAccessLevel) {
        this.idEstimation = idEstimation;
        this.amount = amount;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdEstimation() {
        return idEstimation;
    }

    public void setIdEstimation(Integer idEstimation) {
        this.idEstimation = idEstimation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Requests getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Requests idRequest) {
        this.idRequest = idRequest;
    }

    public CEstimationStatus getIdEstimationStatus() {
        return idEstimationStatus;
    }

    public void setIdEstimationStatus(CEstimationStatus idEstimationStatus) {
        this.idEstimationStatus = idEstimationStatus;
    }

    public Accounts getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Accounts idAccount) {
        this.idAccount = idAccount;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstimation != null ? idEstimation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriceEstimations)) {
            return false;
        }
        PriceEstimations other = (PriceEstimations) object;
        if ((this.idEstimation == null && other.idEstimation != null) || (this.idEstimation != null && !this.idEstimation.equals(other.idEstimation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.config.PriceEstimations[ idEstimation=" + idEstimation + " ]";
    }
    
}
