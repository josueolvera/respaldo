package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
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
    @Basic(optional = false)
    @Column(name = "ID_ACCOUNTING_ACCOUNT")
    @JsonView(JsonViews.Root.class)
    private Integer idAccountingAccount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FIRST_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int firstLevel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SECOND_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int secondLevel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "THIRD_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int thirdLevel;

    @Column(name = "ID_ACCOUNTING_TYPE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingType;

    @Column(name = "ID_ACCOUNTING_NATURE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingNature;

    @Column(name = "ID_ACCOUNTING_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idAccountingCategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AVAILABLE")
    @JsonView(JsonViews.Root.class)
    private boolean available;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;

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
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class})
    private CBudgetSubcategories budgetSubcategory;

    @Column(name = "ID_BUDGET_CATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetCategory;

    @Column(name = "ID_BUDGET_SUBCATEGORY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetSubcategory;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_OF_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer isOfRequest;
    
    public AccountingAccounts() {
    }

    public AccountingAccounts(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public AccountingAccounts(Integer idAccountingAccount, int firstLevel, int secondLevel, int thirdLevel, int idAccountingType, int idAccountingNature, int idAccountingCategory, boolean available, String description) {
        this.idAccountingAccount = idAccountingAccount;
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
        this.thirdLevel = thirdLevel;
        this.idAccountingType = idAccountingType;
        this.idAccountingNature = idAccountingNature;
        this.idAccountingCategory = idAccountingCategory;
        this.available = available;
        this.description = description;
    }

    public Integer getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(Integer idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public int getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(int firstLevel) {
        this.firstLevel = firstLevel;
    }

    public int getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(int secondLevel) {
        this.secondLevel = secondLevel;
    }

    public int getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(int thirdLevel) {
        this.thirdLevel = thirdLevel;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    
        public Integer getIsOfRequest() {
        return isOfRequest;
    }

    public void setIsOfRequest(Integer isOfRequest) {
        this.isOfRequest = isOfRequest;
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
