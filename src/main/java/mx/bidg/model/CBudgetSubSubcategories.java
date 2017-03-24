/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author SOPORTE
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_SUB_SUBCATEGORIES")
public class CBudgetSubSubcategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_SUB_SUBCATEGORIES")
    private Integer idSubSubcategories;

    @Size(max = 40)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

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

    @Column(name = "THIRD_LEVEL", nullable=true)
    @JsonView(JsonViews.Root.class)
    private String thirdLevel;

    public CBudgetSubSubcategories() {
    }

    public CBudgetSubSubcategories(Integer idSubSubcategories) {
        this.idSubSubcategories = idSubSubcategories;
    }

    public CBudgetSubSubcategories(Integer idSubSubcategories, LocalDateTime creationDate) {
        this.idSubSubcategories = idSubSubcategories;
        this.creationDate = creationDate;
    }

    public Integer getIdSubSubcategories() {
        return idSubSubcategories;
    }

    public void setIdSubSubcategories(Integer idSubSubcategories) {
        this.idSubSubcategories = idSubSubcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(String thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubSubcategories != null ? idSubSubcategories.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetSubSubcategories)) {
            return false;
        }
        CBudgetSubSubcategories other = (CBudgetSubSubcategories) object;
        if ((this.idSubSubcategories == null && other.idSubSubcategories != null) || (this.idSubSubcategories != null && !this.idSubSubcategories.equals(other.idSubSubcategories))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetSubSubcategories[ idSubSubcategories=" + idSubSubcategories + " ]";
    }

}
