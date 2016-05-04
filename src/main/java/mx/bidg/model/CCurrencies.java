package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_CURRENCIES")
public class CCurrencies implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CURRENCY")
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    
    @Size(max = 50)
    @Column(name = "CURRENCY")
    @JsonView(JsonViews.Root.class)
    private String currency;

    @Size(max = 10)
    @NotNull
    @Column(name = "ACRONYM")
    @JsonView(JsonViews.Root.class)
    private String acronym;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RATE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rate;

    public CCurrencies() {
    }

    public CCurrencies(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public BigDecimal getRate() {
        if (rate != null) {
            return BigDecimal.ONE.divide(rate, 2, BigDecimal.ROUND_FLOOR);
        }
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurrency != null ? idCurrency.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CCurrencies)) {
            return false;
        }
        CCurrencies other = (CCurrencies) object;
        if ((this.idCurrency == null && other.idCurrency != null) || (this.idCurrency != null && !this.idCurrency.equals(other.idCurrency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CCurrencies[ idCurrency=" + idCurrency + " ]";
    }

}
