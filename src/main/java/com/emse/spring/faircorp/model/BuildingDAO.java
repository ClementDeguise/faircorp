package com.emse.spring.faircorp.model;


import org.springframework.data.jpa.repository.JpaRepository;


public interface BuildingDAO extends JpaRepository<Building, Long>, BuildingCustomDAO {






}