package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gerardo8 on 26/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "CHECKS_BILLS")
public class ChecksBills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CHECK_BILL")
    @JsonView(JsonViews.Root.class)
    private Integer idCheckBill;

    @Column(name = "ID_CHECK", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCheck;

    @JoinColumn(name = "ID_CHECK", referencedColumnName = "ID_CHECK")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Checks check;

    @Column(name = "ID_VOUCHER_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idVoucherType;

    @JoinColumn(name = "ID_VOUCHER_TYPE", referencedColumnName = "ID_VOUCHER_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CVoucherTypes voucherType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "VOUCHER_FOLIO")
    @JsonView(JsonViews.Root.class)
    private String voucherFolio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "VOUCHER_TAX_TOTAL")
    @JsonView(JsonViews.Root.class)
    private Float voucherTaxTotal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "VOUCHER_TOTAL")
    @JsonView(JsonViews.Root.class)
    private Float voucherTotal;

    @OneToMany(mappedBy = "checkBill")
    @JsonView(JsonViews.Embedded.class)
    private List<ChecksBillsAmounts> checksBillsAmounts;

    @OneToMany(mappedBy = "checkBill")
    @JsonView(JsonViews.Embedded.class)
    private List<ChecksBillsDocuments> checksBillsDocuments;

    public ChecksBills() {
    }

    public ChecksBills(Checks check, CVoucherTypes voucherType, String voucherFolio, Float voucherTaxTotal, Float voucherTotal) {
        this.check = check;
        this.voucherType = voucherType;
        this.voucherFolio = voucherFolio;
        this.voucherTaxTotal = voucherTaxTotal;
        this.voucherTotal = voucherTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecksBills that = (ChecksBills) o;

        return idCheckBill != null ? idCheckBill.equals(that.idCheckBill) : that.idCheckBill == null;

    }

    @Override
    public int hashCode() {
        return idCheckBill != null ? idCheckBill.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ChecksBills{" +
                "idCheckBill=" + idCheckBill +
                ", idCheck=" + idCheck +
                ", check=" + check +
                ", idVoucherType=" + idVoucherType +
                ", voucherType=" + voucherType +
                ", voucherFolio='" + voucherFolio + '\'' +
                ", voucherTaxTotal=" + voucherTaxTotal +
                ", voucherTotal=" + voucherTotal +
                ", checksBillsAmounts=" + checksBillsAmounts +
                ", checksBillsDocuments=" + checksBillsDocuments +
                '}';
    }
}
