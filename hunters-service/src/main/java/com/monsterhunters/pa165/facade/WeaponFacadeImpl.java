package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.CommentService;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.WeaponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

@Service
@Transactional
public class WeaponFacadeImpl implements WeaponFacade {

    final static Logger log = LoggerFactory.getLogger(WeaponFacadeImpl.class);

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createWeapon(WeaponCreateDTO weaponCreateDTO) {
        Weapon mappedWeapon = mappingService.mapTo(weaponCreateDTO, Weapon.class);
        Weapon newWeapon = weaponService.createWeapon(mappedWeapon);
        return newWeapon.getId();
    }

    @Override
    public Long updateWeapon(WeaponDTO weaponDTO) {
        Weapon mappedWeapon = mappingService.mapTo(weaponDTO, Weapon.class);
        Weapon updatedWeapon = weaponService.updateWeapon(mappedWeapon);
        return updatedWeapon.getId();
    }

    @Override
    public void deleteWeapon(Long weaponId) {
        Weapon weapon = weaponService.findById(weaponId);
        weaponService.deleteWeapon(weapon);
    }

    @Override
    public void addComment(Long weaponId, Long commentId) {
        weaponService.addComment(weaponService.findById(weaponId),
                commentService.findById(commentId));
    }

    @Override
    public void removeComment(Long weaponId, Long commentId) {
        weaponService.removeComment(weaponService.findById(weaponId),
                commentService.findById(commentId));
        commentService.deleteComment(commentService.findById(commentId));
    }

    @Override
    public void addEffectiveAgainst(Long weaponId, MonsterType m) {
        weaponService.addEffectiveAgainst(weaponService.findById(weaponId),
                m);
    }

    @Override
    public void removeEffectiveAgainst(Long weaponId, MonsterType m) {
        weaponService.removeEffectiveAgainst(weaponService.findById(weaponId),
                m);
    }

    @Override
    public List<WeaponDTO> getAllWeapons() {
        List<Weapon> weapons = weaponService.findAll();
        if (weapons == null) {
            return null;
        } else {
            return mappingService.mapTo(weapons, WeaponDTO.class);
        }
    }

    @Override
    public WeaponDTO getWeaponById(Long weaponId) {
        Weapon weapon = weaponService.findById(weaponId);
        if (weapon == null) {
            return null;
        } else {
            return mappingService.mapTo(weapon, WeaponDTO.class);
        }
    }

    @Override
    public WeaponDTO getWeaponByName(String name) {
        Weapon weapon = weaponService.findByName(name);
        if (weapon == null) {
            return null;
        } else {
            return mappingService.mapTo(weapon, WeaponDTO.class);
        }
    }
}
