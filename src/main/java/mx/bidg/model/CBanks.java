package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_BANKS")

public class CBanks implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BANK")
    @JsonView(JsonViews.Root.class)
    private Integer idBank;
    
    @Size(max = 45)
    @Column(name = "CLAVE")
    @JsonView(JsonViews.Root.class)
    private String clave;
    
    @Size(max = 100)
    @Column(name = "BANK_NAME")
    @JsonView(JsonViews.Root.class)
    private String bankName;
    
    @Size(max = 45)
    @Column(name = "ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String acronyms;
    
    @Size(max = 45)
    @Column(name = "WEB_PAGE")
    @JsonView(JsonViews.Root.class)
    private String webPage;
    
    @Column(name = "DEFAULT_BANK")
    @JsonView(JsonViews.Root.class)
    private Integer defaultBank;

    public CBanks() {
    }

    public CBanks(Integer idBank) {
        this.idBank = idBank;
    }

    public Integer getIdBank() {
        return idBank;
    }

    public void setIdBank(Integer idBank) {
        this.idBank = idBank;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public Integer getDefaultBank() {
        return defaultBank;
    }

    public void setDefaultBank(Integer defaultBank) {
        this.defaultBank = defaultBank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CBanks cBanks = (CBanks) o;

        return idBank.equals(cBanks.idBank);

    }

    @Override
    public int hashCode() {
        return idBank.hashCode();
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBanks[ idBank=" + idBank + " ]";
    }

 }
