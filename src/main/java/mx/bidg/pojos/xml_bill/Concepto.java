package mx.bidg.pojos.xml_bill;

import java.io.Serializable;

/**
 * Created by gerardo8 on 21/07/16.
 */
public class Concepto implements Serializable {

    private Float cantidad;

    private String unidad;

    private String noIdentificacion;

    private String descripcion;

    private Float valorUnitario;

    private Float importe;

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "Concepto{" +
                "cantidad=" + cantidad +
                ", unidad='" + unidad + '\'' +
                ", noIdentificacion='" + noIdentificacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", importe=" + importe +
                '}';
    }
}
