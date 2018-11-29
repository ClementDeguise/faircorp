package com.emse.spring.faircorp.Services;


import com.emse.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {

    @Autowired
    private BuildingDAO buildingDao;


    @CrossOrigin
    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll()
                .stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(building -> new BuildingDto(building)).orElse(null);
    }

    @CrossOrigin
    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        if (dto.getId() != null) building = buildingDao.findById(dto.getId()).orElse(null);

        if (building == null) {
            building = buildingDao.save(new Building(dto.getNumOfFloor()));
            // methode de base de la DAO
            // l'ordre d'appel est important et doit respecter celui du constructeur
        } else {
            building.setLevel(dto.getNumOfFloor());
            buildingDao.save(building);
        }

        return new BuildingDto(building);
    }

    @CrossOrigin
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        buildingDao.deleteById(id);
    }
}