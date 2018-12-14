package com.emse.spring.faircorp.model;

public class RoomDto { //Dto = Data Transfert Object

    private Long id;
    private String name;


    public RoomDto() {
    }

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();


    }

    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

}

