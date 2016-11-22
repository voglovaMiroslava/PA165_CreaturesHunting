/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import java.util.ArrayList;
import java.util.List;
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

    private Location mutne;
    private Location novot;
    private Location mutneCopy;
    private Location klin;
    private Location breza;
    private Location lomna;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        mutne = new Location("Mutne", "Pekna dedinka v udoli.");
        mutneCopy = mutne;
        novot = new Location("Novot", "Hned za kopcom blizko dulova, very nice");
        klin = new Location("Klin", "Taka diera v lese.");
        breza = new Location("Breza", "Kedysi tam rastli brezy.");
        lomna = new Location("Lomna", "Tam kde sa muchy otacaju.");
    }

    /**
     * Test of findById method, of class LocationServiceImpl.
     */
    @Test
    public void testFindById() {
        when(locationDao.findById(any(Long.class))).thenReturn(mutneCopy);
        Location result = locationService.findById(1l);
        assertEquals(mutneCopy, result);
    }

    /**
     * Test of findAll method, of class LocationServiceImpl.
     */
    @Test
    public void testFindAll() {
        List<Location> expectedResult = new ArrayList<>();
        expectedResult.add(this.mutne);
        expectedResult.add(this.novot);
        expectedResult.add(this.klin);
        expectedResult.add(this.breza);
        when(locationDao.findAll()).thenReturn(expectedResult);
        List<Location> foundLocations = locationService.findAll();
        Assert.assertEquals(expectedResult.size(), foundLocations.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            Assert.assertEquals(expectedResult.get(i), foundLocations.get(i));
        }
    }

    /**
     * Test of create method, of class LocationServiceImpl.
     */
    @Test
    public void testCreate() {
        when(locationDao.create(any(Location.class))).thenReturn(true);
        Location createdLocation = locationService.create(mutne);
        assertEquals(mutne, createdLocation);
    }

    /**
     * Test of create method, of class LocationServiceImpl.
     */
    @Test
    public void testCreateExistingLocation() {
        when(locationDao.create(any(Location.class))).thenReturn(false);
        Location createdLocation = locationService.create(mutne);
        assertEquals(null, createdLocation);
    }

    /**
     * Test of remove method, of class LocationServiceImpl.
     */
    @Test
    public void testRemove() {
        when(locationDao.delete(any(Location.class))).thenReturn(true);
        boolean result = locationService.remove(lomna);
        assertEquals(true, result);
    }

    @Test
    public void testupdate() {
        lomna.setDescription("Novy popis.");
        when(locationDao.update(any(Location.class))).thenReturn(lomna);
        Location result = locationService.update(lomna);
        assertEquals(lomna, result);
    }

    /**
     * Test of remove method, of class LocationServiceImpl.
     */
    @Test
    public void testRemoveNonExistingoLocation() {
        when(locationDao.delete(any(Location.class))).thenReturn(false);
        boolean result = locationService.remove(lomna);
        assertEquals(false, result);
    }

    /**
     * Test of findByName method, of class LocationServiceImpl.
     */
    @Test
    public void testFindByName() {
        when(locationDao.findByName(any(String.class))).thenReturn(novot);
        Location result = locationService.findByName("Novot");
        assertEquals(novot, result);
    }

    /**
     * Test of addComment method, of class LocationServiceImpl.
     */
    @Test
    public void testAddComment() {
        // TO DO
    }

    /**
     * Test of removeComment method, of class LocationServiceImpl.
     */
    @Test
    public void testRemoveComment() {
        // TO DO
    }

}
