package com.emse.spring.faircorp.model;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface LightDAO extends JpaRepository<Light, Long>, LightCustomDAO {
}
