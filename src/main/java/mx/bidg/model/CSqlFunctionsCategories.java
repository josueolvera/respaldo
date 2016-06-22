/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author rubens
 */
@Entity
@DynamicUpdate
@Table(name = "C_SQL_FUNCTIONS_CATEGORIES")
public class CSqlFunctionsCategories implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final CSqlFunctionsCategories BUSQUEDA = new CSqlFunctionsCategories(1);
    public static final CSqlFunctionsCategories OPERACION = new CSqlFunctionsCategories(2);
    public static final CSqlFunctionsCategories REGLA = new CSqlFunctionsCategories(3);
    public static final CSqlFunctionsCategories CONDICION = new CSqlFunctionsCategories(4);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CATEGORY")
    private Integer idCategory;
    @Size(max = 45)
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    public CSqlFunctionsCategories() {
    }

    public CSqlFunctionsCategories(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategory != null ? idCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSqlFunctionsCategories)) {
            return false;
        }
        CSqlFunctionsCategories other = (CSqlFunctionsCategories) object;
        if ((this.idCategory == null && other.idCategory != null) || (this.idCategory != null && !this.idCategory.equals(other.idCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CSqlFunctionsCategories[ idCategory=" + idCategory + " ]";
    }
    
}
