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
public class MonsterFacadeImpl implements MonsterFacade{

    @Override
    public Long createMonster(MonsterCreateDTO m) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteMonster(Long monsterId) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MonsterDTO getMonsterById(Long monsterId) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MonsterDTO> getAllMonsters() {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateMonster(MonsterDTO m) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MonsterDTO findById(Long id) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MonsterDTO> findAll() {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addType(MonsterType type, MonsterDTO m) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeType(MonsterType type, MonsterDTO m) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void relocateMonster(LocationDTO newLocation, MonsterDTO m) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LocationDTO> getLocationsWithMostMonsterType(MonsterType type) {
        //TODO implement it
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
