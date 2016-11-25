package com.monsterhunters.pa165.service.config;

import com.monsterhunters.pa165.dto.*;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.User;
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
        mapping(Monster.class, MonsterDTO.class).fields(field("types").accessible(true),field("types").accessible(true));
        mapping(Monster.class, MonsterCreateDTO.class).fields(field("types").accessible(true),field("types").accessible(true));
        mapping(Weapon.class, WeaponDTO.class).fields(field("effectiveAgainst").accessible(true),field("effectiveAgainst").accessible(true));
        mapping(Weapon.class, WeaponCreateDTO.class).fields(field("effectiveAgainst").accessible(true),field("effectiveAgainst").accessible(true));
        mapping(User.class, UserDTO.class);
        mapping(Comment.class, CommentDTO.class);
    }
}
