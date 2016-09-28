package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gerardo8 on 26/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "CHECKS_BILLS_AMOUNTS")
public class ChecksBillsAmounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CHECK_BILL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBillAmount;

    @Column(name = "ID_CHECK_BILL", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBill;

    @JoinColumn(name = "ID_CHECK_BILL", referencedColumnName = "ID_CHECK_BILL")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private ChecksBills checkBill;

    @Basic(optional = false)
    @NotNull
    @Size(max = 15)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;

    public ChecksBillsAmounts() {
    }

    public ChecksBillsAmounts(ChecksBills checkBill, String description, BigDecimal amount) {
        this.checkBill = checkBill;
        this.description = description;
        this.amount = amount;
    }

    public Integer getIdCheckBillAmount() {
        return idCheckBillAmount;
    }

    public void setIdCheckBillAmount(Integer idCheckBillAmount) {
        this.idCheckBillAmount = idCheckBillAmount;
    }

    public Integer getIdCheckBill() {
        return idCheckBill;
    }

    public void setIdCheckBill(Integer idCheckBill) {
        this.idCheckBill = idCheckBill;
    }

    public ChecksBills getCheckBill() {
        return checkBill;
    }

    public void setCheckBill(ChecksBills checkBill) {
        this.checkBill = checkBill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecksBillsAmounts that = (ChecksBillsAmounts) o;

        return idCheckBillAmount != null ? idCheckBillAmount.equals(that.idCheckBillAmount) : that.idCheckBillAmount == null;

    }

    @Override
    public int hashCode() {
        return idCheckBillAmount != null ? idCheckBillAmount.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ChecksBillsAmounts{" +
                "idCheckBillAmount=" + idCheckBillAmount +
                ", idCheckBill=" + idCheckBill +
                ", checkBill=" + checkBill +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
