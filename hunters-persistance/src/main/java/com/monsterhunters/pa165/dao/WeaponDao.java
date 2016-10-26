package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Weapon;


import java.util.List;


/**
 * Created by babcang on 22.10.2016.
 * @author BabcanG
 */

public interface WeaponDao {

    /**
     * Find weapon by id
     *
     * @param id is generated id of weapon
     * @return weapon specified by id
     */
    public Weapon findById(Long id);

    /**
     * Create new weapon - persist to db
     *
     * @param w is the instance of new weapon
     */
    public void create(Weapon w);

    /**
     * Delete weapon from db
     *
     * @param w indicates which instance want to delete
     */
    public void delete(Weapon w);

    /**
     * Return list of all weapons
     *
     * @return list of all weapons
     */
    public List<Weapon> findAll();

    /**
     * Find weapon by its name
     *
     * @param name is name of weapon
     * @return weapon specified by name
     */
    public Weapon findByName(String name);

    /**
     * Update information about weapon
     *
     * @param w indicates which instance want to update
     * @return weapon with updated information
     */
    public Weapon update(Weapon w);

}
