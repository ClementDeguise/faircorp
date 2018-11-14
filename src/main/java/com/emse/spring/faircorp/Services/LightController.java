package com.emse.spring.faircorp.Services;


import com.emse.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/lights")
@Transactional
public class LightController {

    @Autowired
    private LightDAO lightDao;
    @Autowired
    private RoomDAO roomDao;


    @GetMapping
    public List<LightDto> findAll() {
        return lightDao.findAll()
                .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {
        return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }

    @PutMapping(path = "/{id}/switch")
    public LightDto switchStatus(@PathVariable Long id) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);
        return new LightDto(light);
    }

    @PostMapping
    public LightDto create(@RequestBody LightDto dto) {
        Light light = null;

        if (light == null) {
            light = lightDao.save(new Light(roomDao.getOne(dto.getRoomId()), dto.getLevel(), dto.getStatus()));
            // methode de base de la DAO
            // l'ordre d'appel est important et doit respecter celui du constructeur
        } else {
            light.setLevel(dto.getLevel());
            light.setStatus(dto.getStatus());
            lightDao.save(light);
        }

        if (dto.getId() != null) light = lightDao.findById(dto.getId()).orElse(null);

        return new LightDto(light);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        lightDao.deleteById(id);
    }
}
