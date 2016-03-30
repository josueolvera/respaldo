package mx.bidg.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_AUTHORIZATION_TYPES")
public class CAuthorizationTypes implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final CAuthorizationTypes SOLICITUD = new CAuthorizationTypes(1);
    public static final CAuthorizationTypes COTIZACION = new CAuthorizationTypes(2);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUTHORIZATION_TYPE")
    private Integer idAuthorizationType;

    @Size(max = 50)
    @Column(name = "AUTHORIZATION_TYPE")
    private String authorizationType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;

    public CAuthorizationTypes() {
    }

    public CAuthorizationTypes(Integer idAuthorizationType) {
        this.idAuthorizationType = idAuthorizationType;
    }

    public CAuthorizationTypes(Integer idAuthorizationType, int idAccessLevel) {
        this.idAuthorizationType = idAuthorizationType;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdAuthorizationType() {
        return idAuthorizationType;
    }

    public void setIdAuthorizationType(Integer idAuthorizationType) {
        this.idAuthorizationType = idAuthorizationType;
    }

    public String getAuthorizationType() {
        return authorizationType;
    }

    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthorizationType != null ? idAuthorizationType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAuthorizationTypes)) {
            return false;
        }
        CAuthorizationTypes other = (CAuthorizationTypes) object;
        if ((this.idAuthorizationType == null && other.idAuthorizationType != null) || (this.idAuthorizationType != null && !this.idAuthorizationType.equals(other.idAuthorizationType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAuthorizationTypes[ idAuthorizationType=" + idAuthorizationType + " ]";
    }
    
}
