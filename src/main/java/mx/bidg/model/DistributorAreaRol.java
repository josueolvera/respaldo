package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gerardo8 on 15/08/16.
 */
@Entity
@DynamicUpdate
@Table(name = "DISTRIBUTOR_AREA_ROL")
public class DistributorAreaRol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DISTRIBUTOR_AREA_ROL")
    @JsonView(JsonViews.Root.class)
    private Integer idDistributorAreaRol;

    @Column(name = "ID_DISTRIBUTOR", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDistributor;

    @Column(name = "ID_AREA", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idArea;

    @Column(name = "ID_ROLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRole;

    @Column(name = "ID_SUBAREA", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idSubarea;

    @Column(name = "LEVEL_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer levelRequest;

    @Column(name = "AMOUNT_REQUEST")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountRequest;

    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CDistributors distributor;

    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CAreas area;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRoles role;

    @JoinColumn(name = "ID_SUBAREA", referencedColumnName = "ID_SUBAREA")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private CSubareas cSubareas;

    public DistributorAreaRol() {
    }

    public DistributorAreaRol(Integer idDistributorAreaRol) {
        this.idDistributorAreaRol = idDistributorAreaRol;
    }

    public DistributorAreaRol(Integer idDistributor, Integer idArea, Integer idRole, CDistributors distributor, CAreas area, CRoles role) {
        this.idDistributor = idDistributor;
        this.idArea = idArea;
        this.idRole = idRole;
        this.distributor = distributor;
        this.area = area;
        this.role = role;
    }

    public Integer getIdDistributorAreaRol() {
        return idDistributorAreaRol;
    }

    public void setIdDistributorAreaRol(Integer idDistributorAreaRol) {
        this.idDistributorAreaRol = idDistributorAreaRol;
    }

    public Integer getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(Integer idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public CDistributors getDistributor() {
        return distributor;
    }

    public void setDistributor(CDistributors distributor) {
        this.distributor = distributor;
    }

    public CAreas getArea() {
        return area;
    }

    public void setArea(CAreas area) {
        this.area = area;
    }

    public CRoles getRole() {
        return role;
    }

    public void setRole(CRoles role) {
        this.role = role;
    }

    public Integer getIdSubarea() {
        return idSubarea;
    }

    public void setIdSubarea(Integer idSubarea) {
        this.idSubarea = idSubarea;
    }

    public CSubareas getcSubareas() {
        return cSubareas;
    }

    public void setcSubareas(CSubareas cSubareas) {
        this.cSubareas = cSubareas;
    }

    public Integer getLevelRequest() {
        return levelRequest;
    }

    public void setLevelRequest(Integer levelRequest) {
        this.levelRequest = levelRequest;
    }

    public BigDecimal getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(BigDecimal amountRequest) {
        this.amountRequest = amountRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistributorAreaRol that = (DistributorAreaRol) o;

        if (idDistributorAreaRol != null ? !idDistributorAreaRol.equals(that.idDistributorAreaRol) : that.idDistributorAreaRol != null)
            return false;
        if (idDistributor != null ? !idDistributor.equals(that.idDistributor) : that.idDistributor != null)
            return false;
        if (idArea != null ? !idArea.equals(that.idArea) : that.idArea != null) return false;
        if (idRole != null ? !idRole.equals(that.idRole) : that.idRole != null) return false;
        if (distributor != null ? !distributor.equals(that.distributor) : that.distributor != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public int hashCode() {
        int result = idDistributorAreaRol != null ? idDistributorAreaRol.hashCode() : 0;
        result = 31 * result + (idDistributor != null ? idDistributor.hashCode() : 0);
        result = 31 * result + (idArea != null ? idArea.hashCode() : 0);
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
        result = 31 * result + (distributor != null ? distributor.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DistributorAreaRol{" +
                "idDistributorAreaRol=" + idDistributorAreaRol +
                ", idDistributor=" + idDistributor +
                ", idArea=" + idArea +
                ", idRole=" + idRole +
                ", distributor=" + distributor +
                ", area=" + area +
                ", role=" + role +
                '}';
    }
}
