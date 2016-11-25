package com.monsterhunters.pa165.service;

import java.util.Collection;
import java.util.List;

import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Weapon;
import org.dozer.Mapper;


public interface MappingService {
	
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
    Mapper getMapper();
    Weapon mapWeaponFromCreate(WeaponCreateDTO dto);
    Weapon mapWeaponFromDTO(WeaponDTO dto);
}