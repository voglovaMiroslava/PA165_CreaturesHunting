package com.monsterhunters.pa165.service.config;

import com.monsterhunters.pa165.dto.*;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

/**
 * @author Tomas Durcak
 */
public class EntityDTOMapping extends BeanMappingBuilder {

    @Override
    protected void configure() {

        mapping(Location.class, LocationDTO.class, TypeMappingOptions.mapNull(false));
        mapping(Monster.class, MonsterDTO.class, TypeMappingOptions.mapNull(false))
                .fields(field("types").accessible(true), field("types").accessible(true));
        mapping(Monster.class, MonsterCreateDTO.class, TypeMappingOptions.mapNull(false))
                .fields(field("types").accessible(true), field("types").accessible(true));
        mapping(Weapon.class, WeaponDTO.class, TypeMappingOptions.mapNull(false))
                .fields(field("effectiveAgainst").accessible(true), field("effectiveAgainst").accessible(true))
                .fields(field("comments").accessible(true), field("comments").accessible(true));
        mapping(Weapon.class, WeaponCreateDTO.class, TypeMappingOptions.mapNull(false))
                .fields(field("effectiveAgainst").accessible(true), field("effectiveAgainst").accessible(true))
                .fields(field("comments").accessible(true), field("comments").accessible(true));
        mapping(User.class, UserDTO.class, TypeMappingOptions.mapNull(false));
        mapping(Comment.class, CommentDTO.class, TypeMappingOptions.mapNull(false));

    }
}
