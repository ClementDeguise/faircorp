package com.emse.spring.faircorp.model;

public class RoomDto { //Dto = Data Transfert Object

    private Long id;
    private Integer floor;
    private String name;
    private Building building;

    public RoomDto() {
    }

    public RoomDto(Room room) {
        this.id = room.getId();
        this.floor = room.getLevel();
        this.name = room.getName();
        this.building = room.getBuilding();

    }

    public Long getId() {
        return id;
    }

    public Integer getFloor() {
        return floor;
    }

    public String getName() {
        return name;
    }

    public Long getBuildingId() {
        return building.getId();
    }
}

