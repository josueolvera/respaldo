package mx.bidg.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "C_PROOF_STATUS")
public class CProofStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROOF_STATUS")
    private Integer idProofStatus;

    @Size(max = 100)
    @Column(name = "PROOF_STATUS")
    private String proofStatus;

    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;

    public CProofStatus() {
    }

    public CProofStatus(Integer idProofStatus) {
        this.idProofStatus = idProofStatus;
    }

    public Integer getIdProofStatus() {
        return idProofStatus;
    }

    public void setIdProofStatus(Integer idProofStatus) {
        this.idProofStatus = idProofStatus;
    }

    public String getProofStatus() {
        return proofStatus;
    }

    public void setProofStatus(String proofStatus) {
        this.proofStatus = proofStatus;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProofStatus != null ? idProofStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CProofStatus)) {
            return false;
        }
        CProofStatus other = (CProofStatus) object;
        if ((this.idProofStatus == null && other.idProofStatus != null) || (this.idProofStatus != null && !this.idProofStatus.equals(other.idProofStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CProofStatus[ idProofStatus=" + idProofStatus + " ]";
    }
    
}
