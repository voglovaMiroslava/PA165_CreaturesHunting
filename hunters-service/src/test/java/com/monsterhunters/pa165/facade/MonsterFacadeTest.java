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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    private MonsterDao monsterDao;

    @Autowired
    @InjectMocks
    private MonsterService monsterService;

    @Autowired
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
        loc1 = new Location("Location 1", "Unique location");
        loc2 = new Location("Location 2", "Completely diferent location.");
    }

    @Test
    public void testCreateMonster() {
        MonsterCreateDTO m = new MonsterCreateDTO();
        m.setLocationId(2l);
        monsterFacade.createMonster(m);
        verify(monsterDao).create(any(Monster.class));
    }

    @Test
    public void testDeleteMonster() {
        Monster m = createMonster(1l, "Some poor mony", loc1);
        when(monsterDao.findById(1l)).thenReturn(m);
        monsterFacade.deleteMonster(1l);
        verify(monsterDao).delete(m);
    }

    @Test
    public void testUpdateMonster() {
        MonsterDTO mdto = createMonsterDTO(1l, "Mony to be updated", locDto1);
        Monster m = createMonster(1l, "Mony to be updated", loc1);
        monsterFacade.updateMonster(mdto);
        verify(monsterDao).update(m);
    }

    @Test
    public void testFindMonsterById() {
        Monster m = createMonster(22l, "Best monster that ever lived", loc2);
        when(monsterDao.findById(22l)).thenReturn(m);

        MonsterDTO mdto = createMonsterDTO(22l, "Best monster that ever lived", locDto2);
        MonsterDTO returnedMonster = monsterFacade.findById(22l);
        Assert.assertEquals(returnedMonster, mdto);
    }

    @Test
    public void testFindAllMonsters() {
        Monster m1 = createMonster(10l, "Furry monster", loc1);
        Monster m2 = createMonster(15l, "Cute monster", loc2);
        when(monsterDao.findAll()).thenReturn(Arrays.asList(m1, m2));
        MonsterDTO m1Dto = createMonsterDTO(10l, "Furry monster", locDto1);
        MonsterDTO m2Dto = createMonsterDTO(15l, "Cute monster", locDto2);

        List<MonsterDTO> allMonsters = monsterFacade.findAll();
        Assert.assertEquals(allMonsters, Arrays.asList(m1Dto, m2Dto));
    }

    @Test
    public void testAddTypeToMonster() {
        MonsterDTO mDto = createMonsterDTO(16l, "Flaming rose", locDto1);
        Monster m = createMonster(16l, "Flaming rose", loc1);
        m.addType(MonsterType.FIRE);
        monsterFacade.addType(MonsterType.FIRE, mDto);
        verify(monsterDao).update(m);
    }

    @Test
    public void testRemoveTypeFromMonster() {
        MonsterDTO mDto = createMonsterDTO(18l, "Cold rose", locDto1);
        mDto.setTypes(new HashSet<>(Arrays.asList(MonsterType.FIRE)));
        monsterFacade.removeType(MonsterType.FIRE, mDto);

        Monster m = createMonster(1l, "Flaming rose", loc1);
        verify(monsterDao).update(m);
    }

    @Test
    public void testRelocateMonster() {
        MonsterDTO mDto = createMonsterDTO(21l, "Traveling zombie", locDto1);
        monsterFacade.relocateMonster(locDto2, mDto);

        Monster m = createMonster(21l, "Traveling zombie", loc2);
        verify(monsterDao).update(m);
    }

    @Test
    public void testGetLocationsWithMostMonsterType() {
        Monster m1 = createMonster(189l, "Flaming bird", loc1);
        Monster m2 = createMonster(199l, "Flaming metahuman fairy", loc1);

        when(monsterDao.findByType(MonsterType.FAIRY)).thenReturn(Arrays.asList(m1, m2));
        List<LocationDTO> locations = monsterFacade.getLocationsWithMostMonsterType(MonsterType.FAIRY);
        
        Assert.assertEquals(locations.size(), 1);
        Assert.assertEquals(locations.get(0), locDto1);
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
