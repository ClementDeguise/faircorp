package com.emse.spring.faircorp.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Light {

    @Id
   // @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private long saturation;

    // HEX COLOR
    @NotNull
    private String color;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;  //allumé ou eteinte, ON/OFF



    @ManyToOne(optional = false)
    private Room room;


    public Light() {
    }

    public Light(Room room, String color, Status status, Long saturation) {
        this.color = color;
        this.status = status;
        this.room = room;
        this.saturation = saturation;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getSaturation() {
        return this.saturation;
    }

    public void setSaturation(Long saturation) {
        this.saturation = saturation;
    }


}
