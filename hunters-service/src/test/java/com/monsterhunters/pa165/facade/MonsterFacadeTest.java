package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterCreateDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.MonsterService;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Miroslava Voglova
 */
@ContextConfiguration(classes = MappingConfiguration.class)
public class MonsterFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private MonsterService monsterService;

    @Autowired
    @InjectMocks
    private MonsterFacade monsterFacade;

    private LocationDTO locDto1;
    private LocationDTO locDto2;
    private Location loc1;
    private Location loc2;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        locDto1 = new LocationDTO("Location 1", "Unique location");
        locDto2 = new LocationDTO("Location 2", "Completely diferent location.");
        Location loc1 = new Location("Location 1", "Unique location");
        Location loc2 = new Location("Location 2", "Completely diferent location.");
    }

    @Test
    public void testCreateMonster() {
        MonsterCreateDTO mon = new MonsterCreateDTO();
        mon.setLocation(locDto1);
        mon.setName("Scary monster");
        when(monsterService.createMonster(any(Monster.class))).thenReturn(createMonster(1l, "Scary monster", loc1));
        Long id = monsterFacade.createMonster(mon);

        Assert.assertEquals(id.longValue(), 1l);
    }

    @Test
    public void testDeleteMonster() {
        Monster m = createMonster(22l, "Creeper", loc1);
        when(monsterService.findById(22l)).thenReturn(m);
        
        monsterFacade.deleteMonster(22l);
        verify(monsterService).deleteMonster(any(Monster.class));
    }

    @Test
    public void testUpdateMonster() {
        MonsterDTO m = createMonsterDTO(3l, "Random updatable", locDto2);
        monsterFacade.updateMonster(m);
        verify(monsterService).updateMonster(any(Monster.class));
    }

    @Test
    public void testFindMonsterById() {
        Monster m = createMonster(200l, "Dragon", loc2);
        when(monsterService.findById(200l)).thenReturn(m);
        MonsterDTO returnedMon = monsterFacade.findById(200l);
        Assert.assertEquals(returnedMon.getId(), m.getId());
        Assert.assertEquals(returnedMon.getLocation().getName(), m.getLocation().getName());
        Assert.assertEquals(returnedMon.getName(), m.getName());
    }

    @Test
    public void testFindAllMonsters() {
    }

    @Test
    public void testAddTypeToMonster() {
    }

    @Test
    public void testRemoveTypeFromMonster() {
    }

    @Test
    public void testRelocateMonster() {
    }

    @Test
    public void testGetLocationsWithMostMonsterType() {
    }

    private MonsterDTO createMonsterDTO(Long id, String name, LocationDTO location) {
        MonsterDTO mons = new MonsterDTO();
        mons.setId(id);
        mons.setName(name);
        mons.setLocation(location);
        return mons;
    }

    private Monster createMonster(Long id, String name, Location location) {
        Monster mons = new Monster();
        mons.setId(id);
        mons.setName(name);
        mons.setLocation(location);
        return mons;
    }
}
