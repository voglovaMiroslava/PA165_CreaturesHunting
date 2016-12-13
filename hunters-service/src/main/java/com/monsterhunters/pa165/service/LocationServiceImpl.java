package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dao.LocationDaoImpl;
import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

    @Autowired
    private WeaponDao weaponDao;

    @Override
    public Location createLocation(Location location) throws HuntersServiceException {
        try {     
            if(!locationDao.create(location))
                throw new HuntersServiceException("Location with id: " + location.getId() + " and name: " + location.getName() + " already exists.");
            return location;
        } catch (Throwable ex) {
            throw new HuntersServiceException("Cannot create location with id: " + location.getId() + " and name: " + location.getName());
        }
    }

    @Override
    public boolean deleteLocation(Location location) throws HuntersServiceException {
        try {
        if (!locationDao.delete(location)) 
            throw new HuntersServiceException("Location not exists, cannot be deleted.");
            return true;
        } catch (Throwable ex) {
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
                    + " Location ID:" + location.getId() + " Comment ID:" + comment.getId());
        }
        location.addComment(comment);
    }

    @Override
    public void removeComment(Location location, Comment comment) {
        if (!location.removeComment(comment)) {
            throw new HuntersServiceException("Cannot remove comment from location.");
        }
    }

    @Override
    public Weapon getBestWeapon(Long locationId) {
        Location location = locationDao.findById(locationId);
        List<Monster> monsterList = locationDao.getMonstersWithLocation(location);
        List<Weapon> weaponList = weaponDao.findAll();

        if (monsterList.size() <= 0 || weaponList.size() <= 0) {
            return null;
        }

        Map<Weapon, Integer> weaponMap = new HashMap<>();

        for (Weapon w : weaponList) {
            Set<MonsterType> setWithTypes = w.getEffectiveAgainst();
            Integer kills = 0;

            for (Monster m : monsterList) {
                Set<MonsterType> mTypes = m.getTypes();
                Set<MonsterType> common = new HashSet<MonsterType>(mTypes);
                common.retainAll(setWithTypes);
                kills += common.size();
            }
            weaponMap.put(w, kills);
        }
        List<Weapon> bestWeapons = new ArrayList<Weapon>();

        int maxValueInMap = (Collections.max(weaponMap.values()));
        for (Entry<Weapon, Integer> entry : weaponMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                bestWeapons.add(entry.getKey());
            }
        }
        Weapon best = bestWeapons.get(0);

        for (Weapon w : bestWeapons) {
            if (w.getDamage() > best.getDamage()) {
                best = w;
            }
        }

        return best;
    }

    @Override
    public List<Comment> getComments(Long locationId) throws HuntersServiceException {
        try {
            List<Comment> list = new ArrayList<Comment>(locationDao.findById(locationId).getComments());
            return list;
        } catch (Throwable ex) {
            throw new HuntersServiceException("Cannot find list of all comments.", ex);
        }
    }

    @Override
    public List<Monster> getMonsters(Long locationId) throws HuntersServiceException {
        try {
            Location location = locationDao.findById(locationId);
            return locationDao.getMonstersWithLocation(location);
        } catch (Throwable ex) {
            throw new HuntersServiceException("Cannot find list of all monsters.", ex);
        }
    }
}
