package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.service.LocationService;
import com.monsterhunters.pa165.service.MappingService;
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
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Tomas Durcak
 */
@ContextConfiguration(classes = MappingConfiguration.class)
public class LocationFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private LocationDao locationDao;

    @Autowired
    private LocationFacade locationFacade;

    @Autowired
    @InjectMocks
    private LocationService locationService;

    @Autowired
    private MappingService mappingService;

    private Location mutne;
    private LocationCreateDTO mutneCreateDTO;

    private Location novot;
    private Location mutneCopy;
    private Location klin;
    private Location breza;
    private Location lomna;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        mutne = new Location("Mutne", "Pekna dedinka v udoli.");
        
        mutneCreateDTO = new LocationCreateDTO();
        mutneCreateDTO.setName(mutne.getName());
        mutneCreateDTO.setDescription(mutne.getDescription());

        mutneCopy = mutne;
        novot = new Location("Novot", "Hned za kopcom blizko dulova, very nice");
        klin = new Location("Klin", "Taka diera v lese.");
        breza = new Location("Breza", "Kedysi tam rastli brezy.");

    }

    /**
     * Test of createLocation method, of class LocationFacadeImpl.
     */
    @Test
    public void testCreateLocation() {
        when(locationDao.create(any(Location.class))).thenReturn(true);
        when(locationDao.findById(any(Long.class))).thenReturn(mutne);
        Long createdLocationId = locationFacade.createLocation(mutneCreateDTO);
        assertEquals(null, createdLocationId);
    }

    /**
     * Test of getAllLocations method, of class LocationFacadeImpl.
     */
    @Test
    public void testGetAllLocations() {
        List<Location> expectedResult = new ArrayList<>();
        expectedResult.add(this.mutne);
        expectedResult.add(this.novot);
        expectedResult.add(this.klin);
        expectedResult.add(this.breza);
        when(locationDao.findAll()).thenReturn(expectedResult);
        List<LocationDTO> foundLocations = locationFacade.getAllLocations();
        Assert.assertEquals(expectedResult.size(), foundLocations.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            Assert.assertEquals(mappingService.mapTo(expectedResult.get(i), LocationDTO.class), foundLocations.get(i));
        }
    }

    /**
     * Test of getLocationById method, of class LocationFacadeImpl.
     */
    @Test
    public void testGetLocationById() {
        when(locationDao.findById(any(Long.class))).thenReturn(mutneCopy);
        LocationDTO result = locationFacade.getLocationById(1l);
        assertEquals(mappingService.mapTo(mutneCopy, LocationDTO.class), result);
    }

    /**
     * Test of addComment method, of class LocationFacadeImpl.
     */
    @Test
    public void testAddComment() {

        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of removeComment method, of class LocationFacadeImpl.
     */
    @Test
    public void testRemoveComment() {

        // TODO review the generated test code and remove the default call to fail.
    }

}
