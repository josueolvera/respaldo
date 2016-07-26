package mx.bidg.pojos.xml_bill;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Conceptos implements Serializable {
    private List<Concepto> conceptos;

    public List<Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    @Override
    public String toString() {
        return "Conceptos{" +
                "conceptos=" + conceptos +
                '}';
    }
}
