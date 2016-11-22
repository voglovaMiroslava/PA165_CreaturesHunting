package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.Comment;
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
     * @return Location object
     */
    Location create(Location location);

    /**
     * Removes Location
     *
     * @param location object
     * @return true if removing succes
     */
    boolean remove(Location location);

    /**
     * Update Location
     *
     * @param location object
     * @return Location object
     */
    Location update(Location location);

    /**
     * Returns finding Location
     *
     * @param locationName Location name
     * @return Location object of finding location
     */
    Location findByName(String locationName);

    /**
     * Add comment to list of comments of location
     *
     * @param location is location object
     * @param comment is a comment object which will be added to list
     */
    void addComment(Location location, Comment comment);

    /**
     * Remove comment from list of comments of location
     *
     * @param location is location object
     * @param comment is a comment object to removed from list
     */
    void removeComment(Location location, Comment comment);
}
