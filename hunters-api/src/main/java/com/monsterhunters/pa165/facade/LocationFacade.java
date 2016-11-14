package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import java.util.List;

/**
 * LocationFacade interface
 *
 * @author tomas durcak
 */
public interface LocationFacade {

    /**
     * Returns list of all locations
     *
     * @return list of LocationDTO objects
     */
    List<LocationDTO> getAllLocations();

    /**
     * Returns LostaionDTO with id
     *
     * @param id LocationDTO id
     * @return LocationDTO object
     */
    LocationDTO getLocationById(Long id);

    /**
     * Create LocationDTO object
     *
     * @param locationCreateDTO
     * @return locationDTO id
     */
    Long createLocation(LocationCreateDTO locationCreateDTO);
}
