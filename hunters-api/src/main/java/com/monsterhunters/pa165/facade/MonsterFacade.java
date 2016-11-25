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

    /**
     * Creates monster
     *
     * @param m monster to create
     * @return id of created monster
     */
    Long createMonster(MonsterCreateDTO m);

    /**
     * Deletes monster
     *
     * @param monsterId id of deleted monster
     */
    void deleteMonster(Long monsterId);

    /**
     * Updates monster
     *
     * @param m monster to update
     */
    void updateMonster(MonsterDTO m);

    /**
     * Finds monster by id
     *
     * @param id id of the monster
     * @return monster with given id
     */
    MonsterDTO findById(Long id);

    /**
     * Finds all monsters
     *
     * @return all monsters
     */
    List<MonsterDTO> findAll();

    /**
     * Adds type to monster
     *
     * @param type type to be added
     * @param m monster whom it adds type
     */
    void addType(MonsterType type, MonsterDTO m);

    /**
     * Removes type from monster
     *
     * @param type type to be removed
     * @param m monster whose type is removed
     */
    void removeType(MonsterType type, MonsterDTO m);

    /**
     * Relocates monster
     *
     * @param newLocation new location of monster
     * @param m monster to be relocated
     */
    void relocateMonster(LocationDTO newLocation, MonsterDTO m);

    /**
     * Returns locations where lives most of the monsters that have specific
     * type
     *
     * @param type type of the monsters we are looking for
     * @return locations that have most monsters of given type
     */
    List<LocationDTO> getLocationsWithMostMonsterType(MonsterType type);
}
