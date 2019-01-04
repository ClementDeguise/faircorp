package com.emse.spring.faircorp.Services;


import com.emse.spring.faircorp.Services.Subscriber;
import com.emse.spring.faircorp.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private Subscriber subscriber;


    //private String getMessage;
   // private String getMessageById;





    @CrossOrigin
    @GetMapping
    public List<LightDto> findAll() {

        //ask database
        //wait for db update on request transfer

        String getMessage = "GET/ALL";

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender", "SpringReq");
            subscriber.sendMessage(getMessage, "sender");


        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        return lightDao.findAll()
                .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());
    }



    @CrossOrigin
    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {

        String idS = String.valueOf(id);
        String getMessageById = "GET/" + idS;


        //String getMessageById = lightDao.SetGetByIdMessage("GET ", id);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender","SpringReq");
            subscriber.sendMessage(getMessageById, "sender");
            //subscriber.Disconnect();


        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        return new LightDto(light);
       // return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }



    @CrossOrigin
    @PutMapping(path = "/{id}/switch")
    public LightDto switchStatus(@PathVariable Long id) {

        String body;

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);

        //si getStatus renvoie Status.ON, alors Status.OFF, else: Status.ON

        if (light.getStatus() == Status.ON) {
            body = "{\"on\" : true}";
        }
        else {
            body = "{\"on\" : false}";
        }


        String getPutMessage = lightDao.SetPutMessage("PUT", id, body);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender","SpringReq");
            subscriber.sendMessage(getPutMessage, "sender");


        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }


        return new LightDto(light);
    }





    @CrossOrigin
    @PutMapping(path = "/{id}/color")
    public LightDto colorStatus(@PathVariable Long id, @RequestBody String body) {

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        String col;

        // FORMAT "{"color": #ffff}"

        try {
            col = body.substring(11, 16);
            light.setColor(col);
        }
        catch (Exception e){
            System.out.println("Wrong body type");
        }

        // here body is inputted
        String getPutMessage = lightDao.SetPutMessage("PUT", id, body);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender","SpringReq");
            subscriber.sendMessage(getPutMessage, "sender");

        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        return new LightDto(light);
    }






    @CrossOrigin
    @PutMapping(path = "/{id}/sat")
    public LightDto satStatus(@PathVariable Long id, @RequestBody String body) {

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        Long sat;
        // format {"sat": 100}
        try {
            sat = Long.parseLong(body.substring(8, body.indexOf("}")));
            light.setSaturation(sat);
        }
        catch (Exception e){
            System.out.println("Wrong body type");
        }

        // here body is inputted
        String getPutMessage = lightDao.SetPutMessage("PUT", id, body);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender","SpringReq");
            subscriber.sendMessage(getPutMessage, "sender");

        } catch (MqttException me) {
            System.out.println(me.getMessage());
        }

        return new LightDto(light);
    }


    // ROOM NAME CHANGING
    @CrossOrigin
    @PutMapping(path = "/{id}")
    public LightDto roomStatus(@PathVariable Long id, @RequestBody String body) {

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        String roomNm = "0";
        String getPutMessage;
        Long roomId;
        try {
            roomId = Long.parseLong(body.substring(12), body.indexOf("}"));
            System.out.println(roomId);
            light.setRoom(roomDao.getOne(roomId));
            Room room = roomDao.findById(roomId).orElseThrow(IllegalArgumentException::new);

            roomNm = room.getName();


        }
        catch (Exception e){
            System.out.println("Wrong body type");
        }

        // here body is inputted
//        if (roomNm != null) {
//            String Nm = "{\"name\": " + roomNm + "}";
//            getPutMessage = lightDao.SetPutMessage("PUT", id, Nm);
//        }

        String Nm = "{\"name\": " + roomNm + "}";
        getPutMessage = lightDao.SetPutMessage("PUT", id, Nm);

        try {
            subscriber = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender","SpringReq");
            subscriber.sendMessage(getPutMessage, "sender");

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
