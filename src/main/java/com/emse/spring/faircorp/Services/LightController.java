package com.emse.spring.faircorp.Services;


import com.emse.spring.faircorp.Services.Subscriber;
import com.emse.spring.faircorp.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;





@RestController

@CrossOrigin
@RequestMapping("/api/lights")
@Transactional
public class LightController {

    @Autowired
    private LightDAO lightDao;
    @Autowired
    private RoomDAO roomDao;
    //@Autowired
    public Subscriber subscriber;

    //private String getMessage;
   // private String getMessageById;







    @CrossOrigin
    @GetMapping
    public List<LightDto> findAll() {

        //ask database
        //wait for db update on request transfer

        String getMessage = lightDao.SetGetMessage("GET ");

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "light/1");
            subscriber.sendMessage(getMessage, "light/1");

        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        //update of DB on response



        return lightDao.findAll()
                .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());
    }


    @CrossOrigin
    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        String getMessageById = lightDao.SetGetByIdMessage("GET ", id);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "light/" + id);
            subscriber.sendMessage(getMessageById, "light/" + id);


        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        return new LightDto(light);
       // return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }


    @CrossOrigin
    @PutMapping(path = "/{id}/state")
    public LightDto colorStatus(@PathVariable Long id, @RequestBody String body) {

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        String col;

        try {
            col = body.substring(11, 15);
            light.setColor(col);
        }
        catch (Exception e){
            System.out.println("Wrong body type");
        }

        // body contains either the status or the color, but not both
        //the HTML page should create the appropriate body whether the light or the color is switched
        String getPutMessage = lightDao.SetPutMessage("PUT ", id, body);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "light/" + id);
            subscriber.sendMessage(getPutMessage, "light/" + id);



        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        return new LightDto(light);
    }
    //{"color": #ffff}



    @CrossOrigin
    @PutMapping(path = "/{id}/switch")
    public LightDto switchStatus(@PathVariable Long id) {

        String body;

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);
        //si getStatus renvoie Status.ON, alors Status.OFF, else: Status.ON

        if (light.getStatus() == Status.ON) {
            body = "{\"status\": ON}";
        }
        else {
            body = "{\"status\": OFF}";
        }

        // body contains either the status or the color, but not both
        //the HTML page should create the appropriate body whether the light or the color is switched
        String getPutMessage = lightDao.SetPutMessage("PUT", id, body);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "light/" + id);
            subscriber.sendMessage(getPutMessage, "light/" + id);

        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }


        return new LightDto(light);
    }

/*
    @CrossOrigin
    @PostMapping
    public LightDto create(@RequestBody LightDto dto) {
        Light light = null;

        if (light == null) {
            light = lightDao.save(new Light(roomDao.getOne(dto.getRoomId()), dto.getColor(), dto.getStatus()));
            // methode de base de la DAO
            // l'ordre d'appel est important et doit respecter celui du constructeur
        } else {
            light.setColor(dto.getColor());
            light.setStatus(dto.getStatus());
            lightDao.save(light);
        }

        if (dto.getId() != null) light = lightDao.findById(dto.getId()).orElse(null);

        return new LightDto(light);
    }


    @DeleteMapping(path = "/{id}")
    @CrossOrigin
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        lightDao.deleteById(id);
        response.setHeader("Access-Control-Allow-Origin", "*");
    }*/
}
