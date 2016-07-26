package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gerardo8 on 21/07/16.
 */
@Entity
@DynamicUpdate
@Table(name = "C_VOUCHER_TYPES")
public class CVoucherTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VOUCHER_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idVoucherType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VOUCHER_TYPE_NAME")
    @JsonView(JsonViews.Root.class)
    private String voucherTypeName;

    @OneToMany(mappedBy = "voucherType")
    @JsonView(JsonViews.Embedded.class)
    private List<CRefundConceptDocumentsTypes> refundConceptDocumentTypes;

    public CVoucherTypes() {
    }

    public CVoucherTypes(Integer idVoucherType) {
        this.idVoucherType = idVoucherType;
    }

    public CVoucherTypes(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    public Integer getIdVoucherType() {
        return idVoucherType;
    }

    public void setIdVoucherType(Integer idVoucherType) {
        this.idVoucherType = idVoucherType;
    }

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

    public void setVoucherTypeName(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    public List<CRefundConceptDocumentsTypes> getRefundConceptDocumentTypes() {
        return refundConceptDocumentTypes;
    }

    public void setRefundConceptDocumentTypes(List<CRefundConceptDocumentsTypes> refundConceptDocumentTypes) {
        this.refundConceptDocumentTypes = refundConceptDocumentTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CVoucherTypes that = (CVoucherTypes) o;

        if (idVoucherType != null ? !idVoucherType.equals(that.idVoucherType) : that.idVoucherType != null)
            return false;
        return voucherTypeName != null ? voucherTypeName.equals(that.voucherTypeName) : that.voucherTypeName == null;

    }

    @Override
    public int hashCode() {
        int result = idVoucherType != null ? idVoucherType.hashCode() : 0;
        result = 31 * result + (voucherTypeName != null ? voucherTypeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CVoucherTypes{" +
                "idVoucherType=" + idVoucherType +
                ", voucherTypeName='" + voucherTypeName + '\'' +
                '}';
    }
}
