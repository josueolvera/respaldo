package mx.bidg.pojos.xml_bill;

import java.io.Serializable;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Impuestos implements Serializable {

    private Float totalImpuestosTrasladados;

    private Traslados traslados;

    public Float getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }

    public void setTotalImpuestosTrasladados(Float totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }

    public Traslados getTraslados() {
        return traslados;
    }

    public void setTraslados(Traslados traslados) {
        this.traslados = traslados;
    }

    @Override
    public String toString() {
        return "Impuestos{" +
                "totalImpuestosTrasladados=" + totalImpuestosTrasladados +
                ", traslados=" + traslados +
                '}';
    }
}
