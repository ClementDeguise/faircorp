package com.emse.spring.faircorp.model;

public class LightDto { //Dto = Data Transfert Object

    private Long id;
    private Integer level;
    private Status status;
    private Room room;

    public LightDto() {
    }

    public LightDto(Light light) {
        this.id = light.getId();
        this.level = light.getLevel();
        this.status = light.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public Status getStatus() {
        return status;
    }

    public Long getRoomId() {
        return room.getId();
    }
}

