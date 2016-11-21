package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Miroslava Voglova
 */
public class MonsterServiceImpl implements MonsterService {

    @Autowired
    private MonsterDao monsterDao;

    @Override
    public void createMonster(Monster m) {
        monsterDao.create(m);
    }

    @Override
    public void deleteMonster(Monster m) {
        monsterDao.delete(m);
    }

    @Override
    public void updateMonster(Monster m) {
        monsterDao.delete(m);
    }

    @Override
    public Monster findById(Long id) {
        return monsterDao.findById(id);
    }

    @Override
    public List<Monster> findAll() {
        return monsterDao.findAll();
    }

    @Override
    public void addType(MonsterType type, Monster m) {
        m.addType(type);
        monsterDao.update(m);
    }

    @Override
    public void removeType(MonsterType type, Monster m) {
        m.removeType(type);
        monsterDao.update(m);
    }

    @Override
    public void relocateMonster(Location newLocation, Monster m) {
        if (m.getLocation().equals(newLocation)) {
            return;
        }
        m.setLocation(newLocation);
        monsterDao.update(m);
    }

    @Override
    public List<Location> getLocationsWithMostMonsterType(MonsterType type) {
        List<Monster> monsters = monsterDao.findAll();
        Map<Location, Integer> locations = new TreeMap<>();

        for (Monster m : monsters) {
            if (m.getTypes().contains(type)) {
                locations.put(m.getLocation(), locations.get(m.getLocation()) + 1);
            }
        }
        return getLocationsWithHighestValues(locations);
    }

    
    //TODO do it nicer
    private List<Location> getLocationsWithHighestValues(Map<Location, Integer> locations) {
        List<Location> locs = new LinkedList<>();
        SortedSet<Map.Entry<Location, Integer>> sortedEntries = new TreeSet<>(
                (Map.Entry<Location, Integer> e1, Map.Entry<Location, Integer> e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
        });
        sortedEntries.addAll(locations.entrySet());
        int maxValue = sortedEntries.first().getValue();
        for(Map.Entry<Location, Integer> sorted : sortedEntries){
            if(sorted.getValue() < maxValue){
                break;
            }
            locs.add(sorted.getKey());
        }
        return locs;
    }
}
