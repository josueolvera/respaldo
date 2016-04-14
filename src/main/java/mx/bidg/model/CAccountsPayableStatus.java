package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_ACCOUNTS_PAYABLE_STATUS")
public class CAccountsPayableStatus implements Serializable {

    public static final int INACTIVA = 1;
    public static final int PENDIENTE = 2;
    public static final int FINALIZADA = 3;
    public static final int CANCELADA = 4;

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCOUNT_PAYABLE_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountPayableStatus;
    
    @Size(max = 100)
    @Column(name = "ACCOUNTS_PAYABLE_STATUS")
    @JsonView(JsonViews.Root.class)
    private String accountsPayableStatus;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    public CAccountsPayableStatus() {
    }

    public CAccountsPayableStatus(Integer idAccountPayableStatus) {
        this.idAccountPayableStatus = idAccountPayableStatus;
    }

    public Integer getIdAccountPayableStatus() {
        return idAccountPayableStatus;
    }

    public void setIdAccountPayableStatus(Integer idAccountPayableStatus) {
        this.idAccountPayableStatus = idAccountPayableStatus;
    }

    public String getAccountsPayableStatus() {
        return accountsPayableStatus;
    }

    public void setAccountsPayableStatus(String accountsPayableStatus) {
        this.accountsPayableStatus = accountsPayableStatus;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountPayableStatus != null ? idAccountPayableStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAccountsPayableStatus)) {
            return false;
        }
        CAccountsPayableStatus other = (CAccountsPayableStatus) object;
        if ((this.idAccountPayableStatus == null && other.idAccountPayableStatus != null) || (this.idAccountPayableStatus != null && !this.idAccountPayableStatus.equals(other.idAccountPayableStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAccountsPayableStatus[ idAccountPayableStatus=" + idAccountPayableStatus + " ]";
    }

}
