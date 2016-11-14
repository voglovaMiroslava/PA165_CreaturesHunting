package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.LocationService;

import java.util.List;
import javax.inject.Inject;


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

    @Inject
    private MappingService mappingService;

    @Inject
    private LocationService locationService;

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

}
