package com.emse.spring.faircorp.Services;


import com.emse.spring.faircorp.Services.Subscriber;
import com.emse.spring.faircorp.model.*;
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

    public String getMessage;
    public String getMessageById;





    @CrossOrigin
    @GetMapping
    public List<LightDto> findAll() {

        getMessage = lightDao.SetGetMessage("GET");

        return lightDao.findAll()
                .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());
    }


    @CrossOrigin
    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {

        getMessageById = lightDao.SetGetByIdMessage("GET", id);

        return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }

    @CrossOrigin
    @PutMapping(path = "/{id}/switch")
    public LightDto switchStatus(@PathVariable Long id) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);
        //si getStatus renvoie Status.ON, alors Status.OFF, else: Status.ON
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
