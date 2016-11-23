package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.enums.MonsterType;

import java.util.List;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */
public interface WeaponFacade {

    /** Method create new weapon from DTO
     *
     * @param w WeaponCreateDTO object
     * @return id of created weapon
     */
    Long createWeapon(WeaponCreateDTO w);

    /** Method updates weapon
     *
     * @param w is WeaponDTO object
     * @return id of updated weapon
     */
    Long updateWeapon(WeaponDTO w);

    /** Method delete weapon
     *
     * @param weaponId is id of weapon to delete
     */
    void deleteWeapon(Long weaponId);

    /** Method to add comment to list
     *
     * @param weaponId is id of weapon to which will be comment added
     * @param commentId is id of comment
     */
    void addComment(Long weaponId, Long commentId);

    /** Method remove comment from list of comments
     *
     * @param weaponId is id of weapon from which will be commend removec
     * @param commentId is id of comment
     */
    void removeComment(Long weaponId, Long commentId);

    /** Method add type of monster to list of monsters which can be killed by weapon
     *
     * @param weaponId is id of weapon
     * @param m is monster type
     */
    void addEffectiveAgainst(Long weaponId, MonsterType m);

    /** Method remove type of monster from effectiveAgainst list
     *
     * @param weaponId is id of weapon
     * @param m is monster type which want to delete
     */
    void removeEffectiveAgainst(Long weaponId, MonsterType m);

    /** Method return all weapons
     *
     * @return list of all DTOweapons
     */
    List<WeaponDTO> getAllWeapons();

    /** Method return weaponDTO with specified id
     *
     * @param weaponId is weapon id
     * @return found weaponDTO
     */
    WeaponDTO getWeaponById(Long weaponId);

    /** Method return weaponDTO specified by name
     *
     * @param name
     * @return found weaponDTO
     */
    WeaponDTO getWeaponByName(String name);

}
