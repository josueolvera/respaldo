//package mx.bidg.model;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonView;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import mx.bidg.config.JsonViews;
//import mx.bidg.pojos.DateFormatsPojo;
//import mx.bidg.utils.DateTimeConverter;
//import org.hibernate.annotations.DynamicUpdate;
//
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;

package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Created by Leonardo on 13/06/2017.
 */
@Entity
@Table(name = "DISTRIBUTORS_DETAIL_BANKS")
@DynamicUpdate

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator,class, property = "_id")
public class  DistributorsDetailBanks implements Serializable {

    private static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISTRIBUTO_DETAIL_BANK")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorDetailBanks;

    @Column(name = "ID_BANK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBank;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private  Integer idcurrency;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer iddistributo;

    @Column(name = "ID_ACCOUNT_BANK_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idaccountbanktype;

    @Size(max = 15)
    @Column(name = "ACCOUNT_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String accountnumber;

    @Size(max = 20)
    @Column(name = "ACCOUNT_CLAVE")
    @JsonView(JsonViews.Root.class)
    private String accountclave;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amouunt;

    @Basic(optional = false)
    


}






















//    @Basic(optional = false)
//    @JsonView(JsonViews.Root.class)
//    @NotNull
//    @Convert(converter = DateTimeConverter.class)
//    @Column(name = "CREATION_DATE")
//    private LocalDateTime creationDate;
//
//    @Size(max = 50)
//    @Column(name = "USERNAME")
//    @JsonView(JsonViews.Root.class)
//    private String username;
//
//    //RELACIONES ZARCO
//
//    @JoinColumn(name = "ID_BANK", referencedColumnName = "ID_BANK")
//    @ManyToOne(optional = false)
//    @JsonView(JsonViews.Embedded.class)
//    private CBanks banks;
//
//    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
//    @ManyToOne(optional = false)
//    @JsonView(JsonViews.Embedded.class)
//    private CCurrencies currencies;
//
//    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
//    @ManyToOne(optional = false)
//    @JsonView(JsonViews.Embedded.class)
//    private CDistributors distributors;
//
//    @JoinColumn(name = "ID_ACCOUNT_BANK_TYPE", referencedColumnName = "ID_ACCOUNT_BANK_TYPE")
//    @ManyToOne(optional = false)
//    @JsonView(JsonViews.Embedded.class)
//    private CAccountBanksType accountBanksType;
//
//    //CONSTRUNCTORES ZARCO
//
//    public DistributorsDetailBanks(){
//    }
//
//    public DistributorsDetailBanks(Integer idDistributorDetailBank) {
//        this.idDistributorDetailBank = idDistributorDetailBank;
//    }
//
//    public DistributorsDetailBanks(Integer idDistributorDetailBank, LocalDateTime creationDate) {
//        this.idDistributorDetailBank = idDistributorDetailBank;
//        this.creationDate = creationDate;
//    }
//
//    //GETTERS AND SETTERS
//
//    public Integer getIdDistributorDetailBank() {
//        return idDistributorDetailBank;
//    }
//
//    public void setIdDistributorDetailBank(Integer idDistributorDetailBank) {
//        this.idDistributorDetailBank = idDistributorDetailBank;
//    }
//
//    public Integer getIdBank() {
//        return idBank;
//    }
//
//    public void setIdBank(Integer idBank) {
//        this.idBank = idBank;
//    }
//
//    public Integer getIdCurrency() {
//        return idCurrency;
//    }
//
//    public void setIdCurrency(Integer idCurrency) {
//        this.idCurrency = idCurrency;
//    }
//
//    public Integer getIdDistributor() {
//        return idDistributor;
//    }
//
//    public void setIdDistributor(Integer idDistributor) {
//        this.idDistributor = idDistributor;
//    }
//
//    public Integer getIdAccountBankType() {
//        return idAccountBankType;
//    }
//
//    public void setIdAccountBankType(Integer idAccountBankType) {
//        this.idAccountBankType = idAccountBankType;
//    }
//
//    public String getAccountNumber() {
//        return accountNumber;
//    }
//
//    public void setAccountNumber(String accountNumber) {
//        this.accountNumber = accountNumber;
//    }
//
//    public String getAccountClave() {
//        return accountClave;
//    }
//
//    public void setAccountClave(String accountClave) {
//        this.accountClave = accountClave;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public LocalDateTime getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(LocalDateTime creationDate) {
//        this.creationDate = creationDate;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public CBanks getBanks() {
//        return banks;
//    }
//
//    public void setBanks(CBanks banks) {
//        this.banks = banks;
//    }
//
//    public CCurrencies getCurrencies() {
//        return currencies;
//    }
//
//    public void setCurrencies(CCurrencies currencies) {
//        this.currencies = currencies;
//    }
//
//    public CDistributors getDistributors() {
//        return distributors;
//    }
//
//    public void setDistributors(CDistributors distributors) {
//        this.distributors = distributors;
//    }
//
//    public CAccountBanksType getAccountBanksType() {
//        return accountBanksType;
//    }
//
//    public void setAccountBanksType(CAccountBanksType accountBanksType) {
//        this.accountBanksType = accountBanksType;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof DistributorsDetailBanks)) return false;
//
//        DistributorsDetailBanks that = (DistributorsDetailBanks) o;
//
//        if (getIdDistributorDetailBank() != null ? !getIdDistributorDetailBank().equals(that.getIdDistributorDetailBank()) : that.getIdDistributorDetailBank() != null)
//            return false;
//        if (getDistributors() != null ? !getDistributors().equals(that.getDistributors()) : that.getDistributors() != null)
//            return false;
//        return getAccountBanksType() != null ? getAccountBanksType().equals(that.getAccountBanksType()) : that.getAccountBanksType() == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = getIdDistributorDetailBank() != null ? getIdDistributorDetailBank().hashCode() : 0;
//        result = 31 * result + (getDistributors() != null ? getDistributors().hashCode() : 0);
//        result = 31 * result + (getAccountBanksType() != null ? getAccountBanksType().hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "DistributorsDetailBanks{" +
//                "idDistributorDetailBank=" + idDistributorDetailBank +
//                ", idBank=" + idBank +
//                ", idCurrency=" + idCurrency +
//                ", idDistributor=" + idDistributor +
//                ", idAccountBankType=" + idAccountBankType +
//                ", accountNumber='" + accountNumber + '\'' +
//                ", accountClave='" + accountClave + '\'' +
//                ", amount=" + amount +
//                ", creationDate=" + creationDate +
//                ", username='" + username + '\'' +
//                ", banks=" + banks +
//                ", currencies=" + currencies +
//                ", distributors=" + distributors +
//                ", accountBanksType=" + accountBanksType +
//                '}';
//    }
//}
