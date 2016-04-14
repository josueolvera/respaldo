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
@Table(name = "C_BALANCE_STATUS")
public class CBalanceStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BALANCE_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idBalanceStatus;
    
    @Size(max = 45)
    @Column(name = "BALANCE_STATUS")
    @JsonView(JsonViews.Root.class)
    private String balanceStatus;

    public CBalanceStatus() {
    }

    public CBalanceStatus(Integer idBalanceStatus) {
        this.idBalanceStatus = idBalanceStatus;
    }

    public Integer getIdBalanceStatus() {
        return idBalanceStatus;
    }

    public void setIdBalanceStatus(Integer idBalanceStatus) {
        this.idBalanceStatus = idBalanceStatus;
    }

    public String getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(String balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBalanceStatus != null ? idBalanceStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBalanceStatus)) {
            return false;
        }
        CBalanceStatus other = (CBalanceStatus) object;
        if ((this.idBalanceStatus == null && other.idBalanceStatus != null) || (this.idBalanceStatus != null && !this.idBalanceStatus.equals(other.idBalanceStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBalanceStatus[ idBalanceStatus=" + idBalanceStatus + " ]";
    }
    
}
