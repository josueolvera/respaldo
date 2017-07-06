package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;



/**
 * Created by Leonardo on 13/06/2017.
 */
@Entity
@DynamicUpdate
@Table (name = "DISTRIBUTORS_DETAIL_BANKS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class DistributorsDetailBanks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISTRIBUTOR_DETAIL_BANK")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorDetailBank;

    @Column(name = "ID_BANK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBank;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private  Integer idCurrency;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Size(max = 16)
    @Column(name = "ACCOUNT_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String accountNumber;

    @Size(max = 18)
    @Column(name = "ACCOUNT_CLABE")
    @JsonView(JsonViews.Root.class)
    private String accountClabe;

    @Basic
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @NotNull
    @Convert(converter = DateTimeConverter.class)
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;


    //RELACIONES

    @JoinColumn(name = "ID_BANK", referencedColumnName = "ID_BANK")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBanks banks;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currencies;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributors;


    //CONSTRUNCTORES

    public DistributorsDetailBanks(){
    }

    public DistributorsDetailBanks(Integer idDistributorDetailBank) {
        this.idDistributorDetailBank = idDistributorDetailBank;
    }

    public DistributorsDetailBanks(Integer idDistributorDetailBank, LocalDateTime creationDate) {
        this.idDistributorDetailBank = idDistributorDetailBank;
        this.creationDate = creationDate;
    }

    //GETTERS AND SETTERS

    public Integer getIdDistributorDetailBank() {
        return idDistributorDetailBank;
    }

    public void setIdDistributorDetailBank(Integer idDistributorDetailBank) {
        this.idDistributorDetailBank = idDistributorDetailBank;
    }

    public Integer getIdBank() {
        return idBank;
    }

    public void setIdBank(Integer idBank) {
        this.idBank = idBank;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public CBanks getBanks() {
        return banks;
    }

    public void setBanks(CBanks banks) {
        this.banks = banks;
    }

    public CCurrencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(CCurrencies currencies) {
        this.currencies = currencies;
    }

    public CDistributors getDistributors() {
        return distributors;
    }

    public void setDistributors(CDistributors distributors) {
        this.distributors = distributors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistributorsDetailBanks that = (DistributorsDetailBanks) o;

        if (getIdBank() != null ? !getIdBank().equals(that.getIdBank()) : that.getIdBank() != null) return false;
        if (getIdCurrency() != null ? !getIdCurrency().equals(that.getIdCurrency()) : that.getIdCurrency() != null)
            return false;
        if (getIdDistributor() != null ? !getIdDistributor().equals(that.getIdDistributor()) : that.getIdDistributor() != null)
            return false;
        if (getAccountNumber() != null ? !getAccountNumber().equals(that.getAccountNumber()) : that.getAccountNumber() != null)
            return false;
        if (getAccountClabe() != null ? !getAccountClabe().equals(that.getAccountClabe()) : that.getAccountClabe() != null)
            return false;
        if (getAmount() != null ? !getAmount().equals(that.getAmount()) : that.getAmount() != null) return false;
        if (getCreationDate() != null ? !getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        if (getBanks() != null ? !getBanks().equals(that.getBanks()) : that.getBanks() != null) return false;
        if (getCurrencies() != null ? !getCurrencies().equals(that.getCurrencies()) : that.getCurrencies() != null)
            return false;
        return getDistributors() != null ? getDistributors().equals(that.getDistributors()) : that.getDistributors() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdBank() != null ? getIdBank().hashCode() : 0;
        result = 31 * result + (getIdCurrency() != null ? getIdCurrency().hashCode() : 0);
        result = 31 * result + (getIdDistributor() != null ? getIdDistributor().hashCode() : 0);
        result = 31 * result + (getAccountNumber() != null ? getAccountNumber().hashCode() : 0);
        result = 31 * result + (getAccountClabe() != null ? getAccountClabe().hashCode() : 0);
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getBanks() != null ? getBanks().hashCode() : 0);
        result = 31 * result + (getCurrencies() != null ? getCurrencies().hashCode() : 0);
        result = 31 * result + (getDistributors() != null ? getDistributors().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DistributorsDetailBanks{" +
                "idDistributorDetailBank=" + idDistributorDetailBank +
                ", idBank=" + idBank +
                ", idCurrency=" + idCurrency +
                ", idDistributor=" + idDistributor +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountClabe='" + accountClabe + '\'' +
                ", amount=" + amount +
                ", creationDate=" + creationDate +
                ", username='" + username + '\'' +
                ", banks=" + banks +
                ", currencies=" + currencies +
                ", distributors=" + distributors +
                '}';
    }
}
