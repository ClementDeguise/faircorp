package com.emse.spring.faircorp.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Building {


    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer numoffloor;

    @OneToMany(mappedBy = "building")       //nom du lien identique
    private Set<Room> rooms;


    public Building() {
    }

    public Building(Integer numoffloor) {
        this.numoffloor = numoffloor;

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return numoffloor;
    }

    public void setLevel(Integer floor) {
        this.numoffloor = floor;
    }

}


/*
INSERT INTO ROOM(ID, NAME, FLOOR, BUILDING_ID) VALUES(-10, 'Room1', 1, -1);
INSERT INTO ROOM(ID, NAME, FLOOR, BUILDING_ID) VALUES(-9, 'Room2', 1, -1);

INSERT INTO LIGHT(ID, LEVEL, STATUS, ROOM_ID) VALUES (-1, 8, 'ON', -10);
INSERT INTO LIGHT(ID, LEVEL, STATUS, ROOM_ID) VALUES (-2, 0, 'OFF', -10);
 */
