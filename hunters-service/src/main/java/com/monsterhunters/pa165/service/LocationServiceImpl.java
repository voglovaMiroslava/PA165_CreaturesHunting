package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dao.LocationDaoImpl;
import com.monsterhunters.pa165.entity.Location;

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
@ComponentScan(basePackageClasses={LocationDaoImpl.class})
public class LocationServiceImpl implements LocationService {

    @Inject
    private LocationDao locationDao;

    @Override
    public Location findById(Long id) {
        return locationDao.findById(id);
    }

    @Override
    public List<Location> findAll() {
        return locationDao.findAll();
    }

    @Override
    public void create(Location location) {
        locationDao.create(location);
    }

    @Override
    public void remove(Location c) {
        locationDao.delete(c);
    }

    @Override
    public Location findByName(String locationName) {
        return locationDao.findByName(locationName);
    }

}
