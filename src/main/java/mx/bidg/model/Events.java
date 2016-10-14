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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_EVENT")
    @JsonView(JsonViews.Root.class)
    private Integer id;
    
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
    private Users user;
    
    
    @JoinColumn(name = "ID_ROOM", referencedColumnName = "ID_ROOM")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CRooms room;
    
    @Column(name = "ID_USER", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;
    
    @Column(name = "ID_ROOM", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRoom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start.toString();
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public String getEnd() {
        return end.toString();
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public CRooms getRoom() {
        return room;
    }

    public void setRoom(CRooms room) {
        this.room = room;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    
    
}
