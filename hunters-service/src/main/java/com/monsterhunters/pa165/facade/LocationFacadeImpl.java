package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.service.CommentService;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.LocationService;

import java.util.List;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomas Durcak
 */
@Service
@Transactional
public class LocationFacadeImpl implements LocationFacade {

    @Autowired
    private MappingService mappingService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CommentService commentService;

    @Override
    public List<LocationDTO> getAllLocations() {
        return mappingService.mapTo(locationService.findAll(), LocationDTO.class);
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        Location location = locationService.findById(id);
        return (location == null) ? null : mappingService.mapTo(location, LocationDTO.class);
    }

    @Override
    public Long createLocation(LocationCreateDTO locationCreateDTO) {
        Location location = new Location();
        location.setName(locationCreateDTO.getName());
        location.setDescription(locationCreateDTO.getDescription());
        locationService.createLocation(location);
        return location.getId();
    }

    @Override
    public Long updateLocation(LocationDTO locationDTO) {
        Location location = mappingService.mapTo(locationDTO, Location.class);
        Location updatedLocation = locationService.updateLocation(location);
        return updatedLocation.getId();
    }

    @Override
    public boolean deleteLocation(Long id) {
        return locationService.deleteLocation(locationService.findById(id));
    }

    @Override
    public void addComment(Long locationId, Long commentId) {
        locationService.addComment(locationService.findById(locationId),
                commentService.findById(commentId));
    }

    @Override
    public void removeComment(Long locationId, Long commentId) {
        locationService.removeComment(locationService.findById(locationId),
                commentService.findById(commentId));
        commentService.deleteComment(commentService.findById(commentId));
    }

    @Override
    public WeaponDTO getBestWeapon(Long locationId) {
        Weapon w = locationService.getBestWeapon(locationId);
        if (w == null)
            return null;
        else
            return mappingService.mapTo(w, WeaponDTO.class);
    }

    @Override
    public List<CommentDTO> getComments(Long locationId) {
        List<Comment> list = locationService.getComments(locationId);
        return mappingService.mapTo(list, CommentDTO.class);
    }

    @Override
    public List<MonsterDTO> getMonsters(Long locationId) {
        List<Monster> list = locationService.getMonsters(locationId);
        return mappingService.mapTo(list, MonsterDTO.class);
    }
}
