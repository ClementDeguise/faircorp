package com.emse.spring.faircorp.Services;


import com.emse.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {

    @Autowired
    private RoomDAO roomDao;
    @Autowired
    private BuildingDAO buildingDao;


    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{room_id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(room -> new RoomDto(room)).orElse(null);
    }

    @PutMapping(path = "/{room_id}/switch")
    public RoomDto switchStatus(@PathVariable Long id) {
        //find the room
        //retrieve the list of lights
        //create a light class and use the custom room DAO to find all the corresponding lights
        List<Light> lights = roomDao.findLightbyRoomId(id);

        //itère pour chaque element de la liste
        lights.forEach(light -> light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON));

        //update the table and the view
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        return new RoomDto(room);
    }

    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        Room room = null;
        if (dto.getId() != null) room = roomDao.findById(dto.getId()).orElse(null);

        if (room == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName(), buildingDao.getOne(dto.getBuildingId())));
            // methode de base de la DAO
            // l'ordre d'appel est important et doit respecter celui du constructeur
        } else {
            room.setLevel(dto.getFloor());
            room.setName(dto.getName());
            roomDao.save(room);
        }
        //update the view
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{room_id}")
    public void delete(@PathVariable Long id) {
        roomDao.deleteById(id);
    }
}