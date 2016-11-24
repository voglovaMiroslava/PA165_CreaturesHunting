/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

/**
 *
 * @author Tomas Durcak
 */
@ContextConfiguration(classes = MappingConfiguration.class)
public class LocationServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private LocationDao locationDao;

    @Autowired
    @InjectMocks
    private LocationService locationService;

    private Comment comment;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        User userOne;
        userOne = new User("User1", "user1@user.com", "myPasswordHash", true);
        comment = new Comment();
        comment.setContent("This is comment one");
        comment.setUser(userOne);
    }

    /**
     * Test of findById method, of class LocationServiceImpl.
     */
    @Test
    public void testFindById() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        when(locationDao.create(any(Location.class))).thenReturn(true);
        locationService.createLocation(klin);

        when(locationDao.findById(any(Long.class))).thenReturn(klin);
        Location result = locationService.findById(1l);
        assertEquals(klin, result);
    }

    /**
     * Test of findAll method, of class LocationServiceImpl.
     */
    @Test
    public void testFindAll() {
        Location mutne = new Location("Mutne", "Pekna dedinka v udoli.");
        Location novot = new Location("Novot", "Hned za kopcom blizko dulova, very nice");
        Location breza = new Location("Breza", "Kedysi tam rastli brezy.");

        List<Location> expectedResult = new ArrayList<>();
        expectedResult.add(mutne);
        expectedResult.add(novot);
        expectedResult.add(breza);

        when(locationDao.create(any(Location.class))).thenReturn(true);
        locationService.createLocation(mutne);
        locationService.createLocation(breza);
        locationService.createLocation(novot);
        
        when(locationDao.findAll()).thenReturn(expectedResult);
        List<Location> foundLocations = locationService.findAll();
        Assert.assertEquals(expectedResult.size(), foundLocations.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            Assert.assertEquals(expectedResult.get(i), foundLocations.get(i));
        }
    }

    /**
     * Test of createLocation method, of class LocationServiceImpl.
     */
    @Test
    public void testCreate() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        when(locationDao.create(any(Location.class))).thenReturn(true);
        Location createdLocation = locationService.createLocation(klin);
        assertEquals(klin, createdLocation);
    }

    /**
     * Test of createLocation method, of class LocationServiceImpl.
     */
    @Test(expectedExceptions = HuntersServiceException.class)
    public void testCreateExistingLocation() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        when(locationDao.create(any(Location.class))).thenReturn(true);
        locationService.createLocation(klin);

        when(locationDao.create(any(Location.class))).thenReturn(false);
        locationService.createLocation(klin);
    }

    /**
     * Test of deleteLocation method, of class LocationServiceImpl.
     */
    @Test
    public void testRemove() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        when(locationDao.create(any(Location.class))).thenReturn(true);
        locationService.createLocation(klin);

        when(locationDao.delete(any(Location.class))).thenReturn(true);
        boolean result = locationService.deleteLocation(klin);
        assertEquals(true, result);
    }

    /**
     * Test of deleteLocation method, of class LocationServiceImpl.
     */
    @Test(expectedExceptions = HuntersServiceException.class)
    public void testRemoveNonExistingLocation() {
        when(locationDao.delete(any(Location.class))).thenReturn(false);
        Location lokca = new Location("Lokca", "Podpalaci tam byvaju.");
        locationService.deleteLocation(lokca);
    }

    public void testupdate() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        when(locationDao.create(any(Location.class))).thenReturn(true);
        locationService.createLocation(klin);

        klin.setDescription("Novy popis.");
        when(locationDao.update(any(Location.class))).thenReturn(klin);
        Location result = locationService.updateLocation(klin);
        assertEquals(klin, result);
    }

    /**
     * Test of findByName method, of class LocationServiceImpl.
     */
    @Test
    public void testFindByName() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        when(locationDao.findByName(any(String.class))).thenReturn(klin);
        Location result = locationService.findByName("Klin");
        assertEquals(klin, result);
    }

    /**
     * Test of findByName method, of class LocationServiceImpl.
     */
    @Test(expectedExceptions = HuntersServiceException.class)
    public void testFindByInvalidName() {
        when(locationDao.findByName(any(String.class))).thenReturn(null);
        Location result = locationService.findByName("WrongName");
        assertEquals(null, result);
    }

    /**
     * Test of addComment method, of class LocationServiceImpl.
     */
    @Test(expectedExceptions = HuntersServiceException.class)
    public void testAddDuplicateComment() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        locationService.addComment(klin, comment);
        locationService.addComment(klin, comment);
    }

    @Test
    public void testAddComment() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        assertEquals(0, klin.getComments().size());
        locationService.addComment(klin, comment);
        assertEquals(klin.getComments().size(), 1);
    }

    /**
     * Test of removeComment method, of class LocationServiceImpl.
     */
    @Test
    public void testRemoveComment() {
        Location klin = new Location("Klin", "Taka diera v lese.");
        locationService.addComment(klin, comment);
        Set<Comment> commentList = klin.getComments();
        assertEquals(commentList.size(), 1);
        locationService.removeComment(klin, comment);
        Set<Comment> commentList2 = klin.getComments();
        assertEquals(commentList2.size(), 0);
    }

    /**
     * Test of removeComment method, of class LocationServiceImpl.
     */
    @Test(expectedExceptions = HuntersServiceException.class)
    public void testRemoveNonExistingComment() {
        Location tapesovo = new Location("Tapesovo", "Hned za kopcom blizko dulova, very nice");
        locationService.removeComment(tapesovo, comment);
    }

}
