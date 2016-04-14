package mx.bidg.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "C_TRANSACTIONS_STATUS")
public class CTransactionsStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACTION_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idTransactionStatus;
    
    @Size(max = 100)
    @Column(name = "TRANSACTION_STATUS")
    @JsonView(JsonViews.Root.class)
    private String transactionStatus;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    public CTransactionsStatus() {
    }

    public CTransactionsStatus(Integer idTransactionStatus) {
        this.idTransactionStatus = idTransactionStatus;
    }

    public Integer getIdTransactionStatus() {
        return idTransactionStatus;
    }

    public void setIdTransactionStatus(Integer idTransactionStatus) {
        this.idTransactionStatus = idTransactionStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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
        hash += (idTransactionStatus != null ? idTransactionStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTransactionsStatus)) {
            return false;
        }
        CTransactionsStatus other = (CTransactionsStatus) object;
        if ((this.idTransactionStatus == null && other.idTransactionStatus != null) || (this.idTransactionStatus != null && !this.idTransactionStatus.equals(other.idTransactionStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CTransactionsStatus[ idTransactionStatus=" + idTransactionStatus + " ]";
    }
    
}
