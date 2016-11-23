package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Weapon} entity.
 *
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

@Service
public interface WeaponService {

    /**
     * Create new weapon
     * @param w weapon object
     * @return created weapon
     */
    Weapon createWeapon(Weapon w) throws HuntersServiceException;

    /**
     * Delete weapon
     * @param w weapon object
     */
    void deleteWeapon(Weapon w) throws HuntersServiceException;

    /**
     * Updates weapon
     * @param w weapon object to update
     * @return updated weapon object
     */
    Weapon updateWeapon(Weapon w) throws HuntersServiceException;

    /**
     * Add comment to list of comments of weapon
     * @param w is weapon object
     * @param c is a comment object which will be added to list
     */
    void addComment(Weapon w, Comment c) throws HuntersServiceException;

    /**
     * Remove comment from list of comments of weapon
     * @param w is weapon object
     * @param c is a comment object to removed from list
     */
    void removeComment(Weapon w, Comment c) throws HuntersServiceException;

    /**
     * Add monster type which can be injured by the weapon
     * @param w is weapon object
     * @param m is monster type
     */
    void addEffectiveAgainst(Weapon w, MonsterType m) throws HuntersServiceException;

    /**
     * Remove monster type from effectiveAgainst list
     * @param w weapon object
     * @param m is monster type
     */
    void removeEffectiveAgainst(Weapon w, MonsterType m) throws HuntersServiceException;

    /**
     * Find all weapons
     * @return list of all weapons
     */
    List<Weapon> findAll() throws HuntersServiceException;

    /**
     *  Find weapon by ID
     * @param id is id of a weapon
     * @return found weapon
     */
    Weapon findById(Long id) throws HuntersServiceException;

    /**
     * Find weapon by its name
     * @param wName name of the weapon
     * @return found weapon
     */
    Weapon findByName(String wName) throws HuntersServiceException;
}
