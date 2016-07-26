package mx.bidg.pojos.xml_bill;

import java.io.Serializable;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Complemento implements Serializable {
    private TimbreFiscalDigital timbreFiscalDigital;

    public TimbreFiscalDigital getTimbreFiscalDigital() {
        return timbreFiscalDigital;
    }

    public void setTimbreFiscalDigital(TimbreFiscalDigital timbreFiscalDigital) {
        this.timbreFiscalDigital = timbreFiscalDigital;
    }
}
