package mx.bidg.pojos;

import java.math.BigDecimal;

/**
 * Created by Desarrollador on 08/03/2017.
 */
public class FolioAmountTruckdriverPojo {
    String folio;
    BigDecimal commission;
    BigDecimal iva;
    BigDecimal total;

    public FolioAmountTruckdriverPojo() {
    }

    public FolioAmountTruckdriverPojo(String folio, BigDecimal commission, BigDecimal iva, BigDecimal total) {
        this.folio = folio;
        this.commission = commission;
        this.iva = iva;
        this.total = total;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FolioAmountTruckdriverPojo)) return false;

        FolioAmountTruckdriverPojo that = (FolioAmountTruckdriverPojo) o;

        if (folio != null ? !folio.equals(that.folio) : that.folio != null) return false;
        if (commission != null ? !commission.equals(that.commission) : that.commission != null) return false;
        if (iva != null ? !iva.equals(that.iva) : that.iva != null) return false;
        return total != null ? total.equals(that.total) : that.total == null;

    }

    @Override
    public int hashCode() {
        int result = folio != null ? folio.hashCode() : 0;
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (iva != null ? iva.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FolioAmountTruckdriverPojo{" +
                "folio='" + folio + '\'' +
                ", commission=" + commission +
                ", iva=" + iva +
                ", total=" + total +
                '}';
    }
}
