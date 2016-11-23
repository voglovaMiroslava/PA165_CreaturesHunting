package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterCreateDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.MonsterService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Miroslava Voglova
 */
@Service
@Transactional
public class MonsterFacadeImpl implements MonsterFacade {

    private static final Logger LOG = LoggerFactory.getLogger(MonsterFacadeImpl.class);

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createMonster(MonsterCreateDTO m) {
        Monster monster = mappingService.mapTo(m, Monster.class);
        monsterService.createMonster(monster);
        return monster.getId();
    }

    @Override
    public void deleteMonster(Long monsterId) {
        Monster m = monsterService.findById(monsterId);
        monsterService.deleteMonster(m);
    }

    @Override
    public void updateMonster(MonsterDTO m) {
        Monster monster = mappingService.mapTo(m, Monster.class);
        monsterService.updateMonster(monster);
    }

    @Override
    public MonsterDTO findById(Long id) {
        Monster m = monsterService.findById(id);
        if (m == null) {
            return null;
        }
        return mappingService.mapTo(m, MonsterDTO.class);
    }

    @Override
    public List<MonsterDTO> findAll() {
        return mappingService.mapTo(monsterService.findAll(), MonsterDTO.class);
    }

    @Override
    public void addType(MonsterType type, MonsterDTO m) {
        Monster monster = mappingService.mapTo(m, Monster.class);
        monster.addType(type);
        monsterService.updateMonster(monster);
    }

    @Override
    public void removeType(MonsterType type, MonsterDTO m) {
        Monster monster = mappingService.mapTo(m, Monster.class);
        monster.removeType(type);
        monsterService.updateMonster(monster);
    }

    @Override
    public void relocateMonster(LocationDTO newLocation, MonsterDTO m) {
        Monster monster = mappingService.mapTo(m, Monster.class);
        Location location = mappingService.mapTo(newLocation, Location.class);
        monster.setLocation(location);
        monsterService.updateMonster(monster);
    }

    @Override
    public List<LocationDTO> getLocationsWithMostMonsterType(MonsterType type) {
        List<Location> locations = monsterService.getLocationsWithMostMonsterType(type);
        return mappingService.mapTo(locations, LocationDTO.class);
    }

}
