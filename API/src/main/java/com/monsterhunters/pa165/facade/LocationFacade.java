package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import java.util.List;
/**
 *
 * @author tomas durcak
 */
public interface LocationFacade {
    List<LocationDTO> getAllLocations();
    LocationDTO getLocationById(Long id);

    Long createLocation(LocationCreateDTO locationCreateDTO);
}
