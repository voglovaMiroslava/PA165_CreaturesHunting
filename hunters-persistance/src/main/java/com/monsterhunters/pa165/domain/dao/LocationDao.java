package com.monsterhunters.pa165.domain.dao;

import com.monsterhunters.pa165.domain.entity.Location;
//import com.monsterhunters.pa165.domain.enums.MonsterType;
import java.util.List;

/**
 * LocationDao
 *
 * @author Tomas Durcak
 */
public interface LocationDao {

    /**
     * Returns location with specific id
     *
     * @param id id of location
     * @return the location with the specified id
     */
    public Location findById(Long id);

    /**
     * Returns location with specific name
     *
     * @param name name of location
     * @return the location with the specified name
     */
    public Location findByName(String name);

    /**
     * Returns list of all locations
     *
     * @return the list of all locations
     */
    public List<Location> findAll();

    /**
     * Create new location
     *
     * @param location the instance of new location
     */
    public void create(Location location);

    /**
     * Delete location
     *
     * @param location the instance to delete
     */
    public void delete(Location location);

    /**
     * Update location
     *
     * @param location the instance to update
     */
    public void update(Location location);

}
