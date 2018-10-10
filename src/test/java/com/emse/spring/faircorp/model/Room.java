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
    private Integer level;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "room")       //nom du lien identique
    private Set<Light> light;


    public Room() {
    }

    public Room(Integer level) {
        this.level = level;

    }

/*
    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }
*/



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
