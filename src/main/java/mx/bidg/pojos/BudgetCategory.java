package mx.bidg.pojos;

import mx.bidg.model.RealBudgetSpending;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetCategory {
    //private Integer idBudgetCategory;
    //private String name;
    //private String bussinessLine;
    private List<BudgetSubcategory> secondLevel;
    private List<RealBudgetSpending>levelOne;

    public BudgetCategory() {
    }

    /*public BudgetCategory(Integer idBudgetCategory, String name) {
        this.idBudgetCategory = idBudgetCategory;
        this.name = name;
    }*/

    /*public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBussinessLine() {
        return bussinessLine;
    }

    public void setBussinessLine(String bussinessLine) {
        this.bussinessLine = bussinessLine;
    }*/

    public List<BudgetSubcategory> getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(List<BudgetSubcategory> secondLevel) {
        this.secondLevel = secondLevel;
    }

    public List<RealBudgetSpending> getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(List<RealBudgetSpending> levelOne) {
        this.levelOne = levelOne;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetCategory that = (BudgetCategory) o;

        return idBudgetCategory != null ? idBudgetCategory.equals(that.idBudgetCategory) : that.idBudgetCategory == null;

    }

    @Override
    public int hashCode() {
        return idBudgetCategory != null ? idBudgetCategory.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BudgetCategory{" +
                "idBudgetCategory=" + idBudgetCategory +
                ", name='" + name + '\'' +
                '}';
    }*/
}
