package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by gerardo8 on 30/06/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_ACTION_TYPES")

public class CActionTypes {

    private static final long serialVersionUID = 1L;

    public static final CActionTypes ALTA = new CActionTypes(1);
    public static final CActionTypes BAJA = new CActionTypes(2);
    public static final CActionTypes MODIFICAION = new CActionTypes(3);
    public static final CActionTypes REACTIVACION = new CActionTypes(4);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACTION_TYPE")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private Integer idActionType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ACTION_TYPE")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private String actionType;

    public CActionTypes() {}

    public CActionTypes(Integer idActionType) {
        this.idActionType = idActionType;
    }

    public CActionTypes(String actionType) {
        this.actionType = actionType;
    }

    public Integer getIdActionType() {
        return idActionType;
    }

    public void setIdActionType(Integer idActionType) {
        this.idActionType = idActionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CActionTypes that = (CActionTypes) o;

        if (idActionType != null ? !idActionType.equals(that.idActionType) : that.idActionType != null) return false;
        return actionType != null ? actionType.equals(that.actionType) : that.actionType == null;

    }

    @Override
    public int hashCode() {
        int result = idActionType != null ? idActionType.hashCode() : 0;
        result = 31 * result + (actionType != null ? actionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CActionTypes{" +
                "idActionType=" + idActionType +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}
