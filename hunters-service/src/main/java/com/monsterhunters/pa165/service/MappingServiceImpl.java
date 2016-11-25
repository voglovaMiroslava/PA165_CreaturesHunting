package com.monsterhunters.pa165.service;

import java.util.*;

import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Weapon;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingServiceImpl implements MappingService {

    @Autowired
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u, mapToClass);
    }

    @Override
    public Weapon mapWeaponFromCreate(WeaponCreateDTO dto){
        Weapon w = new Weapon();
        w.setEffectiveAgainst(dto.getEffectiveAgainst());
        w.setAmmo(dto.getAmmo());
        w.setDamage(dto.getDamage());
        w.setGunReach(dto.getGunReach());
        w.setName(dto.getName());
        return w;
    }

    @Override
    public Weapon mapWeaponFromDTO(WeaponDTO dto){
        Weapon w = new Weapon();
        w.setEffectiveAgainst(dto.getEffectiveAgainst());
        w.setAmmo(dto.getAmmo());
        w.setDamage(dto.getDamage());
        w.setGunReach(dto.getGunReach());
        w.setName(dto.getName());
        w.setId(dto.getId());
        Set<CommentDTO> SetCommentDTOS = dto.getComments();
        List<Comment> mappedComments = mapTo(SetCommentDTOS, Comment.class);
        Set<Comment> commentsSet = new HashSet<>(mappedComments);
        w.setComments(commentsSet);
        return w;
    }


    @Override
    public Mapper getMapper() {
        return dozer;
    }
}
