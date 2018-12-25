package com.emse.spring.faircorp.model;

public class LightDto { //Dto = Data Transfert Object

    private Long id;
    private String color;
    private Status status;
    private Long saturation;
    private Long roomId;

    public LightDto() {
    }

    public LightDto(Light light) {
        this.id = light.getId();
        this.color = light.getColor();
        this.status = light.getStatus();
        this.roomId = light.getRoomId();
        this.saturation = light.getSaturation();

    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Status getStatus() {
        return status;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getSaturation() { return  saturation; }

}

