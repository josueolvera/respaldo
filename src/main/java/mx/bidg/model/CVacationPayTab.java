package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Created by PC_YAIR on 14/11/2016.
 */

@Entity
@DynamicUpdate
@Table(name = "C_VACATION_PAY_TAB")

public class CVacationPayTab implements Serializable{
 private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAY_VAC")
    @JsonView(JsonViews.Root.class)
    private Integer idPayVac;

    @Column(name = "START_YEAR" , insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer startYear;

    @Column(name = "END_YEAR",insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer endYear;

    @Column(name = "DAYS",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer days;

    @Column(name = "PERCENTAGE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private BigDecimal percentage;


    public CVacationPayTab() {
    }

    public CVacationPayTab(Integer idPayVac){
        this.idPayVac =idPayVac;
    }

    public Integer getIdPayVac() {return idPayVac;}

    public void setIdPayVac(Integer idPayVac) {this.idPayVac = idPayVac;}

    public Integer getStartYear() {return startYear;}

    public void setStartYear(Integer startYear) {this.startYear = startYear;}

    public Integer getEndYear() {return endYear;}

    public void setEndYear(Integer endYear) {this.endYear = endYear;}

    public Integer getDays() {return days;}

    public void setDays(Integer days) {this.days = days;}

    public BigDecimal getPercentage() {return percentage;}

    public void setPercentage(BigDecimal percentage) {this.percentage = percentage;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CVacationPayTab)) return false;

        CVacationPayTab that = (CVacationPayTab) o;

        return idPayVac.equals(that.idPayVac);

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPayVac != null ? idPayVac.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return "CVacationPayTab{" +
                "idPayVac=" + idPayVac +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", days=" + days +
                ", percentage=" + percentage +
                '}';
    }
}
