package mx.bidg.pojos;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Desarrollador on 02/03/2017.
 */
public class PdfAternaPojo {

    BigDecimal subtotalD;
    BigDecimal ivaD;
    BigDecimal totalD;
    BigDecimal subtotalM;
    BigDecimal ivaM;
    BigDecimal totalM;
    boolean conciliation;
    Integer NumSecures;
    List<String> foliosNoEncontradoD;
    List<String> foliosNoEncontradoM;
    List<String> foliosEncontrados;
    List<FolioAmountTruckdriverPojo> foliosMNoEncontradosD;
    List<FolioAmountTruckdriverPojo> foliosMNoEncontradosM;

    public PdfAternaPojo() {
    }

    public PdfAternaPojo(BigDecimal subtotalD, BigDecimal ivaD, BigDecimal totalD, BigDecimal subtotalM, BigDecimal ivaM, BigDecimal totalM, boolean conciliation) {
        this.subtotalD = subtotalD;
        this.ivaD = ivaD;
        this.totalD = totalD;
        this.subtotalM = subtotalM;
        this.ivaM = ivaM;
        this.totalM = totalM;
        this.conciliation = conciliation;
    }

    public BigDecimal getSubtotalD() {
        return subtotalD;
    }

    public void setSubtotalD(BigDecimal subtotalD) {
        this.subtotalD = subtotalD;
    }

    public BigDecimal getIvaD() {
        return ivaD;
    }

    public void setIvaD(BigDecimal ivaD) {
        this.ivaD = ivaD;
    }

    public BigDecimal getTotalD() {
        return totalD;
    }

    public void setTotalD(BigDecimal totalD) {
        this.totalD = totalD;
    }

    public BigDecimal getSubtotalM() {
        return subtotalM;
    }

    public void setSubtotalM(BigDecimal subtotalM) {
        this.subtotalM = subtotalM;
    }

    public BigDecimal getIvaM() {
        return ivaM;
    }

    public void setIvaM(BigDecimal ivaM) {
        this.ivaM = ivaM;
    }

    public BigDecimal getTotalM() {
        return totalM;
    }

    public void setTotalM(BigDecimal totalM) {
        this.totalM = totalM;
    }

    public boolean isConciliation() {
        return conciliation;
    }

    public void setConciliation(boolean conciliation) {
        this.conciliation = conciliation;
    }

    public Integer getNumSecures() {
        return NumSecures;
    }

    public void setNumSecures(Integer numSecures) {
        NumSecures = numSecures;
    }

    public List<String> getFoliosNoEncontradoD() {
        return foliosNoEncontradoD;
    }

    public void setFoliosNoEncontradoD(List<String> foliosNoEncontrado) {
        this.foliosNoEncontradoD = foliosNoEncontrado;
    }

    public List<String> getFoliosEncontrados() {
        return foliosEncontrados;
    }

    public void setFoliosEncontrados(List<String> foliosEncontrados) {
        this.foliosEncontrados = foliosEncontrados;
    }

    public List<String> getFoliosNoEncontradoM() {
        return foliosNoEncontradoM;
    }

    public void setFoliosNoEncontradoM(List<String> foliosNoEncontradoM) {
        this.foliosNoEncontradoM = foliosNoEncontradoM;
    }

    public List<FolioAmountTruckdriverPojo> getFoliosMNoEncontradosD() {
        return foliosMNoEncontradosD;
    }

    public void setFoliosMNoEncontradosD(List<FolioAmountTruckdriverPojo> foliosMNoEncontradosD) {
        this.foliosMNoEncontradosD = foliosMNoEncontradosD;
    }

    public List<FolioAmountTruckdriverPojo> getFoliosMNoEncontradosM() {
        return foliosMNoEncontradosM;
    }

    public void setFoliosMNoEncontradosM(List<FolioAmountTruckdriverPojo> foliosMNoEncontradosM) {
        this.foliosMNoEncontradosM = foliosMNoEncontradosM;
    }
}
