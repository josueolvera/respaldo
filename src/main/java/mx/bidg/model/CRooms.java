package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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
import mx.bidg.config.JsonViews;

/**
 *
 * @author rubens
 */
@Entity
@Table(name = "C_ROOMS")

public class CRooms implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ROOM")
    @JsonView(JsonViews.Root.class)
    private Integer idRoom;
    
    @Size(max = 200)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;
    
    @Column(name = "VIEWFORALL")
    @JsonView(JsonViews.Root.class)
    private Integer viewforall;
    
    @Size(max = 200)
    @Column(name = "IMAGEURL")
    @JsonView(JsonViews.Root.class)
    private String imageurl;

    public CRooms() {
    }

    public CRooms(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getViewforall() {
        return viewforall;
    }

    public void setViewforall(Integer viewforall) {
        this.viewforall = viewforall;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRoom != null ? idRoom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CRooms)) {
            return false;
        }
        CRooms other = (CRooms) object;
        if ((this.idRoom == null && other.idRoom != null) || (this.idRoom != null && !this.idRoom.equals(other.idRoom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CRooms[ idRoom=" + idRoom + " ]";
    }
    
}
