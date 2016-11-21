package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Monster;
import java.util.List;

/**
 *
 * @author Miroslava Voglova
 */
public interface MonsterDao {

    /**
     * Finds monster by it's id.
     * @param id generated id of monster
     * @return monster with given id
     */
    Monster findById(Long id);
    
    /**
     * Saves monster to db.
     * @param m monster which will be saved
     */
    void create(Monster m);
    
    /**
     * Updates monster in db.
     * @param m monster which will be updated
     */
    void update(Monster m);
    
    /**
     * Removes monster from db.
     * @param m monster to be removed
     */
    void delete(Monster m);
    
    /**
     * Returns all monsters that was saved in db.
     * @return list of monsters from db
     */
    List<Monster> findAll();
    
    /**
     * Returns monster with given name
     * @param name name of the monster we are looking for
     * @return monster with given name
     */
    Monster findByName(String name);
}
