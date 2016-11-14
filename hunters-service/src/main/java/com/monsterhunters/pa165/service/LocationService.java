package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.Location;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * An interface that defines a service access to the {@link Location} entity.
 *
 * @author Tomas Durcak
 */
@Service
public interface LocationService {

    /**
     * Returns finding Location
     *
     * @param id Location id
     * @return Lcoation object of finding Location
     */
    Location findById(Long id);

    /**
     * Returns list of all locations
     *
     * @return list of Location objects
     */
    List<Location> findAll();

    /**
     * Creates new location
     *
     * @param location object
     */
    void create(Location location);

    /**
     * Removes Location
     *
     * @param location object
     */
    void remove(Location location);

    /**
     * Returns finding Location
     *
     * @param locationName Location name
     * @return Location object of finding location
     */
    Location findByName(String locationName);
}
