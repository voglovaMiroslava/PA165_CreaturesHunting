package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miroslava Voglova
 */
@Service
public class MonsterServiceImpl implements MonsterService {

    @Autowired
    private MonsterDao monsterDao;

    @Override
    public Monster createMonster(Monster m) {
        if (m == null) {
            throw new IllegalArgumentException("Monster should not be null");
        }
        try {
            monsterDao.create(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while creating monster.", e);
        }
        return m;
    }

    @Override
    public void deleteMonster(Monster m) {
        if (m == null) {
            throw new IllegalArgumentException("Monster should not be null");
        }
        try {
            monsterDao.delete(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while deleting monster.", e);
        }
    }

    @Override
    public Monster updateMonster(Monster m) {
        if (m == null) {
            throw new IllegalArgumentException("Monster should not be null");
        }
        try {
            monsterDao.update(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while updating monster.", e);
        }
        return m;
    }

    @Override
    public Monster findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id should not be null");
        }
        try {
            return monsterDao.findById(id);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot find monster with " + id + " id.", e);
        }
    }

    @Override
    public List<Monster> findAll() {
        try {
            return monsterDao.findAll();
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while getting all monsters.", e);
        }
    }

    @Override
    public void addType(MonsterType type, Monster m) {
        if (m == null) {
            throw new IllegalArgumentException("Monster should not be null");
        }
        if (m.getTypes().contains(type)) {
            throw new HuntersServiceException("Monster already has given type.");
        }

        m.addType(type);
        try {
            monsterDao.update(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while adding type to monster with id " + m.getId(), e);
        }
    }

    @Override
    public void removeType(MonsterType type, Monster m) {
        if (m == null) {
            throw new IllegalArgumentException("Monster should not be null");
        }

        if (!m.getTypes().contains(type)) {
            throw new HuntersServiceException("Monster does not have given type.");
        }

        m.removeType(type);

        try {
            monsterDao.update(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while removing type from monster with id " + m.getId(), e);
        }
    }

    @Override
    public void relocateMonster(Location newLocation, Monster m) {
        if (m == null) {
            throw new IllegalArgumentException("Monster should not be null");
        }
        
        if (m.getLocation().equals(newLocation)) {
            throw new HuntersServiceException("Monster already in location.");
        }
        m.setLocation(newLocation);
        
        try {
            monsterDao.update(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Exception was thrown while relocating monster with id " + m.getId(), e);
        }
    }

    @Override
    public List<Location> getLocationsWithMostMonsterType(MonsterType type) {
        List<Monster> monsters = monsterDao.findByType(type);
        Map<Location, Integer> locations = new HashMap<>();

        for (Monster m : monsters) {
            Integer valueInMap = locations.get(m.getLocation());
            locations.put(m.getLocation(), valueInMap == null ? 0 : valueInMap + 1);
        }
        
        List<Location> mostLocation = new LinkedList<>();
        if (locations.isEmpty()) {
            return mostLocation;
        }

        int highestCount = Collections.max(locations.values());

        for (Location loc : locations.keySet()) {
            if (locations.get(loc).equals(highestCount)) {
                mostLocation.add(loc);
            }
        }

        return mostLocation;
    }
}
