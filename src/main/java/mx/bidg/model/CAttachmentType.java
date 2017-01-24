/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

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

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "C_ATTACHMENT_TYPE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CAttachmentType implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final CAttachmentType anexoA = new CAttachmentType(1);
    private static final CAttachmentType anexoB = new CAttachmentType(2);
    private static final CAttachmentType anexoC = new CAttachmentType(3);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ATTACHMENT_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idAttachmentType;

    @Size(max = 25)
    @Column(name = "name")
    @JsonView(JsonViews.Root.class)
    private String name;

    public CAttachmentType() {
    }

    public CAttachmentType(Integer idAttachmentType) {
        this.idAttachmentType = idAttachmentType;
    }

    public Integer getIdAttachmentType() {
        return idAttachmentType;
    }

    public void setIdAttachmentType(Integer idAttachmentType) {
        this.idAttachmentType = idAttachmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttachmentType != null ? idAttachmentType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAttachmentType)) {
            return false;
        }
        CAttachmentType other = (CAttachmentType) object;
        if ((this.idAttachmentType == null && other.idAttachmentType != null) || (this.idAttachmentType != null && !this.idAttachmentType.equals(other.idAttachmentType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAttachmentType[ idAttachmentType=" + idAttachmentType + " ]";
    }
    
}
