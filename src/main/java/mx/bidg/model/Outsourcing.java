/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gerardo8
 */
@Entity
@Table(name = "OUTSOURCING")
public class Outsourcing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 10)
    @Column(name = "ID_W")
    private String idW;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUELDO")
    private BigDecimal sueldo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEPTIMO_DIA")
    private BigDecimal septimoDia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORAS_EXTRAS")
    private BigDecimal horasExtras;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESTAJOS")
    private BigDecimal destajos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMISIONES")
    private BigDecimal comisiones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INDEMNIZACION_ESPECIAL")
    private BigDecimal indemnizacionEspecial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREMIOS_EFICIENCIA")
    private BigDecimal premiosEficiencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VACACIONES_A_TIEMPO")
    private BigDecimal vacacionesATiempo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIMA_DE_VACACIONES_A_TIEMPO")
    private BigDecimal primaDeVacacionesATiempo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VACACIONES_REPORTADAS")
    private BigDecimal vacacionesReportadas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIMA_DE_VACACIONES_REPORTADA")
    private BigDecimal primaDeVacacionesReportada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AGUINALDO")
    private BigDecimal aguinaldo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIMA_DE_ANTIGUEDAD")
    private BigDecimal primaDeAntiguedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OTRAS_PERCEPCIONES")
    private BigDecimal otrasPercepciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_PERCEPCIONES")
    private BigDecimal totalPercepciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RET_INV_Y_VIDA")
    private BigDecimal retInvYVida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RET_CESANTIA")
    private BigDecimal retCesantia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RET_ENF_Y_MAT_OBRERO")
    private BigDecimal retEnfYMatObrero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEGURO_DE_VIVIENDA_INFONAVIT")
    private BigDecimal seguroDeViviendaInfonavit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRESTAMO_INFONAVIT_VSM")
    private BigDecimal prestamoInfonavitVsm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBS_AL_EMPLEO_ACREDITADO")
    private BigDecimal subsAlEmpleoAcreditado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBSIDIO_AL_EMPLEO_SP")
    private BigDecimal subsidioAlEmpleoSp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISR_ANTES_DE_SUBS_AL_EMPLEO")
    private BigDecimal isrAntesDeSubsAlEmpleo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISR_ART142")
    private BigDecimal isrArt142;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISR_SP")
    private BigDecimal isrSp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMSS")
    private BigDecimal imss;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRESTAMO_INFONAVIT")
    private BigDecimal prestamoInfonavit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AJUSTE_AL_NETO")
    private BigDecimal ajusteAlNeto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISR_FINIQUITO")
    private BigDecimal isrFiniquito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OTRAS_DEDUCCIONES")
    private BigDecimal otrasDeducciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_DEDUCCIONES")
    private BigDecimal totalDeducciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NETO")
    private BigDecimal neto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INVALIDEZ_Y_VIDA")
    private BigDecimal invalidezYVida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CESANTIA_Y_VEJEZ")
    private BigDecimal cesantiaYVejez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENF_Y_MAT_PATRON")
    private BigDecimal enfYMatPatron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FONDO_RETIRO_SAR")
    private BigDecimal fondoRetiroSar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPUESTO_ESTATAL")
    private BigDecimal impuestoEstatal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RIESGO_DE_TRABAJO")
    private BigDecimal riesgoDeTrabajo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMSS_EMPRESA")
    private BigDecimal imssEmpresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INFONAVIT_EMPRESA")
    private BigDecimal infonavitEmpresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GUARDERIA_IMSS")
    private BigDecimal guarderiaImss;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OTRAS_OBLIGACIONES")
    private BigDecimal otrasObligaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_OBLIGACIONES")
    private BigDecimal totalObligaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERCEPCIONES_SUBSIDIO_SP_TOTAL_OBLIGACIONES")
    private BigDecimal percepcionesSubsidioSpTotalObligaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMISION")
    private BigDecimal comision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IVA")
    private BigDecimal iva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_IVA")
    private BigDecimal totalIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private int status;

    public Outsourcing() {
    }

    public Outsourcing(Integer id) {
        this.id = id;
    }

    public Outsourcing(Integer id, BigDecimal sueldo, BigDecimal septimoDia, BigDecimal horasExtras, BigDecimal destajos, BigDecimal comisiones, BigDecimal indemnizacionEspecial, BigDecimal premiosEficiencia, BigDecimal vacacionesATiempo, BigDecimal primaDeVacacionesATiempo, BigDecimal vacacionesReportadas, BigDecimal primaDeVacacionesReportada, BigDecimal aguinaldo, BigDecimal primaDeAntiguedad, BigDecimal otrasPercepciones, BigDecimal totalPercepciones, BigDecimal retInvYVida, BigDecimal retCesantia, BigDecimal retEnfYMatObrero, BigDecimal seguroDeViviendaInfonavit, BigDecimal prestamoInfonavitVsm, BigDecimal subsAlEmpleoAcreditado, BigDecimal subsidioAlEmpleoSp, BigDecimal isrAntesDeSubsAlEmpleo, BigDecimal isrArt142, BigDecimal isrSp, BigDecimal imss, BigDecimal prestamoInfonavit, BigDecimal ajusteAlNeto, BigDecimal isrFiniquito, BigDecimal otrasDeducciones, BigDecimal totalDeducciones, BigDecimal neto, BigDecimal invalidezYVida, BigDecimal cesantiaYVejez, BigDecimal enfYMatPatron, BigDecimal fondoRetiroSar, BigDecimal impuestoEstatal, BigDecimal riesgoDeTrabajo, BigDecimal imssEmpresa, BigDecimal infonavitEmpresa, BigDecimal guarderiaImss, BigDecimal otrasObligaciones, BigDecimal totalObligaciones, BigDecimal percepcionesSubsidioSpTotalObligaciones, BigDecimal comision, BigDecimal iva, BigDecimal totalIva, LocalDateTime creationDate, int status) {
        this.id = id;
        this.sueldo = sueldo;
        this.septimoDia = septimoDia;
        this.horasExtras = horasExtras;
        this.destajos = destajos;
        this.comisiones = comisiones;
        this.indemnizacionEspecial = indemnizacionEspecial;
        this.premiosEficiencia = premiosEficiencia;
        this.vacacionesATiempo = vacacionesATiempo;
        this.primaDeVacacionesATiempo = primaDeVacacionesATiempo;
        this.vacacionesReportadas = vacacionesReportadas;
        this.primaDeVacacionesReportada = primaDeVacacionesReportada;
        this.aguinaldo = aguinaldo;
        this.primaDeAntiguedad = primaDeAntiguedad;
        this.otrasPercepciones = otrasPercepciones;
        this.totalPercepciones = totalPercepciones;
        this.retInvYVida = retInvYVida;
        this.retCesantia = retCesantia;
        this.retEnfYMatObrero = retEnfYMatObrero;
        this.seguroDeViviendaInfonavit = seguroDeViviendaInfonavit;
        this.prestamoInfonavitVsm = prestamoInfonavitVsm;
        this.subsAlEmpleoAcreditado = subsAlEmpleoAcreditado;
        this.subsidioAlEmpleoSp = subsidioAlEmpleoSp;
        this.isrAntesDeSubsAlEmpleo = isrAntesDeSubsAlEmpleo;
        this.isrArt142 = isrArt142;
        this.isrSp = isrSp;
        this.imss = imss;
        this.prestamoInfonavit = prestamoInfonavit;
        this.ajusteAlNeto = ajusteAlNeto;
        this.isrFiniquito = isrFiniquito;
        this.otrasDeducciones = otrasDeducciones;
        this.totalDeducciones = totalDeducciones;
        this.neto = neto;
        this.invalidezYVida = invalidezYVida;
        this.cesantiaYVejez = cesantiaYVejez;
        this.enfYMatPatron = enfYMatPatron;
        this.fondoRetiroSar = fondoRetiroSar;
        this.impuestoEstatal = impuestoEstatal;
        this.riesgoDeTrabajo = riesgoDeTrabajo;
        this.imssEmpresa = imssEmpresa;
        this.infonavitEmpresa = infonavitEmpresa;
        this.guarderiaImss = guarderiaImss;
        this.otrasObligaciones = otrasObligaciones;
        this.totalObligaciones = totalObligaciones;
        this.percepcionesSubsidioSpTotalObligaciones = percepcionesSubsidioSpTotalObligaciones;
        this.comision = comision;
        this.iva = iva;
        this.totalIva = totalIva;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdW() {
        return idW;
    }

    public void setIdW(String idW) {
        this.idW = idW;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public BigDecimal getSeptimoDia() {
        return septimoDia;
    }

    public void setSeptimoDia(BigDecimal septimoDia) {
        this.septimoDia = septimoDia;
    }

    public BigDecimal getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(BigDecimal horasExtras) {
        this.horasExtras = horasExtras;
    }

    public BigDecimal getDestajos() {
        return destajos;
    }

    public void setDestajos(BigDecimal destajos) {
        this.destajos = destajos;
    }

    public BigDecimal getComisiones() {
        return comisiones;
    }

    public void setComisiones(BigDecimal comisiones) {
        this.comisiones = comisiones;
    }

    public BigDecimal getIndemnizacionEspecial() {
        return indemnizacionEspecial;
    }

    public void setIndemnizacionEspecial(BigDecimal indemnizacionEspecial) {
        this.indemnizacionEspecial = indemnizacionEspecial;
    }

    public BigDecimal getPremiosEficiencia() {
        return premiosEficiencia;
    }

    public void setPremiosEficiencia(BigDecimal premiosEficiencia) {
        this.premiosEficiencia = premiosEficiencia;
    }

    public BigDecimal getVacacionesATiempo() {
        return vacacionesATiempo;
    }

    public void setVacacionesATiempo(BigDecimal vacacionesATiempo) {
        this.vacacionesATiempo = vacacionesATiempo;
    }

    public BigDecimal getPrimaDeVacacionesATiempo() {
        return primaDeVacacionesATiempo;
    }

    public void setPrimaDeVacacionesATiempo(BigDecimal primaDeVacacionesATiempo) {
        this.primaDeVacacionesATiempo = primaDeVacacionesATiempo;
    }

    public BigDecimal getVacacionesReportadas() {
        return vacacionesReportadas;
    }

    public void setVacacionesReportadas(BigDecimal vacacionesReportadas) {
        this.vacacionesReportadas = vacacionesReportadas;
    }

    public BigDecimal getPrimaDeVacacionesReportada() {
        return primaDeVacacionesReportada;
    }

    public void setPrimaDeVacacionesReportada(BigDecimal primaDeVacacionesReportada) {
        this.primaDeVacacionesReportada = primaDeVacacionesReportada;
    }

    public BigDecimal getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(BigDecimal aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public BigDecimal getPrimaDeAntiguedad() {
        return primaDeAntiguedad;
    }

    public void setPrimaDeAntiguedad(BigDecimal primaDeAntiguedad) {
        this.primaDeAntiguedad = primaDeAntiguedad;
    }

    public BigDecimal getOtrasPercepciones() {
        return otrasPercepciones;
    }

    public void setOtrasPercepciones(BigDecimal otrasPercepciones) {
        this.otrasPercepciones = otrasPercepciones;
    }

    public BigDecimal getTotalPercepciones() {
        return totalPercepciones;
    }

    public void setTotalPercepciones(BigDecimal totalPercepciones) {
        this.totalPercepciones = totalPercepciones;
    }

    public BigDecimal getRetInvYVida() {
        return retInvYVida;
    }

    public void setRetInvYVida(BigDecimal retInvYVida) {
        this.retInvYVida = retInvYVida;
    }

    public BigDecimal getRetCesantia() {
        return retCesantia;
    }

    public void setRetCesantia(BigDecimal retCesantia) {
        this.retCesantia = retCesantia;
    }

    public BigDecimal getRetEnfYMatObrero() {
        return retEnfYMatObrero;
    }

    public void setRetEnfYMatObrero(BigDecimal retEnfYMatObrero) {
        this.retEnfYMatObrero = retEnfYMatObrero;
    }

    public BigDecimal getSeguroDeViviendaInfonavit() {
        return seguroDeViviendaInfonavit;
    }

    public void setSeguroDeViviendaInfonavit(BigDecimal seguroDeViviendaInfonavit) {
        this.seguroDeViviendaInfonavit = seguroDeViviendaInfonavit;
    }

    public BigDecimal getPrestamoInfonavitVsm() {
        return prestamoInfonavitVsm;
    }

    public void setPrestamoInfonavitVsm(BigDecimal prestamoInfonavitVsm) {
        this.prestamoInfonavitVsm = prestamoInfonavitVsm;
    }

    public BigDecimal getSubsAlEmpleoAcreditado() {
        return subsAlEmpleoAcreditado;
    }

    public void setSubsAlEmpleoAcreditado(BigDecimal subsAlEmpleoAcreditado) {
        this.subsAlEmpleoAcreditado = subsAlEmpleoAcreditado;
    }

    public BigDecimal getSubsidioAlEmpleoSp() {
        return subsidioAlEmpleoSp;
    }

    public void setSubsidioAlEmpleoSp(BigDecimal subsidioAlEmpleoSp) {
        this.subsidioAlEmpleoSp = subsidioAlEmpleoSp;
    }

    public BigDecimal getIsrAntesDeSubsAlEmpleo() {
        return isrAntesDeSubsAlEmpleo;
    }

    public void setIsrAntesDeSubsAlEmpleo(BigDecimal isrAntesDeSubsAlEmpleo) {
        this.isrAntesDeSubsAlEmpleo = isrAntesDeSubsAlEmpleo;
    }

    public BigDecimal getIsrArt142() {
        return isrArt142;
    }

    public void setIsrArt142(BigDecimal isrArt142) {
        this.isrArt142 = isrArt142;
    }

    public BigDecimal getIsrSp() {
        return isrSp;
    }

    public void setIsrSp(BigDecimal isrSp) {
        this.isrSp = isrSp;
    }

    public BigDecimal getImss() {
        return imss;
    }

    public void setImss(BigDecimal imss) {
        this.imss = imss;
    }

    public BigDecimal getPrestamoInfonavit() {
        return prestamoInfonavit;
    }

    public void setPrestamoInfonavit(BigDecimal prestamoInfonavit) {
        this.prestamoInfonavit = prestamoInfonavit;
    }

    public BigDecimal getAjusteAlNeto() {
        return ajusteAlNeto;
    }

    public void setAjusteAlNeto(BigDecimal ajusteAlNeto) {
        this.ajusteAlNeto = ajusteAlNeto;
    }

    public BigDecimal getIsrFiniquito() {
        return isrFiniquito;
    }

    public void setIsrFiniquito(BigDecimal isrFiniquito) {
        this.isrFiniquito = isrFiniquito;
    }

    public BigDecimal getOtrasDeducciones() {
        return otrasDeducciones;
    }

    public void setOtrasDeducciones(BigDecimal otrasDeducciones) {
        this.otrasDeducciones = otrasDeducciones;
    }

    public BigDecimal getTotalDeducciones() {
        return totalDeducciones;
    }

    public void setTotalDeducciones(BigDecimal totalDeducciones) {
        this.totalDeducciones = totalDeducciones;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }

    public BigDecimal getInvalidezYVida() {
        return invalidezYVida;
    }

    public void setInvalidezYVida(BigDecimal invalidezYVida) {
        this.invalidezYVida = invalidezYVida;
    }

    public BigDecimal getCesantiaYVejez() {
        return cesantiaYVejez;
    }

    public void setCesantiaYVejez(BigDecimal cesantiaYVejez) {
        this.cesantiaYVejez = cesantiaYVejez;
    }

    public BigDecimal getEnfYMatPatron() {
        return enfYMatPatron;
    }

    public void setEnfYMatPatron(BigDecimal enfYMatPatron) {
        this.enfYMatPatron = enfYMatPatron;
    }

    public BigDecimal getFondoRetiroSar() {
        return fondoRetiroSar;
    }

    public void setFondoRetiroSar(BigDecimal fondoRetiroSar) {
        this.fondoRetiroSar = fondoRetiroSar;
    }

    public BigDecimal getImpuestoEstatal() {
        return impuestoEstatal;
    }

    public void setImpuestoEstatal(BigDecimal impuestoEstatal) {
        this.impuestoEstatal = impuestoEstatal;
    }

    public BigDecimal getRiesgoDeTrabajo() {
        return riesgoDeTrabajo;
    }

    public void setRiesgoDeTrabajo(BigDecimal riesgoDeTrabajo) {
        this.riesgoDeTrabajo = riesgoDeTrabajo;
    }

    public BigDecimal getImssEmpresa() {
        return imssEmpresa;
    }

    public void setImssEmpresa(BigDecimal imssEmpresa) {
        this.imssEmpresa = imssEmpresa;
    }

    public BigDecimal getInfonavitEmpresa() {
        return infonavitEmpresa;
    }

    public void setInfonavitEmpresa(BigDecimal infonavitEmpresa) {
        this.infonavitEmpresa = infonavitEmpresa;
    }

    public BigDecimal getGuarderiaImss() {
        return guarderiaImss;
    }

    public void setGuarderiaImss(BigDecimal guarderiaImss) {
        this.guarderiaImss = guarderiaImss;
    }

    public BigDecimal getOtrasObligaciones() {
        return otrasObligaciones;
    }

    public void setOtrasObligaciones(BigDecimal otrasObligaciones) {
        this.otrasObligaciones = otrasObligaciones;
    }

    public BigDecimal getTotalObligaciones() {
        return totalObligaciones;
    }

    public void setTotalObligaciones(BigDecimal totalObligaciones) {
        this.totalObligaciones = totalObligaciones;
    }

    public BigDecimal getPercepcionesSubsidioSpTotalObligaciones() {
        return percepcionesSubsidioSpTotalObligaciones;
    }

    public void setPercepcionesSubsidioSpTotalObligaciones(BigDecimal percepcionesSubsidioSpTotalObligaciones) {
        this.percepcionesSubsidioSpTotalObligaciones = percepcionesSubsidioSpTotalObligaciones;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Outsourcing)) {
            return false;
        }
        Outsourcing other = (Outsourcing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Outsourcing[ id=" + id + " ]";
    }
    
}
