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
    
    private Integer idBudget;
    private Integer idBudgetCategory;
    private Integer idBudgetSubcategory;
    private Integer idCostCenter;
    private Integer idBudgetType;
    private Integer idBudgetNature;
    private CBudgetCategories budgetCategory;
    private CBudgetSubcategories budgetSubcategory;
    private Integer year;
    private Boolean isAuthorized;
    private BigDecimal granTotal;
    private List<ConceptPojo> conceptos;
    private List<TotalMonthPojo> totalMonth;

    public BudgetPojo() {
    }

    public BudgetPojo(Integer idBudget, Integer idBudgetCategory, Integer idBudgetSubcategory, Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, CBudgetCategories budgetCategory, CBudgetSubcategories budgetSubcategory, Integer year, Boolean isAuthorized) {
        this.idBudget = idBudget;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.idCostCenter = idCostCenter;
        this.idBudgetType = idBudgetType;
        this.idBudgetNature = idBudgetNature;
        this.budgetCategory = budgetCategory;
        this.budgetSubcategory = budgetSubcategory;
        this.year = year;
        this.isAuthorized = isAuthorized;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdBudgetType() {
        return idBudgetType;
    }

    public void setIdBudgetType(Integer idBudgetType) {
        this.idBudgetType = idBudgetType;
    }

    public Integer getIdBudgetNature() {
        return idBudgetNature;
    }

    public void setIdBudgetNature(Integer idBudgetNature) {
        this.idBudgetNature = idBudgetNature;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
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
}
