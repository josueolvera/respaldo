package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "TRANSACTIONS_PROOFS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class TransactionsProofs implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRANSACTION_PROOF")
    private Integer idTransactionProof;

    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;

    @JoinColumn(name = "ID_PROOF_STATUS", referencedColumnName = "ID_PROOF_STATUS")
    @ManyToOne(optional = false)
    private CProofStatus cProofStatus;

    @JoinColumn(name = "FOLIO", referencedColumnName = "FOLIO")
    @ManyToOne(optional = false)
    private CFolios cFolios;

    public TransactionsProofs() {
    }

    public TransactionsProofs(Integer idTransactionProof) {
        this.idTransactionProof = idTransactionProof;
    }

    public Integer getIdTransactionProof() {
        return idTransactionProof;
    }

    public void setIdTransactionProof(Integer idTransactionProof) {
        this.idTransactionProof = idTransactionProof;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CProofStatus getCProofStatus() {
        return cProofStatus;
    }

    public void setCProofStatus(CProofStatus cProofStatus) {
        this.cProofStatus = cProofStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransactionProof != null ? idTransactionProof.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionsProofs)) {
            return false;
        }
        TransactionsProofs other = (TransactionsProofs) object;
        if ((this.idTransactionProof == null && other.idTransactionProof != null) || (this.idTransactionProof != null && !this.idTransactionProof.equals(other.idTransactionProof))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.TransactionsProofs[ idTransactionProof=" + idTransactionProof + " ]";
    }

    public CFolios getCFolios() {
        return cFolios;
    }

    public void setCFolios(CFolios cFolios) {
        this.cFolios = cFolios;
    }
    
}
