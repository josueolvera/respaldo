/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_GROUPS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CGroups implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GROUP")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private Integer idGroup;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "GROUP_NAME")
    @JsonView({JsonViews.Root.class, JsonViews.EmbeddedDwEnterprises.class})
    private String groupName;
    
    @Size(max = 15)
    @Column(name = "ACRONYMS")
    @JsonView(JsonViews.Root.class)
    private String acronyms;
    
    @OneToMany(mappedBy = "group")
    @JsonView(JsonViews.EmbeddedBudget.class)
    private List<Budgets> budgetsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @JsonView(JsonViews.Embedded.class)
    private List<DwEnterprises> dwEnterprisesList;

    public CGroups() {
    }

    public CGroups(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public CGroups(Integer idGroup, String groupName) {
        this.idGroup = idGroup;
        this.groupName = groupName;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public List<Budgets> getBudgetsList() {
        return budgetsList;
    }

    public void setBudgetsList(List<Budgets> budgetsList) {
        this.budgetsList = budgetsList;
    }
    
    public List<DwEnterprises> getDwEnterprisesList() {
        return dwEnterprisesList;
    }

    public void setDwEnterprisesList(List<DwEnterprises> dwEnterprisesList) {
        this.dwEnterprisesList = dwEnterprisesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGroup != null ? idGroup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CGroups)) {
            return false;
        }
        CGroups other = (CGroups) object;
        if ((this.idGroup == null && other.idGroup != null) || (this.idGroup != null && !this.idGroup.equals(other.idGroup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CGroups[ idGroup=" + idGroup + " ]";
    }
    
}
