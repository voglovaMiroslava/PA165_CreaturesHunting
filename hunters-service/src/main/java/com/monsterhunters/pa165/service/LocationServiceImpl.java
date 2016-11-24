package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dao.LocationDaoImpl;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link LocationService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 *
 * @author Tomas Durcak
 */
@Service
@ComponentScan(basePackageClasses = {LocationDaoImpl.class})
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public Location createLocation(Location location) throws HuntersServiceException {
        if (locationDao.create(location)) {
            return location;
        } else {
            throw new HuntersServiceException("Cannot create location with id: " + location.getId() + " and name: " + location.getName());
        }
    }

    @Override
    public boolean deleteLocation(Location location) throws HuntersServiceException {
        if (locationDao.delete(location)) {
            return true;
        } else {
            throw new HuntersServiceException("Cannot delete location with id: " + location.getId() + " and name: " + location.getName());
        }
    }

    @Override
    public Location updateLocation(Location location) throws HuntersServiceException {
        try {
            locationDao.update(location);
            return location;
        } catch (Throwable ex) {
            throw new HuntersServiceException("Cannot update location with " + location.getId() + " id.", ex);
        }
    }

    @Override
    public Location findById(Long id) throws HuntersServiceException {
        try {
            return locationDao.findById(id);
        } catch (Throwable ex) {
            throw new HuntersServiceException("Cannot find location with " + id + " id.", ex);
        }
    }

    @Override
    public List<Location> findAll() throws HuntersServiceException {
        try {
            return locationDao.findAll();
        } catch (Throwable ex) {
            throw new HuntersServiceException("Cannot find list of all locations.", ex);
        }
    }

    @Override
    public Location findByName(String locationName) throws HuntersServiceException {
        Location result = locationDao.findByName(locationName);
        if (result != null) {
            return result;
        } else {
            throw new HuntersServiceException("Cannot find location with " + locationName + " name.");
        }
    }

    @Override
    public void addComment(Location location, Comment comment) throws HuntersServiceException {
        if (location.getComments().contains(comment)) {
            throw new HuntersServiceException("Same comment already exists for this location."
                    + " Weapon ID:" + location.getId() + " Comment ID:" + comment.getId());
        }
        location.addComment(comment);
    }

    @Override
    public void removeComment(Location location, Comment comment) {
        //location.removeComment(comment);
        if (!location.removeComment(comment)) {
            throw new HuntersServiceException("Cannot remove comment from location.");
        }
    }

}
