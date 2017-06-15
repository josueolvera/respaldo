package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Leonardo on 12/06/2017.
 */

@Entity
@Table(name = "C_ACCOUNT_BANKS_TYPE")
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CAccountBanksType implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNT_BANK_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountBankType;

    @Size(max = 100)
    @Column(name = "NAME_BANK_TYPE")
    @JsonView(JsonViews.Root.class)
    private String nameBankType;

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

    public CAccountBanksType() {
    }

    public Integer getIdAccountBankType() {
        return idAccountBankType;
    }

    public void setIdAccountBankType(String IdAccountBankType) {
        this.idAccountBankType = idAccountBankType;
    }

    public String getNameBankType() {
        return nameBankType;
    }

    public void setNameBankType(String nameBankType) {
        this.nameBankType = nameBankType;
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
        if (!(o instanceof CAccountBanksType)) return false;

        CAccountBanksType that = (CAccountBanksType) o;

        if (getIdAccountBankType() != null ? !getIdAccountBankType().equals(that.getIdAccountBankType()) : that.getIdAccountBankType() != null)
            return false;
        if (getNameBankType() != null ? !getNameBankType().equals(that.getNameBankType()) : that.getNameBankType() != null)
            return false;
        if (getCreationDate() != null ? !getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() != null)
            return false;
        return getUsername() != null ? getUsername().equals(that.getUsername()) : that.getUsername() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdAccountBankType() != null ? getIdAccountBankType().hashCode() : 0;
        result = 31 * result + (getNameBankType() != null ? getNameBankType().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CAccountBanksType{" +
                "idAccountBankType=" + idAccountBankType +
                ", nameBankType='" + nameBankType + '\'' +
                ", creationDate=" + creationDate +
                ", username='" + username + '\'' +
                '}';
    }


}
