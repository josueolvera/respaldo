package mx.bidg.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollador on 27/03/2017.
 */
public class BussinessLine {
    private Integer idBussinessLine;
    private String name;
    private boolean show;
    private BigDecimal januaryAmount;
    private BigDecimal februaryAmount;
    private BigDecimal marchAmount;
    private BigDecimal aprilAmount;
    private BigDecimal mayAmount;
    private BigDecimal juneAmount;
    private BigDecimal julyAmount;
    private BigDecimal augustAmount;
    private BigDecimal septemberAmount;
    private BigDecimal octoberAmount;
    private BigDecimal novemberAmount;
    private BigDecimal decemberAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalBudgetAmountLastYear;
    private BigDecimal realTotalBudgetAmount;
    private List<Distributor> distributorList;

    public BussinessLine() {
        this.show = false;
        this.distributorList = new ArrayList<>();
    }

    public BussinessLine(Integer idBussinessLine, String name) {
        this.idBussinessLine = idBussinessLine;
        this.name = name;
    }

    public Integer getIdBussinessLine() {
        return idBussinessLine;
    }

    public void setIdBussinessLine(Integer idBussinessLine) {
        this.idBussinessLine = idBussinessLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getJanuaryAmount() {
        double zero = 0;

        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getJanuaryAmount().doubleValue();
            }
        }

        this.januaryAmount = new BigDecimal(zero);
        return januaryAmount;
    }

    public void setJanuaryAmount(BigDecimal januaryAmount) {
        this.januaryAmount = januaryAmount;
    }

    public BigDecimal getFebruaryAmount() {
        double zero = 0;

        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getFebruaryAmount().doubleValue();
            }
        }

        this.februaryAmount = new BigDecimal(zero);
        return februaryAmount;
    }

    public void setFebruaryAmount(BigDecimal februaryAmount) {
        this.februaryAmount = februaryAmount;
    }

    public BigDecimal getMarchAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getMarchAmount().doubleValue();
            }
        }
        this.marchAmount = new BigDecimal(zero);
        return marchAmount;
    }

    public void setMarchAmount(BigDecimal marchAmount) {
        this.marchAmount = marchAmount;
    }

    public BigDecimal getAprilAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getAprilAmount().doubleValue();
            }
        }
        this.aprilAmount = new BigDecimal(zero);
        return aprilAmount;
    }

    public void setAprilAmount(BigDecimal aprilAmount) {
        this.aprilAmount = aprilAmount;
    }

    public BigDecimal getMayAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getMayAmount().doubleValue();
            }
        }
        this.mayAmount = new BigDecimal(zero);
        return mayAmount;
    }

    public void setMayAmount(BigDecimal mayAmount) {
        this.mayAmount = mayAmount;
    }

    public BigDecimal getJuneAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getJuneAmount().doubleValue();
            }
        }
        this.juneAmount = new BigDecimal(zero);
        return juneAmount;
    }

    public void setJuneAmount(BigDecimal juneAmount) {
        this.juneAmount = juneAmount;
    }

    public BigDecimal getJulyAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getJulyAmount().doubleValue();
            }
        }
        this.julyAmount = new BigDecimal(zero);
        return julyAmount;
    }

    public void setJulyAmount(BigDecimal julyAmount) {
        this.julyAmount = julyAmount;
    }

    public BigDecimal getAugustAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getAugustAmount().doubleValue();
            }
        }
        this.augustAmount = new BigDecimal(zero);
        return augustAmount;
    }

    public void setAugustAmount(BigDecimal augustAmount) {
        this.augustAmount = augustAmount;
    }

    public BigDecimal getSeptemberAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getSeptemberAmount().doubleValue();
            }
        }
        this.septemberAmount = new BigDecimal(zero);
        return septemberAmount;
    }

    public void setSeptemberAmount(BigDecimal septemberAmount) {
        this.septemberAmount = septemberAmount;
    }

    public BigDecimal getOctoberAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getOctoberAmount().doubleValue();
            }
        }
        this.octoberAmount = new BigDecimal(zero);
        return octoberAmount;
    }

    public void setOctoberAmount(BigDecimal octoberAmount) {
        this.octoberAmount = octoberAmount;
    }

    public BigDecimal getNovemberAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getNovemberAmount().doubleValue();
            }
        }
        this.novemberAmount = new BigDecimal(zero);
        return novemberAmount;
    }

    public void setNovemberAmount(BigDecimal novemberAmount) {
        this.novemberAmount = novemberAmount;
    }

    public BigDecimal getDecemberAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getDecemberAmount().doubleValue();
            }
        }
        this.decemberAmount = new BigDecimal(zero);
        return decemberAmount;
    }

    public void setDecemberAmount(BigDecimal decemberAmount) {
        this.decemberAmount = decemberAmount;
    }

    public BigDecimal getTotalAmount() {
        double zero = 0;
        if (!getDistributorList().isEmpty()) {
            for (Distributor distributor : getDistributorList()) {
                zero += distributor.getTotalAmount().doubleValue();
            }
        }
        this.totalAmount = new BigDecimal(zero);
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalBudgetAmountLastYear() {
        double zero = 0;

        if (!getDistributorList().isEmpty()){
            for(Distributor distributor : getDistributorList()){
                zero += distributor.getTotalBudgetAmountLastYear().doubleValue();
            }
        }
        this.totalBudgetAmountLastYear = new BigDecimal(zero);
        return totalBudgetAmountLastYear;
    }

    public void setTotalBudgetAmountLastYear(BigDecimal totalBudgetAmountLastYear) {
        this.totalBudgetAmountLastYear = totalBudgetAmountLastYear;
    }


    public List<Distributor> getDistributorList() {
        return distributorList;
    }

    public void setDistributorList(List<Distributor> distributorList) {
        this.distributorList = distributorList;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public BigDecimal getRealTotalBudgetAmount() {
        double zero = 0;

        if (!getDistributorList().isEmpty()){
            for (Distributor distributor : getDistributorList()){
                if (distributor != null) {
                    if (distributor.getRealTotalBudgetAmount() != null){
                        zero += distributor.getRealTotalBudgetAmount().doubleValue();
                    }
                }
            }
        }

        this.realTotalBudgetAmount = new BigDecimal(zero);
        return realTotalBudgetAmount;
    }

    public void setRealTotalBudgetAmount(BigDecimal realTotalBudgetAmount) {
        this.realTotalBudgetAmount = realTotalBudgetAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BussinessLine)) return false;

        BussinessLine that = (BussinessLine) o;

        return idBussinessLine != null ? idBussinessLine.equals(that.idBussinessLine) : that.idBussinessLine == null;

    }

    @Override
    public int hashCode() {
        return idBussinessLine != null ? idBussinessLine.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BussinessLine{" +
                "idBussinessLine=" + idBussinessLine +
                ", name='" + name + '\'' +
                ", distributorList=" + distributorList +
                '}';
    }
}
