package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
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
     * Creates new location
     *
     * @param location object
     * @return Location object
     * @throws HuntersServiceException when an error occures
     */
    Location createLocation(Location location) throws HuntersServiceException;

    /**
     * Removes Location
     *
     * @param location object
     * @return true if removing succes
     * @throws HuntersServiceException when an error occures
     */
    boolean deleteLocation(Location location) throws HuntersServiceException;

    /**
     * Update Location
     *
     * @param location object
     * @return Location object
     * @throws HuntersServiceException when an error occures
     */
    Location updateLocation(Location location) throws HuntersServiceException;

    /**
     * Returns finding Location
     *
     * @param id Location id
     * @return Lcoation object of finding Location
     * @throws HuntersServiceException when an error occures
     */
    Location findById(Long id) throws HuntersServiceException;

    /**
     * Returns list of all locations
     *
     * @return list of Location objects
     * @throws HuntersServiceException when an error occures
     */
    List<Location> findAll() throws HuntersServiceException;

    /**
     * Returns finding Location
     *
     * @param locationName Location name
     * @return Location object of finding location
     * @throws HuntersServiceException when an error occures
     */
    Location findByName(String locationName) throws HuntersServiceException;

    /**
     * Add comment to list of comments of location
     *
     * @param location is location object
     * @param comment is a comment object which will be added to list
     * @throws HuntersServiceException when an error occures
     */
    void addComment(Location location, Comment comment) throws HuntersServiceException;

    /**
     * Remove comment from list of comments of location
     *
     * @param location is location object
     * @param comment is a comment object to removed from list
     * @throws HuntersServiceException when an error occures
     */
    void removeComment(Location location, Comment comment) throws HuntersServiceException;
}
