package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import java.util.List;

/**
 *
 * @author Miroslava Voglova
 */
public interface MonsterService {

    void createMonster(Monster m);

    void deleteMonster(Monster m);

    void updateMonster(Monster m);

    Monster findById(Long id);

    List<Monster> findAll();

    void addType(MonsterType type, Monster m);

    void removeType(MonsterType type, Monster m);

    void relocateMonster(Location newLocation, Monster m);
    
    List<Location> getLocationsWithMostMonsterType(MonsterType type);
}
