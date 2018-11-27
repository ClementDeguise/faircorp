package com.emse.spring.faircorp.model;

public class RoomDto { //Dto = Data Transfert Object

    private Long id;
    private Integer floor;
    private String name;
    private Long buildingId;

    public RoomDto() {
    }

    public RoomDto(Room room) {
        this.id = room.getId();
        this.floor = room.getLevel();
        this.name = room.getName();
        this.buildingId = room.getBuildingId();

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
        return buildingId;
    }
}

