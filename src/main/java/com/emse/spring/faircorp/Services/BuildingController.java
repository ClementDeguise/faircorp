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
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {

    @Autowired
    private BuildingDAO buildingDao;



    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll()
                .stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{building_id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(building -> new BuildingDto(building)).orElse(null);
    }

    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        if (dto.getId() != null) building = buildingDao.findById(dto.getId()).orElse(null);

        if (building == null) {
            building = buildingDao.save(new Building(dto.getNumOfFloors()));
            // methode de base de la DAO
            // l'ordre d'appel est important et doit respecter celui du constructeur
        } else {
            building.setLevel(dto.getNumOfFloors());
            buildingDao.save(building);
        }

        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{building_id}")
    public void delete(@PathVariable Long id) {
        buildingDao.deleteById(id);
    }
}