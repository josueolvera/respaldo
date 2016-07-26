package mx.bidg.pojos.xml_bill;

import java.io.Serializable;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Emisor implements Serializable {
    private DomicilioFiscal domicilioFiscal;
    private ExpedidoEn expedidoEn;
    private RegimenFiscal regimenFiscal;

    public DomicilioFiscal getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(DomicilioFiscal domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public ExpedidoEn getExpedidoEn() {
        return expedidoEn;
    }

    public void setExpedidoEn(ExpedidoEn expedidoEn) {
        this.expedidoEn = expedidoEn;
    }

    public RegimenFiscal getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(RegimenFiscal regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }
}
