package com.monsterhunters.pa165.service.config;

import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author Tomas Durcak
 */
public class EntityDTOMapping extends BeanMappingBuilder {

    @Override
    protected void configure() {
        mapping(Location.class, LocationDTO.class);
        mapping(Monster.class, MonsterDTO.class);
        mapping(Weapon.class, WeaponDTO.class);
        mapping(User.class, UserDTO.class);
        mapping(Comment.class, CommentDTO.class);
    }
}
