package com.monsterhunters.pa165.service.config;

import com.monsterhunters.pa165.dto.LocationDTO;
//import com.monsterhunters.pa165.dto.WeaponDTO;
//import com.monsterhunters.pa165.dto.MosnterDTO;

import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.Weapon;

import org.dozer.loader.api.BeanMappingBuilder;

// TODO: make mapping of monster...

/**
 *
 * @author Tomas Durcak
 */
public class EntityDTOMapping extends BeanMappingBuilder {

    @Override
    protected void configure() {
        mapping(Location.class, LocationDTO.class);
//        mapping(Monster.class, MonsterDTO.class);
//        mapping(Weapon.class, WeaponDTO.class);

    }
}
