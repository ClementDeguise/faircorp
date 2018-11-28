package com.emse.spring.faircorp.model;

public class BuildingDto {

    private Long id;
    private Integer numOfFloor;


    public BuildingDto() {
    }

    public BuildingDto(Building building) {
        this.id = building.getId();
        this.numOfFloor = building.getLevel();


    }

    public Long getId() {
        return id;
    }

    public Integer getNumOfFloor() {
        return numOfFloor;
    }

}

