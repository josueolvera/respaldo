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

    @ManyToOne
    @JoinColumn(name = "ID_COST_CENTER", referencedColumnName = "ID_COST_CENTER")
    @JsonView(JsonViews.Embedded.class)
    private CCostCenter costCenter;

    @ManyToOne
    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @JsonView(JsonViews.Embedded.class)
    private CRoles role;

    public RolesCostCenter() {
    }

    public RolesCostCenter(CCostCenter costCenter, CRoles role, Boolean isBudget, Boolean isRequest) {
        this.costCenter = costCenter;
        this.role = role;
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

    @Override
    public String toString() {
        return "RolesCostCenter{" +
                "idRoleCostCenter=" + idRoleCostCenter +
                ", idCostCenter=" + idCostCenter +
                ", idRole=" + idRole +
                ", costCenter=" + costCenter +
                ", role=" + role +
                '}';
    }
}
