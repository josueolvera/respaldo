/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Desarrollador
 */
@Entity
@DynamicUpdate
@Table(name = "PAYROLL")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Payroll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PAYROLL")
    @JsonView(JsonViews.Root.class)
    private Integer idPayroll;

    @Column(name = "NUMERO_DE_EMPLEADO")
    @JsonView(JsonViews.Root.class)
    private Integer numeroDeEmpleado;

    @Column(name = "ID_DISTRIBUIDOR")
    @JsonView(JsonViews.Root.class)
    private Integer idDistribuidor;

    @Column(name = "ID_REGION")
    @JsonView(JsonViews.Root.class)
    private Integer idRegion;

    @Column(name = "ID_ZONA")
    @JsonView(JsonViews.Root.class)
    private Integer idZona;

    @Column(name = "ID_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBranch;

    @Column(name = "ID_AREA")
    @JsonView(JsonViews.Root.class)
    private Integer idArea;

    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @Size(max = 50)
    @Column(name = "DISTRIBUTOR_NAME")
    @JsonView(JsonViews.Root.class)
    private String distributorName;

    @Size(max = 50)
    @Column(name = "REGION_NAME")
    @JsonView(JsonViews.Root.class)
    private String regionName;

    @Size(max = 50)
    @Column(name = "ZONA_NAME")
    @JsonView(JsonViews.Root.class)
    private String zonaName;

    @Size(max = 50)
    @Column(name = "NOMBRE")
    @JsonView(JsonViews.Root.class)
    private String nombre;

    @Size(max = 16)
    @Column(name = "CLAVE_SAP")
    @JsonView(JsonViews.Root.class)
    private String claveSap;

    @Size(max = 50)
    @Column(name = "BANCO")
    @JsonView(JsonViews.Root.class)
    private String banco;

    @Column(name = "NUMERO_DE_CUENTA")
    @JsonView(JsonViews.Root.class)
    private String numeroDeCuenta;

    @Column(name = "CUENTA_CLABE")
    @JsonView(JsonViews.Root.class)
    private String cuentaClabe;

    @Size(max = 50)
    @Column(name = "SUCURSAL")
    @JsonView(JsonViews.Root.class)
    private String sucursal;

    @Size(max = 50)
    @Column(name = "AREA")
    @JsonView(JsonViews.Root.class)
    private String area;

    @Size(max = 50)
    @Column(name = "PUESTO")
    @JsonView(JsonViews.Root.class)
    private String puesto;

    @Size(max = 13)
    @Column(name = "RFC")
    @JsonView(JsonViews.Root.class)
    private String rfc;

    @Size(max = 50)
    @Column(name = "CURP")
    @JsonView(JsonViews.Root.class)
    private String curp;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INGRESO")
    @Convert(converter = DateConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDate fechaIngreso;

    @Column(name = "SUELDO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal sueldo;

    @Column(name = "MONTO_RETARDO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal montoRetardo;

    @Column(name = "DESCUENTO")
    private BigDecimal descuento;

    @Column(name = "BONO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal bono;

    @Column(name = "AJUSTE")
    @JsonView(JsonViews.Root.class)
    private BigDecimal ajuste;

    @Column(name = "PRIMA_VACACIONAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal primaVacacional;

    @Column(name = "EFECTIVO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal efectivo;

    @Column(name = "EFECTIVO_EDMON")
    @JsonView(JsonViews.Root.class)
    private BigDecimal efectivoEdmon;

    @Column(name = "COMISION_EMCOFIN")
    @JsonView(JsonViews.Root.class)
    private BigDecimal comisionEmcofin;

    @Column(name = "RHMAS_PAGO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rhmasPago;

    @Column(name = "RHMAS_TOTAL_FACTURAR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal rhmasTotalFacturar;

    @Column(name = "PERCEPCION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal percepcion;

    @Column(name = "DEDUCCION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal deduccion;

    @Column(name = "PAGO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal pago;

    @Column(name = "COMISION_NEC")
    @JsonView(JsonViews.Root.class)
    private BigDecimal comisionNec;

    @Column(name = "TOTAL_FACTURAR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalFacturar;

    @Column(name = "APOYO_PASAJES")
    @JsonView(JsonViews.Root.class)
    private BigDecimal apoyoPasajes;

    @Column(name = "MONTO_PROMOTOR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal montoPromotor;

    @Column(name = "COMISION_PROMOTOR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal comissionPromotor;

    @Column(name = "APOYO_375")
    @JsonView(JsonViews.Root.class)
    private BigDecimal apoyo375;

    @Column(name = "MONTO_MENSUAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal montoMensual;

    @Column(name = "COMISION_MENSUAL")
    @JsonView(JsonViews.Root.class)
    private BigDecimal comisionMensual;

    public Payroll() {
    }

    public Payroll(Integer idPayroll) {
        this.idPayroll = idPayroll;
    }

    public Payroll(Integer idPayroll, LocalDate fechaIngreso) {
        this.idPayroll = idPayroll;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdPayroll() {
        return idPayroll;
    }

    public void setIdPayroll(Integer idPayroll) {
        this.idPayroll = idPayroll;
    }

    public Integer getNumeroDeEmpleado() {
        return numeroDeEmpleado;
    }

    public void setNumeroDeEmpleado(Integer numeroDeEmpleado) {
        this.numeroDeEmpleado = numeroDeEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public String getCuentaClabe() {
        return cuentaClabe;
    }

    public void setCuentaClabe(String cuentaClabe) {
        this.cuentaClabe = cuentaClabe;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getBono() {
        return bono;
    }

    public void setBono(BigDecimal bono) {
        this.bono = bono;
    }

    public BigDecimal getAjuste() {
        return ajuste;
    }

    public void setAjuste(BigDecimal ajuste) {
        this.ajuste = ajuste;
    }

    public BigDecimal getPrimaVacacional() {
        return primaVacacional;
    }

    public void setPrimaVacacional(BigDecimal primaVacacional) {
        this.primaVacacional = primaVacacional;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getEfectivoEdmon() {
        return efectivoEdmon;
    }

    public void setEfectivoEdmon(BigDecimal efectivoEdmon) {
        this.efectivoEdmon = efectivoEdmon;
    }

    public BigDecimal getComisionEmcofin() {
        return comisionEmcofin;
    }

    public void setComisionEmcofin(BigDecimal comisionEmcofin) {
        this.comisionEmcofin = comisionEmcofin;
    }

    public BigDecimal getRhmasPago() {
        return rhmasPago;
    }

    public void setRhmasPago(BigDecimal rhmasPago) {
        this.rhmasPago = rhmasPago;
    }

    public BigDecimal getRhmasTotalFacturar() {
        return rhmasTotalFacturar;
    }

    public void setRhmasTotalFacturar(BigDecimal rhmasTotalFacturar) {
        this.rhmasTotalFacturar = rhmasTotalFacturar;
    }

    public BigDecimal getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(BigDecimal percepcion) {
        this.percepcion = percepcion;
    }

    public BigDecimal getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(BigDecimal deduccion) {
        this.deduccion = deduccion;
    }

    public BigDecimal getPago() {
        return pago;
    }

    public void setPago(BigDecimal pago) {
        this.pago = pago;
    }

    public BigDecimal getComisionNec() {
        return comisionNec;
    }

    public void setComisionNec(BigDecimal comisionNec) {
        this.comisionNec = comisionNec;
    }

    public BigDecimal getTotalFacturar() {
        return totalFacturar;
    }

    public void setTotalFacturar(BigDecimal totalFacturar) {
        this.totalFacturar = totalFacturar;
    }

    public BigDecimal getMontoRetardo() {
        return montoRetardo;
    }

    public void setMontoRetardo(BigDecimal montoRetardo) {
        this.montoRetardo = montoRetardo;
    }

    public DateFormatsPojo getFechaIngresoFormats() {
        if (fechaIngreso == null) {
            return null;
        }
        return new DateFormatsPojo(fechaIngreso);
    }

    public Integer getIdDistribuidor() {
        return idDistribuidor;
    }

    public void setIdDistribuidor(Integer idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getZonaName() {
        return zonaName;
    }

    public void setZonaName(String zonaName) {
        this.zonaName = zonaName;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public BigDecimal getApoyoPasajes() {
        return apoyoPasajes;
    }

    public void setApoyoPasajes(BigDecimal apoyoPasajes) {
        this.apoyoPasajes = apoyoPasajes;
    }

    public BigDecimal getMontoPromotor() {
        return montoPromotor;
    }

    public void setMontoPromotor(BigDecimal montoPromotor) {
        this.montoPromotor = montoPromotor;
    }

    public BigDecimal getComissionPromotor() {
        return comissionPromotor;
    }

    public void setComissionPromotor(BigDecimal comissionPromotor) {
        this.comissionPromotor = comissionPromotor;
    }

    public BigDecimal getApoyo375() {
        return apoyo375;
    }

    public void setApoyo375(BigDecimal apoyo375) {
        this.apoyo375 = apoyo375;
    }

    public BigDecimal getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(BigDecimal montoMensual) {
        this.montoMensual = montoMensual;
    }

    public BigDecimal getComisionMensual() {
        return comisionMensual;
    }

    public void setComisionMensual(BigDecimal comisionMensual) {
        this.comisionMensual = comisionMensual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPayroll != null ? idPayroll.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payroll)) {
            return false;
        }
        Payroll other = (Payroll) object;
        if ((this.idPayroll == null && other.idPayroll != null) || (this.idPayroll != null && !this.idPayroll.equals(other.idPayroll))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Payroll[ idPayroll=" + idPayroll + " ]";
    }
    
}
