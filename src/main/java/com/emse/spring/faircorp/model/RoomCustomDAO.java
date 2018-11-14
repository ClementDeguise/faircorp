package com.emse.spring.faircorp.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomCustomDAO {

    List<Light> findLightbyRoomId(Long id);

    List<Room> findbyName(String name);

    // @Query("select rm from Room rm where rm.name = :name")       //a mettre dans le DAO de base si @Query utilisé
    // Room findbyName(@Param("name") String name);




}
