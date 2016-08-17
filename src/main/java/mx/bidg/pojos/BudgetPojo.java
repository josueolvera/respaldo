/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.pojos;

import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author sistemask
 */
public class BudgetPojo {
    
    private int idBudget;
    private int idDistributor;
    private int idArea;
    private int idBudgetCategory;
    private int idBudgetSubcategory;
    private CBudgetCategories budgetCategory;
    private CBudgetSubcategories budgetSubcategory;
    private int year;
    private int isAuthorized;
    private BigDecimal granTotal;
    private List<ConceptPojo> conceptos;
    private List<TotalMonthPojo> totalMonth;

    public BudgetPojo() {
    }

    public BudgetPojo(int idBudget, int idDistributor, int idArea, int idBudgetCategory, int idBudgetSubcategory, int year, int isAuthorized, BigDecimal granTotal, List<ConceptPojo> conceptos, List<TotalMonthPojo> totalMonth) {
        this.idBudget = idBudget;
        this.idDistributor = idDistributor;
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

    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
        this.idDistributor = idDistributor;
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

    public CBudgetCategories getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(CBudgetCategories budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public CBudgetSubcategories getBudgetSubcategory() {
        return budgetSubcategory;
    }

    public void setBudgetSubcategory(CBudgetSubcategories budgetSubcategory) {
        this.budgetSubcategory = budgetSubcategory;
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
