package com.emse.spring.faircorp.model;

import java.util.List;


public interface LightCustomDAO {
    List<Light> findOnLights();
    String SetGetMessage(String method);
    String SetGetByIdMessage(String method, Long id);
    String SetPutMessage(String method, Long id, String body);

}
