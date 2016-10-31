package com.monsterhunters.pa165.testdao;

import com.monsterhunters.pa165.config.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.domain.dao.LocationDao;
import com.monsterhunters.pa165.domain.dao.MonsterDao;
import com.monsterhunters.pa165.domain.entity.Location;
import com.monsterhunters.pa165.domain.entity.Monster;
import com.monsterhunters.pa165.domain.enums.MonsterType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

import java.util.List;


/**
 * Created by babcang on 24.10.2016.
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LocationDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private MonsterDao monsterDao;

    private Location location1;
    private Location location2;

    private Monster monster1;
    private Monster monster2;

    @BeforeMethod
    public void createLocations() throws Exception {
        location1 = createLocation("Location1", "This is Location1");
        location2 = createLocation("Location2", "This is Location2");

        locationDao.create(location1);
        locationDao.create(location2);

        monster1 = createMonster("Trumper", MonsterType.DRAGON);
        monster2 = createMonster("Fiko", MonsterType.HYPNOTIC);
        monster2.addType(MonsterType.FIRE);
        monster1.setLocation(location1);
        monster2.setLocation(location2);

        monsterDao.create(monster1);
        monsterDao.create(monster2);
    }

    @Test
    public void shouldCreateNewLocation() throws Exception {
        Location location = createLocation("Location", "New Location created for testing");
        locationDao.create(location);

        assertEquals(locationDao.findAll().size(), 3);
        assertTrue(locationDao.findAll().contains(location));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void shouldNotAllowNullLocationName() throws Exception {
        Location location = createLocation(null, "This has same name as Location1");
        locationDao.create(location);

        assertEquals(locationDao.findAll().size(), 3);
        assertTrue(locationDao.findAll().contains(location));
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void shoutNotCreateExistingLocation() throws Exception {
        Location location = createLocation("Location2", "This is Location1");
        locationDao.create(location);
    }

    @Test
    public void shouldUpdateNewLocation() throws Exception {
        Location location = locationDao.findById(location1.getId());
        location.setDescription("This is new description of Location1");
        locationDao.update(location);

        Location updatedLocation;
        updatedLocation = locationDao.findById(location.getId());
        assertTrue(locationDao.findAll().contains(updatedLocation));
        assertEquals(location, location1);
        assertEquals(location, updatedLocation);
        assertEquals(updatedLocation.getDescription(), "This is new description of Location1");
    }

    @Test
    public void shouldDeleteNewCreatedLocation() throws Exception {
        Location location = createLocation("Location", "This is Location");
        locationDao.create(location);
        assertNotNull(locationDao.findById(location.getId()));
        assertEquals(locationDao.findAll().size(), 3);
        locationDao.delete(location);
        assertNull(locationDao.findById(location.getId()));
        assertEquals(locationDao.findAll().size(), 2);
    }

    @Test
    public void shouldDeleteLocationAssignedToMonster() throws Exception {
        Location loc;
        loc = locationDao.findById(location1.getId());
        assertNotNull(locationDao.findById(loc.getId()));
        locationDao.delete(loc);
        assertNull(monsterDao.findById(monster1.getId()));
        assertNull(locationDao.findById(loc.getId()));
        assertEquals(locationDao.findAll().size(), 1);
        assertTrue(locationDao.findAll().contains(location2));
        assertFalse(locationDao.findAll().contains(location1));
    }

    @Test
    public void shouldFindById() throws Exception {
        Location location = locationDao.findById(location1.getId());
        assertEquals(location, location1);
        assertEquals(location.getName(), "Location1");
        assertEquals(location.getDescription(), "This is Location1");
    }

    @Test
    public void shouldFindByNonExistingId() throws Exception {
        Location location = locationDao.findById((long) 0);
        assertEquals(location, null);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void shouldFindByNullId() throws Exception {
        Location location = locationDao.findById(null);
        assertEquals(location, null);
    }

    @Test
    public void shouldFindByName() throws Exception {
        Location location = locationDao.findByName(location2.getName());
        assertEquals(location, location2);
        assertEquals(location.getName(), "Location2");
        assertEquals(location.getDescription(), "This is Location2");
    }

    @Test
    public void shouldFindByNonExistingName() throws Exception {
        Location location = locationDao.findByName("Milka doesn't exist");
        assertEquals(location, null);
    }

    @Test
    public void shouldFindByNullName() throws Exception {
        Location location = locationDao.findByName(null);
        assertEquals(location, null);
    }

    @Test
    public void shouldFindAll() {
        Location location = createLocation("Location3", "This is Location3");
        locationDao.create(location);
        List<Location> locations = locationDao.findAll();

        assertEquals(locations.size(), 3);
        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));
        assertTrue(locations.contains(location));
    }

    /**
     * This method creates new instance of Location
     * @param name is used to set name of location
     * @param description is used to set description of location
     * @return instance of location with assigned parameters
     */
    private static Location createLocation(String name, String description) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        return location;
    }

    /**
     * This method creates new instance of Monster
     * @param name is used to set name of monster
     * @param monsterType is used to addType of monster
     * @return instance of monster with assigned parameters
     */
    private static Monster createMonster(String name, MonsterType monsterType) {
        Monster monster = new Monster();
        monster.setName(name);
        monster.addType(monsterType);
        monster.setHeight(1.91);
        monster.setWeight(19.1);
        monster.setPower(191);
        return monster;
    }

}
