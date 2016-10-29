package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by josue on 11/10/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "C_COMMISSION_BONUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CCommissionBonus implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final CCommissionBonus BONO_POR_CUMPLIMIENTO = new CCommissionBonus(1);
    public static final CCommissionBonus BONO_POR_NUEVO_INGRESO  = new CCommissionBonus(2);
    public static final CCommissionBonus APOYO_PASAJE  = new CCommissionBonus(3);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMMISSION_BONUS")
    @JsonView(JsonViews.Root.class)
    private Integer idCommissionBonus;

    @Size(max = 50)
    @Column(name = "TYPE_BONUS")
    @JsonView(JsonViews.Root.class)
    private String typeBonus;

    public CCommissionBonus() {

    }

    private CCommissionBonus(Integer idCommissionBonus){
        this.idCommissionBonus = idCommissionBonus;
    }

    public Integer getIdCommissionBonus() {
        return idCommissionBonus;
    }

    public void setIdCommissionBonus(Integer idCommissionBonus) {
        idCommissionBonus = idCommissionBonus;
    }

    public String getTypeBonus() {
        return typeBonus;
    }

    public void setTypeBonus(String typeBonus) {
        this.typeBonus = typeBonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CCommissionBonus that = (CCommissionBonus) o;

        if (idCommissionBonus != null ? !idCommissionBonus.equals(that.idCommissionBonus) : that.idCommissionBonus != null)
            return false;
        return typeBonus != null ? typeBonus.equals(that.typeBonus) : that.typeBonus == null;

    }

    @Override
    public int hashCode() {
        int result = idCommissionBonus != null ? idCommissionBonus.hashCode() : 0;
        result = 31 * result + (typeBonus != null ? typeBonus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CCommissionBonus{" +
                "IdCommissionBonus=" + idCommissionBonus +
                ", typeBonus='" + typeBonus + '\'' +
                '}';
    }
}
