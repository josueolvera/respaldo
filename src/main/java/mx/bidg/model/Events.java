/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author rubens
 */
@Entity
@Table(name = "EVENTS")
public class Events implements Serializable{
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_EVENT")
    @JsonView(JsonViews.Root.class)
    private Integer idevent;
    
    @Size(max = 200)
    @Column(name = "TITLE")
    @JsonView(JsonViews.Root.class)
    private String title;
    
    @Column(name = "START")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime start;
    
    
    @Column(name = "END")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime end;
    
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private Users users;
    
    
    @JoinColumn(name = "ID_ROOM", referencedColumnName = "ID_ROOM")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRooms crooms;
    
    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;
    
    @Column(name = "ID_ROOM", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRoom;

    /**
     * @return the idevent
     */
    public Integer getIdevent() {
        return idevent;
    }

    /**
     * @param idevent the idevent to set
     */
    public void setIdevent(Integer idevent) {
        this.idevent = idevent;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * @return the users
     */
    public Users getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Users users) {
        this.users = users;
    }

    /**
     * @return the crooms
     */
    public CRooms getCrooms() {
        return crooms;
    }

    /**
     * @param crooms the crooms to set
     */
    public void setCrooms(CRooms crooms) {
        this.crooms = crooms;
    }

    

    
    
    
    
    
    
    
    
    
}
