package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dao.LocationDaoImpl;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;

import java.util.List;

import javax.inject.Inject;

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
    public Location create(Location location) {
        if (locationDao.create(location)) {
            return location;
        }
        return null;
    }

    @Override
    public boolean remove(Location location) {
        if (location != null) {
            return locationDao.delete(location);
        }
        return false;
    }

    @Override
    public Location update(Location location) {
        locationDao.update(location);
        return location;
    }

    @Override
    public Location findById(Long id) {
        return locationDao.findById(id);
    }

    @Override
    public List<Location> findAll() {
        return locationDao.findAll();
    }

    @Override
    public Location findByName(String locationName) {
        return locationDao.findByName(locationName);
    }

    @Override
    public void addComment(Location location, Comment comment) {
        if (location.getComments().contains(comment)) {
            throw new HuntersServiceException("Same comment already exists for this location."
                    + " Weapon ID:" + location.getId() + " Comment ID:" + comment.getId());
        }
        location.addComment(comment);
    }

    @Override
    public void removeComment(Location location, Comment comment) {
        location.removeComment(comment);
    }

}
