package com.monsterhunters.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.facade.LocationFacade;
import com.monsterhunters.pa165.rest.exceptions.ResourceAlreadyExistingException;
import com.monsterhunters.pa165.rest.exceptions.ResourceNotFoundException;
import com.monsterhunters.pa165.rest.tools.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.List;

/**
 * Location Controller
 *
 * @author Durcak Tomas
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_LOCATIONS)
public class LocationsController {
    final static Logger logger = LoggerFactory.getLogger(LocationsController.class);

    @Autowired
    private LocationFacade locationFacade;

    /**
     * Get list of all Locations GET
     * http://localhost:8080/rest/locations
     *
     * @return list of LocationDTO
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<LocationDTO> getLocations() throws JsonProcessingException {
        logger.debug("rest getLocations()");
        return locationFacade.getAllLocations();
    }

    /**
     * Get location by id GET
     * http://localhost:8080/rest/locations/1
     *
     * @param id is ID of location
     * @return LocationDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final LocationDTO getLocation(@PathVariable("id") long id) throws Exception {
        try {
            return locationFacade.getLocationById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Delete location by id DELETE
     * http://localhost:8080/rest/locations/1
     *
     * @param id of location
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteLocation(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteLocation({})", id);
        try {
            locationFacade.deleteLocation(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Create new location by POST
     * curl -X POST http://localhost:8080/rest/locations/create
     * -i -H "Content-Type: application/json" --data
     * '{"name":"test","description":"testing description"}'
     *
     * @param location
     * @return LocationDTO
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final LocationDTO createLocation(@RequestBody LocationCreateDTO location) throws Exception {

        logger.debug("rest createLocation()");

        try {
            Long id = locationFacade.createLocation(location);
            return locationFacade.getLocationById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     *
     * @param id
     * @param comment
     * @return LocationDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final LocationDTO addComment(@PathVariable("id") long id, @RequestBody CommentDTO comment) throws Exception {
        logger.debug("rest addComment({)", id);
        try {
            locationFacade.addComment(id, comment.getId());
            return locationFacade.getLocationById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**
     *
     * @param id
     * @param commentId
     * @return LocationDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/comments/{cId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final LocationDTO removeComment(@PathVariable(value="id") long id, @PathVariable(value="cId") long commentId) throws Exception {
        logger.debug("rest removeComment({)", id);
        try {
            locationFacade.removeComment(id, commentId);
            return locationFacade.getLocationById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**
     *
     * @param id
     * @return WeaponDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/bestWeapon", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO getBestWeapon(@PathVariable("id") long id) throws Exception {
        try {           
            return locationFacade.getBestWeapon(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
}
