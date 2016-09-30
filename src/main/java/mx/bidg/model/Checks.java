package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 22/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "CHECKS")
public class Checks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CHECK")
    @JsonView(JsonViews.Root.class)
    private Integer idCheck;

    @Column(name = "ID_TRAVEL_EXPENSE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTravelExpense;

    @OneToOne()
    @JoinColumn(name="ID_TRAVEL_EXPENSE")
    @JsonView(JsonViews.Embedded.class)
    private TravelExpenses travelExpense;

    @Column(name = "AUTHORIZED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal authorizedAmount;

    @Column(name = "CHECKED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal checkedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRED_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime expiredDate;

    @Column(name = "ID_CHECK_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCheckStatus;

    @JoinColumn(name = "ID_CHECK_STATUS", referencedColumnName = "ID_CHECK_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCheckStatus checkStatus;

    @OneToMany(mappedBy = "check")
    @JsonView(JsonViews.Embedded.class)
    private List<ChecksBills> checksBills;

    public Checks() {
    }

    public Checks(TravelExpenses travelExpense, LocalDateTime expiredDate) {
        this.travelExpense = travelExpense;
        this.expiredDate = expiredDate;
        this.checkStatus = CCheckStatus.PENDIENTE;
    }

    public Integer getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(Integer idCheck) {
        this.idCheck = idCheck;
    }

    public Integer getIdTravelExpense() {
        return idTravelExpense;
    }

    public void setIdTravelExpense(Integer idTravelExpense) {
        this.idTravelExpense = idTravelExpense;
    }

    public TravelExpenses getTravelExpense() {
        return travelExpense;
    }

    public void setTravelExpense(TravelExpenses travelExpense) {
        this.travelExpense = travelExpense;
    }

    public BigDecimal getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(BigDecimal authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public BigDecimal getCheckedAmount() {
        return checkedAmount;
    }

    public void setCheckedAmount(BigDecimal checkedAmount) {
        this.checkedAmount = checkedAmount;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public DateFormatsPojo getExpiredDateFormats() {
        return (expiredDate == null) ? null : new DateFormatsPojo(expiredDate);
    }

    public Integer getIdCheckStatus() {
        return idCheckStatus;
    }

    public void setIdCheckStatus(Integer idCheckStatus) {
        this.idCheckStatus = idCheckStatus;
    }

    public CCheckStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(CCheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public List<ChecksBills> getChecksBills() {
        return checksBills;
    }

    public void setChecksBills(List<ChecksBills> checksBills) {
        this.checksBills = checksBills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Checks checks = (Checks) o;

        return idCheck != null ? idCheck.equals(checks.idCheck) : checks.idCheck == null;

    }

    @Override
    public int hashCode() {
        return idCheck != null ? idCheck.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Checks{" +
                "idCheck=" + idCheck +
                ", idTravelExpense=" + idTravelExpense +
                ", travelExpense=" + travelExpense +
                ", checksBills=" + checksBills +
                '}';
    }
}
