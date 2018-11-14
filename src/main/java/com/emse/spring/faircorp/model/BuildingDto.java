package com.emse.spring.faircorp.model;

public class BuildingDto {

    private Long id;
    private Integer numoffloor;


    public BuildingDto() {
    }

    public BuildingDto(Building building) {
        this.id = building.getId();
        this.numoffloor = building.getLevel();


    }

    public Long getId() {
        return id;
    }

    public Integer getNumOfFloors() {
        return numoffloor;
    }

}

