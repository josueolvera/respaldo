package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "ROLE_COST_CENTER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class RolesCostCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROLE_COST_CENTER")
    @JsonView(JsonViews.Root.class)
    private Integer idRoleCostCenter;

    @Column(name = "ID_COST_CENTER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCostCenter;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @Column(name = "ID_BUDGET_NATURE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetNature;

    @ManyToOne
    @JoinColumn(name = "ID_COST_CENTER", referencedColumnName = "ID_COST_CENTER")
    @JsonView(JsonViews.Embedded.class)
    private CCostCenter costCenter;

    @ManyToOne
    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @JsonView(JsonViews.Embedded.class)
    private CRoles role;

    @ManyToOne
    @JoinColumn(name = "ID_BUDGET_NATURE", referencedColumnName = "ID_BUDGET_NATURE")
    @JsonView(JsonViews.Embedded.class)
    private CBudgetNature budgetNature;

    @Column(name = "IS_BUDGET", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean isBudget;

    @Column(name = "IS_REQUEST", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean isRequest;

    public RolesCostCenter() {
    }

    public RolesCostCenter(CCostCenter costCenter, CRoles role, Boolean isBudget, Boolean isRequest) {
        this.costCenter = costCenter;
        this.role = role;
        this.isBudget = isBudget;
        this.isRequest = isRequest;
    }

    public Integer getIdRoleCostCenter() {
        return idRoleCostCenter;
    }

    public void setIdRoleCostCenter(Integer idRoleCostCenter) {
        this.idRoleCostCenter = idRoleCostCenter;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getIdBudgetNature() {
        return idBudgetNature;
    }

    public void setIdBudgetNature(Integer idBudgetNature) {
        this.idBudgetNature = idBudgetNature;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CCostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public CRoles getRole() {
        return role;
    }

    public void setRole(CRoles role) {
        this.role = role;
    }

    public CBudgetNature getBudgetNature() {
        return budgetNature;
    }

    public void setBudgetNature(CBudgetNature budgetNature) {
        this.budgetNature = budgetNature;
    }

    public Boolean getBudget() {
        return isBudget;
    }

    public void setBudget(Boolean budget) {
        isBudget = budget;
    }

    public Boolean getRequest() {
        return isRequest;
    }

    public void setRequest(Boolean request) {
        isRequest = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesCostCenter that = (RolesCostCenter) o;

        if (idCostCenter != null ? !idCostCenter.equals(that.idCostCenter) : that.idCostCenter != null) return false;
        if (idRole != null ? !idRole.equals(that.idRole) : that.idRole != null) return false;
        if (isBudget != null ? !isBudget.equals(that.isBudget) : that.isBudget != null) return false;
        return isRequest != null ? isRequest.equals(that.isRequest) : that.isRequest == null;

    }

    @Override
    public int hashCode() {
        int result = idCostCenter != null ? idCostCenter.hashCode() : 0;
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
        result = 31 * result + (isBudget != null ? isBudget.hashCode() : 0);
        result = 31 * result + (isRequest != null ? isRequest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RolesCostCenter{" +
                "idRoleCostCenter=" + idRoleCostCenter +
                ", idCostCenter=" + idCostCenter +
                ", idRole=" + idRole +
                ", idBudgetNature=" + idBudgetNature +
                ", costCenter=" + costCenter +
                ", role=" + role +
                ", budgetNature=" + budgetNature +
                ", isBudget=" + isBudget +
                ", isRequest=" + isRequest +
                '}';
    }
}
