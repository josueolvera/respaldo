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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Leonardo on 04/07/2017.
 */

@Entity
@DynamicUpdate
@Table (name = "DISTRIBUTORS_DETAIL_BANKS_HISTORY")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class DistributorsDetailBanksHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETAIL_BANKS_HISTORY")
    @JsonView(JsonViews.Root.class)
    private Integer idDetailBanksHistory;

    @Column(name = "ID_DISTRIBUTOR_DETAIL_BANK")
    @JsonView(JsonViews.Root.class)
    private  Integer idDistributorDetailBank;

    @Basic
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Size(max = 50)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    //CONSTRUCTORES

    public DistributorsDetailBanksHistory() {
    }

    public DistributorsDetailBanksHistory(Integer idDistributorDetailBank) {
        this.idDistributorDetailBank = idDistributorDetailBank;
    }

    //GETTERS AND SETTERS


    public Integer getIdDetailBanksHistory() {
        return idDetailBanksHistory;
    }

    public void setIdDetailBanksHistory(Integer idDetailBanksHistory) {
        this.idDetailBanksHistory = idDetailBanksHistory;
    }

    public Integer getIdDistributorDetailBank() {
        return idDistributorDetailBank;
    }

    public void setIdDistributorDetailBank(Integer idDistributorDetailBank) {
        this.idDistributorDetailBank = idDistributorDetailBank;
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

    //EQUALS() AND HASHCOSE()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistributorsDetailBanksHistory)) return false;

        DistributorsDetailBanksHistory that = (DistributorsDetailBanksHistory) o;

        if (getIdDetailBanksHistory() != null ? !getIdDetailBanksHistory().equals(that.getIdDetailBanksHistory()) : that.getIdDetailBanksHistory() != null)
            return false;
        if (getIdDistributorDetailBank() != null ? !getIdDistributorDetailBank().equals(that.getIdDistributorDetailBank()) : that.getIdDistributorDetailBank() != null)
            return false;
        if (getAmount() != null ? !getAmount().equals(that.getAmount()) : that.getAmount() != null) return false;
        if (getCreationDate() != null ? !getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() != null)
            return false;
        return getUsername() != null ? getUsername().equals(that.getUsername()) : that.getUsername() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdDetailBanksHistory() != null ? getIdDetailBanksHistory().hashCode() : 0;
        result = 31 * result + (getIdDistributorDetailBank() != null ? getIdDistributorDetailBank().hashCode() : 0);
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        return result;
    }

    //TO STRING()

    @Override
    public String toString() {
        return "DistributorsDetailBanksHistory{" +
                "idDetailBanksHistory=" + idDetailBanksHistory +
                ", idDistributorDetailBank=" + idDistributorDetailBank +
                ", amount=" + amount +
                ", creationDate=" + creationDate +
                ", username='" + username + '\'' +
                '}';
    }
}

