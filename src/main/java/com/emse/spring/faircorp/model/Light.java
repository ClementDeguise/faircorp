package com.emse.spring.faircorp.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Light {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer level;      //étage

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;  //allumé ou eteinte, ON/OFF



    @ManyToOne(optional = false)
    private Room room;


    public Light() {
    }

    public Light(Room room, Integer level, Status status) {
        this.level = level;
        this.status = status;
        this.room = room;
    }

    public Long getRoomId() {
        return room.getId();
    }

    public void setRoom(Room room) {
        this.room = room;
    }



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
