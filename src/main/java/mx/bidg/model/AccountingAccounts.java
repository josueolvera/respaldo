package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Rafael
 */
@Entity
@Table(name = "ACCOUNTING_ACCOUNTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AccountingAccounts implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCOUNTING_ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Column(name = "ID_ACCOUNTING_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingType;

    @Column(name = "ID_ACCOUNTING_NATURE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingNature;

    @Column(name = "ID_ACCOUNTING_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingCategory;

    @Column(name = "ID_BUDGET_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetCategory;

    @Column(name = "ID_BUDGET_SUBCATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;

    @Column(name = "ID_BUDGET_SUB_SUBCATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubSubcategories;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Size(max = 30)
    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Size(max = 45)
    @Column(name = "ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String acronyms;

    @JoinColumn(name = "ID_ACCOUNTING_CATEGORY", referencedColumnName = "ID_ACCOUNTING_CATEGORY")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAccountingAccountCategory cAccountingAccountCategory;

    @JoinColumn(name = "ID_ACCOUNTING_NATURE", referencedColumnName = "ID_ACCOUNTING_NATURE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAccountingAccountNature cAccountingAccountNature;

    @JoinColumn(name = "ID_ACCOUNTING_TYPE", referencedColumnName = "ID_ACCOUNTING_TYPE")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAccountingAccountType cAccountingAccountType;

    @JoinColumn(name = "ID_BUDGET_CATEGORY", referencedColumnName = "ID_BUDGET_CATEGORY")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBudgetCategories budgetCategory;

    @JoinColumn(name = "ID_BUDGET_SUBCATEGORY", referencedColumnName = "ID_BUDGET_SUBCATEGORY")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBudgetSubcategories budgetSubcategory;

    @JoinColumn(name = "ID_BUDGET_SUB_SUBCATEGORY", referencedColumnName = "ID_BUDGET_SUB_SUBCATEGORY")
    @ManyToOne
    @JsonView({JsonViews.Embedded.class})
    private CBudgetSubSubcategories cBudgetSubSubcategories;

    public AccountingAccounts() {
    }

    public AccountingAccounts(Integer idAccountingAccount){this.idAccountingAccount=idAccountingAccount;}

    public AccountingAccounts(int idAccountingType, int idAccountingNature, int idAccountingCategory, Integer idBudgetCategory, Integer idBudgetSubcategory, int idBudgetSubSubcategories) {
        this.idAccountingType = idAccountingType;
        this.idAccountingNature = idAccountingNature;
        this.idAccountingCategory = idAccountingCategory;
        this.idBudgetCategory = idBudgetCategory;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.idBudgetSubSubcategories = idBudgetSubSubcategories;
    }

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public int getIdAccountingType() {
        return idAccountingType;
    }

    public void setIdAccountingType(int idAccountingType) {
        this.idAccountingType = idAccountingType;
    }

    public int getIdAccountingNature() {
        return idAccountingNature;
    }

    public void setIdAccountingNature(int idAccountingNature) {
        this.idAccountingNature = idAccountingNature;
    }

    public int getIdAccountingCategory() {
        return idAccountingCategory;
    }

    public void setIdAccountingCategory(int idAccountingCategory) {
        this.idAccountingCategory = idAccountingCategory;
    }

    public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public Integer getIdBudgetSubSubcategories() {
        return idBudgetSubSubcategories;
    }

    public void setIdBudgetSubSubcategories(Integer idBudgetSubSubcategories) {
        this.idBudgetSubSubcategories = idBudgetSubSubcategories;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CAccountingAccountCategory getcAccountingAccountCategory() {
        return cAccountingAccountCategory;
    }

    public void setcAccountingAccountCategory(CAccountingAccountCategory cAccountingAccountCategory) {
        this.cAccountingAccountCategory = cAccountingAccountCategory;
    }

    public CAccountingAccountNature getcAccountingAccountNature() {
        return cAccountingAccountNature;
    }

    public void setcAccountingAccountNature(CAccountingAccountNature cAccountingAccountNature) {
        this.cAccountingAccountNature = cAccountingAccountNature;
    }

    public CAccountingAccountType getcAccountingAccountType() {
        return cAccountingAccountType;
    }

    public void setcAccountingAccountType(CAccountingAccountType cAccountingAccountType) {
        this.cAccountingAccountType = cAccountingAccountType;
    }

    public CBudgetCategories getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(CBudgetCategories budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public CBudgetSubcategories getBudgetSubcategory() {
        return budgetSubcategory;
    }

    public void setBudgetSubcategory(CBudgetSubcategories budgetSubcategory) {
        this.budgetSubcategory = budgetSubcategory;
    }

    public CBudgetSubSubcategories getcBudgetSubSubcategories() {
        return cBudgetSubSubcategories;
    }

    public void setcBudgetSubSubcategories(CBudgetSubSubcategories cBudgetSubSubcategories) {
        this.cBudgetSubSubcategories = cBudgetSubSubcategories;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccountingAccount != null ? idAccountingAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountingAccounts)) {
            return false;
        }
        AccountingAccounts other = (AccountingAccounts) object;
        if ((this.idAccountingAccount == null && other.idAccountingAccount != null) || (this.idAccountingAccount != null && !this.idAccountingAccount.equals(other.idAccountingAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AccountingAccounts[ idAccountingAccount=" + idAccountingAccount + " ]";
    }

}
