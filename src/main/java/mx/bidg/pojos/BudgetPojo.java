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
public class BudgetPojo {
    
    private int idBudget;
    private int idGroup;
    private int idArea;
    private int idBudgetCategory;
    private int idBudgetSubcategory;
    private int year;
    private int isAuthorized;
    private BigDecimal granTotal;
    private List<ConceptPojo> conceptos;
    private List<TotalMonthPojo> totalMonth;

    public BudgetPojo() {
    }

    public BudgetPojo(int idBudget, int idGroup, int idArea, int idBudgetCategory, int idBudgetSubcategory, int year, int isAuthorized, BigDecimal granTotal, List<ConceptPojo> conceptos, List<TotalMonthPojo> totalMonth) {
        this.idBudget = idBudget;
        this.idGroup = idGroup;
        this.idArea = idArea;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.year = year;
        this.isAuthorized = isAuthorized;
        this.granTotal = granTotal;
        this.conceptos = conceptos;
        this.totalMonth = totalMonth;
    }

    public int getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(int idBudget) {
        this.idBudget = idBudget;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(int idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public int getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(int idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public BigDecimal getGranTotal() {
        return granTotal;
    }

    public void setGranTotal(BigDecimal granTotal) {
        this.granTotal = granTotal;
    }

    public List<ConceptPojo> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ConceptPojo> conceptos) {
        this.conceptos = conceptos;
    }

    public List<TotalMonthPojo> getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(List<TotalMonthPojo> totalMonth) {
        this.totalMonth = totalMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(int isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
    
}
