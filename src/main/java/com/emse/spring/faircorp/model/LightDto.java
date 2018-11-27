package com.emse.spring.faircorp.model;

public class LightDto { //Dto = Data Transfert Object

    private Long id;
    private Integer level;
    private Status status;
    private Long roomid;

    public LightDto() {
    }

    public LightDto(Light light) {
        this.id = light.getId();
        this.level = light.getLevel();
        this.status = light.getStatus();
        this.roomid = light.getRoomId();

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
        return roomid;
    }
}

