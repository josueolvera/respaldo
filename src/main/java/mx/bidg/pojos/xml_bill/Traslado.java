package mx.bidg.pojos.xml_bill;

import java.io.Serializable;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Traslado implements Serializable {

    private String impuesto;

    private Float tasa;

    private Float importe;

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public Float getTasa() {
        return tasa;
    }

    public void setTasa(Float tasa) {
        this.tasa = tasa;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "Traslado{" +
                "impuesto='" + impuesto + '\'' +
                ", tasa=" + tasa +
                ", importe=" + importe +
                '}';
    }
}
