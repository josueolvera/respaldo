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
    private BigDecimal granTotal;
    private List<ConceptPojo> concepts;
    private List<TotalMonthPojo> totalMonths;

    public BudgetPojo() {
    }

    public BudgetPojo(int idBudget, int idGroup, int idArea, int idBudgetCategory, int idBudgetSubcategory, List<ConceptPojo> concepts, List<TotalMonthPojo> totalMonths) {
        this.idBudget = idBudget;
        this.idGroup = idGroup;
        this.idArea = idArea;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.concepts = concepts;
        this.totalMonths = totalMonths;
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

    public List<ConceptPojo> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<ConceptPojo> concepts) {
        this.concepts = concepts;
    }

    public List<TotalMonthPojo> getTotalMonths() {
        return totalMonths;
    }

    public void setTotalMonths(List<TotalMonthPojo> totalMonths) {
        this.totalMonths = totalMonths;
    }
    
}
