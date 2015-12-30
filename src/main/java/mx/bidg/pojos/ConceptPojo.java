/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.pojos;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author sistemask
 */
public class ConceptPojo {
    
    private int idConcept;
    private int idBudget;
    private int dwEnterprise;
    private int year;
    private String conceptName;
    private BigDecimal total;
    private boolean equals;
    private List<ConceptMonthPojo> conceptMonth;

    public ConceptPojo() {
    }

    public ConceptPojo(int idConcept, int idBudget, int dwEnterprise, int year, String conceptName, boolean equals, List<ConceptMonthPojo> conceptMonth) {
        this.idConcept = idConcept;
        this.idBudget = idBudget;
        this.dwEnterprise = dwEnterprise;
        this.year = year;
        this.conceptName = conceptName;
        this.equals = equals;
        this.conceptMonth = conceptMonth;
    }

    public int getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(int idConcept) {
        this.idConcept = idConcept;
    }

    public int getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(int idBudget) {
        this.idBudget = idBudget;
    }

    public int getDwEnterprise() {
        return dwEnterprise;
    }

    public void setDwEnterprise(int dwEnterprise) {
        this.dwEnterprise = dwEnterprise;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isEquals() {
        return equals;
    }

    public void setEquals(boolean equals) {
        this.equals = equals;
    }

    public List<ConceptMonthPojo> getConceptMonth() {
        return conceptMonth;
    }

    public void setConceptMonth(List<ConceptMonthPojo> conceptMonth) {
        this.conceptMonth = conceptMonth;
    }
    
}
