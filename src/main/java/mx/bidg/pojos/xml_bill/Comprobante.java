package mx.bidg.pojos.xml_bill;

import java.io.Serializable;

/**
 * Created by gerardo8 on 20/07/16.
 */
public class Comprobante implements Serializable {

    private Float total;

    private String folio;

    private Emisor emisor;

    private Receptor receptor;

    private Conceptos conceptos;

    private Impuestos impuestos;

    private Complemento complemento;

    private Float tipoCambio;

    private String moneda;

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public void setReceptor(Receptor receptor) {
        this.receptor = receptor;
    }

    public Conceptos getConceptos() {
        return conceptos;
    }

    public void setConceptos(Conceptos conceptos) {
        this.conceptos = conceptos;
    }

    public Impuestos getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Impuestos impuestos) {
        this.impuestos = impuestos;
    }

    public Complemento getComplemento() {
        return complemento;
    }

    public void setComplemento(Complemento complemento) {
        this.complemento = complemento;
    }

    public Float getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Float tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        return "Comprobante{" +
                "total=" + total +
                ", folio='" + folio + '\'' +
                ", emisor=" + emisor +
                ", receptor=" + receptor +
                ", conceptos=" + conceptos +
                ", impuestos=" + impuestos +
                ", complemento=" + complemento +
                '}';
    }
}
