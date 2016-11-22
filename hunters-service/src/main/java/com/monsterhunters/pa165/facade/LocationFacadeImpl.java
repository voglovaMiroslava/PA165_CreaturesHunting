package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.dto.LocationDTO;
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
        locationService.create(location);
        return location.getId();
    }

    @Override
    public boolean removeLocation(Long id) {
        return locationService.remove(locationService.findById(id));
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

}
