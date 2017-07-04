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

    @Basic(optional = false)
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

    //RELACIONES ZARCO

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

    //CONSTRUNCTORES ZARCO

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

        if (idBank != null ? !idBank.equals(that.idBank) : that.idBank != null) return false;
        if (idCurrency != null ? !idCurrency.equals(that.idCurrency) : that.idCurrency != null) return false;
        if (idDistributor != null ? !idDistributor.equals(that.idDistributor) : that.idDistributor != null)
            return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (accountClabe != null ? !accountClabe.equals(that.accountClabe) : that.accountClabe != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (banks != null ? !banks.equals(that.banks) : that.banks != null) return false;
        if (currencies != null ? !currencies.equals(that.currencies) : that.currencies != null) return false;
        return distributors != null ? distributors.equals(that.distributors) : that.distributors == null;
    }

    @Override
    public int hashCode() {
        int result = idBank != null ? idBank.hashCode() : 0;
        result = 31 * result + (idCurrency != null ? idCurrency.hashCode() : 0);
        result = 31 * result + (idDistributor != null ? idDistributor.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (accountClabe != null ? accountClabe.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (banks != null ? banks.hashCode() : 0);
        result = 31 * result + (currencies != null ? currencies.hashCode() : 0);
        result = 31 * result + (distributors != null ? distributors.hashCode() : 0);
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
