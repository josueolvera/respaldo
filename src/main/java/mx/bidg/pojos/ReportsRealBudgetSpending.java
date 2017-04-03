package mx.bidg.pojos;

import mx.bidg.model.RealBudgetSpending;

import java.util.List;

/**
 * Created by Kevin Salvador on 03/04/2017.
 */
public class ReportsRealBudgetSpending {
    private List<RealBudgetSpending> currentYear;
    private List<RealBudgetSpending> lastYear;

    public List<RealBudgetSpending> getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(List<RealBudgetSpending> currentYear) {
        this.currentYear = currentYear;
    }

    public List<RealBudgetSpending> getLastYear() {
        return lastYear;
    }

    public void setLastYear(List<RealBudgetSpending> lastYear) {
        this.lastYear = lastYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportsRealBudgetSpending)) return false;

        ReportsRealBudgetSpending that = (ReportsRealBudgetSpending) o;

        if (getCurrentYear() != null ? !getCurrentYear().equals(that.getCurrentYear()) : that.getCurrentYear() != null)
            return false;
        return getLastYear() != null ? getLastYear().equals(that.getLastYear()) : that.getLastYear() == null;
    }

    @Override
    public int hashCode() {
        int result = getCurrentYear() != null ? getCurrentYear().hashCode() : 0;
        result = 31 * result + (getLastYear() != null ? getLastYear().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportsRealBudgetSpending{" +
                "currentYear=" + currentYear +
                ", lastYear=" + lastYear +
                '}';
    }
}
