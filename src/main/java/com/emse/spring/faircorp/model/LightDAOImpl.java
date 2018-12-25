package com.emse.spring.faircorp.model;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class LightDAOImpl implements LightCustomDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Light> findOnLights() {     //will create a query to find lights ON
        String jpql = "select lt from Light lt where lt.status = :value";
        return em.createQuery(jpql, Light.class)
                .setParameter("value", Status.ON)
                .getResultList();
    }

    public String SetGetMessage(String method) {
        String message = method + "http://192.168.1.131/api/xCXbljoa3kZ-iI0yQIc6luELl2mdtQjBnX5SkO8x/lights/";
        return message;
    }

    public String SetGetByIdMessage(String method, Long id) {
        String idS = String.valueOf(id);
        String message = method + "http://192.168.1.131/api/xCXbljoa3kZ-iI0yQIc6luELl2mdtQjBnX5SkO8x/lights/" + idS;
        return message;
    }

    public String SetPutMessage(String method, Long id, String body) {
        String idS = String.valueOf(id);
        String message = method + "/" + idS + "/" + body;

        //String message = method + "http://192.168.1.131/api/xCXbljoa3kZ-iI0yQIc6luELl2mdtQjBnX5SkO8x/lights/" + idS + "/state " + body;
        return message;
    }

}
