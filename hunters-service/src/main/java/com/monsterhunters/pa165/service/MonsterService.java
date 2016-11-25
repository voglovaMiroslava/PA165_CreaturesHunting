package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miroslava Voglova
 */
@Service
public interface MonsterService {

    /**
     * Creates monster
     *
     * @param m monster to create
     * @return created monster
     */
    Monster createMonster(Monster m);

    /**
     * Deletes monster
     *
     * @param m monster to delete
     */
    void deleteMonster(Monster m);

    /**
     * Updates monster
     *
     * @param m monster to update
     * @return updated monster
     */
    Monster updateMonster(Monster m);

    /**
     * Finds monster by id
     *
     * @param id id of the monster
     * @return monster or null if no monster with given id exists
     */
    Monster findById(Long id);

    /**
     * Finds all monsters
     *
     * @return all monsters
     */
    List<Monster> findAll();

    /**
     * Adds type to monster
     *
     * @param type type to be added
     * @param m monster whom it adds type
     */
    void addType(MonsterType type, Monster m);

    /**
     * Removes type from monster
     *
     * @param type type to be removed
     * @param m monster whose type is removed
     */
    void removeType(MonsterType type, Monster m);

    /**
     * Relocates monster
     *
     * @param newLocation new location of monster
     * @param m monster to be relocated
     */
    void relocateMonster(Location newLocation, Monster m);

    /**
     * Returns locations where lives most of the monsters that have specific
     * type
     *
     * @param type type of the monsters we are looking for
     * @return locations that have most monsters of given type
     */
    List<Location> getLocationsWithMostMonsterType(MonsterType type);
}
