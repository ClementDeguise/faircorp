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
}
