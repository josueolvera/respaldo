package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by josue on 11/10/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "BONUS_COMMISSIONABLE_EMPLOYEE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BonusCommisionableEmployee implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BONUS_COMMISSIONABLE_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idBonusCommissionableEmployee;

    @Column(name = "ID_EMPLOYEE")
    @JsonView(JsonViews.Root.class)
    private Integer idEmployee;

    @Column(name = "BONUS_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal bonusAmount;

    @Column(name = "ID_COMMISSION_BONUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCommissionBonus;

    @JoinColumn(name = "ID_COMMISSION_BONUS", referencedColumnName = "ID_COMMISSION_BONUS")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CCommissionBonus cCommissionBonus;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private Employees employees;

    public BonusCommisionableEmployee() {
    }

    public BonusCommisionableEmployee(Integer idBonusCommissionableEmployee) {
        this.idBonusCommissionableEmployee = idBonusCommissionableEmployee;
    }

    public Integer getIdBonusCommissionableEmployee() {
        return idBonusCommissionableEmployee;
    }

    public void setIdBonusCommissionableEmployee(Integer idBonusCommissionableEmployee) {
        this.idBonusCommissionableEmployee = idBonusCommissionableEmployee;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public BigDecimal getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(BigDecimal bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Integer getIdCommissionBonus() {
        return idCommissionBonus;
    }

    public void setIdCommissionBonus(Integer idCommissionBonus) {
        this.idCommissionBonus = idCommissionBonus;
    }

    public CCommissionBonus getcCommissionBonus() {
        return cCommissionBonus;
    }

    public void setcCommissionBonus(CCommissionBonus cCommissionBonus) {
        this.cCommissionBonus = cCommissionBonus;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BonusCommisionableEmployee that = (BonusCommisionableEmployee) o;

        if (idBonusCommissionableEmployee != null ? !idBonusCommissionableEmployee.equals(that.idBonusCommissionableEmployee) : that.idBonusCommissionableEmployee != null)
            return false;
        if (idEmployee != null ? !idEmployee.equals(that.idEmployee) : that.idEmployee != null) return false;
        if (bonusAmount != null ? !bonusAmount.equals(that.bonusAmount) : that.bonusAmount != null) return false;
        if (idCommissionBonus != null ? !idCommissionBonus.equals(that.idCommissionBonus) : that.idCommissionBonus != null)
            return false;
        return cCommissionBonus != null ? cCommissionBonus.equals(that.cCommissionBonus) : that.cCommissionBonus == null;

    }

    @Override
    public int hashCode() {
        int result = idBonusCommissionableEmployee != null ? idBonusCommissionableEmployee.hashCode() : 0;
        result = 31 * result + (idEmployee != null ? idEmployee.hashCode() : 0);
        result = 31 * result + (bonusAmount != null ? bonusAmount.hashCode() : 0);
        result = 31 * result + (idCommissionBonus != null ? idCommissionBonus.hashCode() : 0);
        result = 31 * result + (cCommissionBonus != null ? cCommissionBonus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BonusCommisionableEmployee{" +
                "idBonusCommissionableEmployee=" + idBonusCommissionableEmployee +
                ", idEmployee=" + idEmployee +
                ", bonusAmount=" + bonusAmount +
                ", idCommissionBonus=" + idCommissionBonus +
                ", cCommissionBonus=" + cCommissionBonus +
                '}';
    }
}
