package com.emse.spring.faircorp.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDAOImpl implements RoomCustomDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Light> findLightbyRoomId(Long id) {
        String jpql = "select lt from Light lt inner join lt.room rm where rm.id = :roomid";  //lt et rm variables muettes
        return em.createQuery(jpql, Light.class)
                .setParameter("roomid", id)
                .getResultList();
    }

    @Override
    public List<Room> findbyName(String name) {
        String jpql = "select rm from Room rm where rm.name = :name";
        return em.createQuery(jpql, Room.class)
                .setParameter("name", name)
                .getResultList();
    }


}
