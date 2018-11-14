package com.emse.spring.faircorp.model;


import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomDAO extends JpaRepository<Room, Long>, RoomCustomDAO {     //JpaRepository< type de la donnée, ici Room, et type de la clé, ici l'Id>


}
