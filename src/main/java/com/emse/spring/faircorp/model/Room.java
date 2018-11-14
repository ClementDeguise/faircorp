package com.emse.spring.faircorp.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Room {


    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer floor;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "room")       //nom du lien identique
    private Set<Light> light;


    @ManyToOne(optional = false)
    private Building building;


    public Room() {
    }

    public Room(Integer floor, String name, Building building) {
        this.floor = floor;
        this.building = building;
        this.name = name;

    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }




    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return floor;
    }

    public void setLevel(Integer floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }


}
