package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterCreateDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.enums.MonsterType;
import java.util.List;

/**
 *
 * @author Miroslava Voglova
 */
public interface MonsterFacade {
    
    Long createMonster(MonsterCreateDTO m);
    
    void deleteMonster(Long monsterId);
    
    void updateMonster(MonsterDTO m);

    MonsterDTO findById(Long id);

    List<MonsterDTO> findAll();

    void addType(MonsterType type, MonsterDTO m);

    void removeType(MonsterType type, MonsterDTO m);

    void relocateMonster(LocationDTO newLocation, MonsterDTO m);
    
    List<LocationDTO> getLocationsWithMostMonsterType(MonsterType type);
}
