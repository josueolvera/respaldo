package mx.bidg.pojos.xml_bill;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Traslados implements Serializable {
    private List<Traslado> traslados;

    public List<Traslado> getTraslados() {
        return traslados;
    }

    public void setTraslados(List<Traslado> traslados) {
        this.traslados = traslados;
    }

    @Override
    public String toString() {
        return "Traslados{" +
                "traslados=" + traslados +
                '}';
    }
}
