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
    Long createWeapon(WeaponCreateDTO w);
    Long updateWeapon(WeaponDTO w);
    void deleteWeapon(Long weaponId);
    void addComment(Long weaponId, Long commentId);
    void removeComment(Long weaponId, Long commentId);
    void addEffectiveAgainst(Long weaponId, MonsterType m);
    void removeEffectiveAgainst(Long weaponId, MonsterType m);

    List<WeaponDTO> getAllWeapons();
    WeaponDTO getWeaponById(Long weaponId);
    WeaponDTO getWeaponByName(String name);

}
