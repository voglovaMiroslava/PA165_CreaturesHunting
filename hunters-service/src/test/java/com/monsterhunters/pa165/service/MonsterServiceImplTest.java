package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
public class MonsterServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private MonsterDao monsterDao;

    @Autowired
    @InjectMocks
    private MonsterService monsterService;

    private Monster monster1;
    private Monster monster2;
    private Monster monster3;
    private Monster monster4;
    private Monster monster5;

    private Location location1;
    private Location location2;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        location1 = new Location("Deadly desert", "Really deadly desert");
        location2 = new Location("High hills", "You never seen higher.");

        monster1 = createMonster("Drowner",
                location1,
                Arrays.asList(MonsterType.WATER, MonsterType.UNDEAD));
        monster2 = createMonster("Moon wraith",
                location1,
                Arrays.asList(
                        MonsterType.FIRE,
                        MonsterType.FAIRY,
                        MonsterType.UNDEAD));
        monster3 = createMonster("Skooma",
                location2,
                Arrays.asList(MonsterType.HYPNOTIC, MonsterType.UNDEAD));
        monster4 = createMonster("Lady of the Lake",
                location2,
                Arrays.asList(MonsterType.FAIRY, MonsterType.WATER));
        monster5 = createMonster("Lightning fox",
                location2,
                Arrays.asList(MonsterType.LIGHTNING));
    }

    @Test
    public void testCreateMonster() {
        Monster mon = monsterService.createMonster(monster3);
        verify(monsterDao).create(mon);
        Assert.assertEquals(mon, monster3);
    }

    @Test
    public void testDeleteMonster() {
        monsterService.deleteMonster(monster1);
        verify(monsterDao).delete(monster1);
    }

    @Test
    public void testUpdateMonster() {
        Monster mon = monsterService.updateMonster(monster1);
        verify(monsterDao).update(monster1);
        Assert.assertEquals(mon, monster1);
    }

    @Test
    public void testFindById() {
        when(monsterDao.findById(1l)).thenReturn(monster2);
        Monster mon = monsterService.findById(1l);
        Assert.assertEquals(mon, monster2);
    }

    @Test
    public void testFindByNonExistentId() {
        when(monsterDao.findById(22l)).thenReturn(null);
        Monster mon = monsterService.findById(22l);
        Assert.assertEquals(mon, null);
    }

    @Test
    public void testFindAll() {
        List<Monster> monsters = Arrays.asList(monster1, monster2, monster3, monster4, monster5);
        when(monsterDao.findAll()).thenReturn(monsters);
        List<Monster> findedMonsters = monsterService.findAll();
        Assert.assertEquals(findedMonsters, monsters);
    }

    @Test
    public void testAddType() {
        Monster monsterBefore = createMonster("No type monster", location1, new LinkedList<>());
        Monster monsterAfter = createMonster("No type monster", location1, Arrays.asList(MonsterType.FIRE));
        monsterService.addType(MonsterType.FIRE, monsterBefore);
        Assert.assertEquals(monsterBefore.getName(), monsterAfter.getName());
        Assert.assertEquals(monsterBefore.getLocation(), monsterAfter.getLocation());
        Assert.assertEquals(monsterBefore.getTypes(), monsterAfter.getTypes());
    }

    @Test
    public void testAddAlreadyHavingType() {
        monsterService.addType(MonsterType.LIGHTNING, monster5);
        Assert.assertEquals(monster5.getTypes(), Arrays.asList(MonsterType.LIGHTNING));
    }

    @Test
    public void testRemoveType() {
        Monster monsterBefore = createMonster("Monster with type", location1, Arrays.asList(MonsterType.FIRE));
        Monster monsterAfter = createMonster("Monster with type", location1, new LinkedList<>());
        monsterService.removeType(MonsterType.FIRE, monsterBefore);
        Assert.assertEquals(monsterBefore.getName(), monsterAfter.getName());
        Assert.assertEquals(monsterBefore.getLocation(), monsterAfter.getLocation());
        Assert.assertEquals(monsterBefore.getTypes(), monsterAfter.getTypes());
    }

    @Test
    public void testRemoveNotHavingType() {
        monsterService.removeType(MonsterType.FAIRY, monster5);
        Assert.assertEquals(monster5.getTypes(), Arrays.asList(MonsterType.LIGHTNING));
    }

    @Test
    public void testRelocateMonster() {
        Monster monst = createMonster("Traveling monster", location1, new LinkedList<>());
        monsterService.relocateMonster(location2, monst);
        Assert.assertEquals(monst.getLocation(), location2);
    }

    @Test
    public void testRelocateMonsterToSameLocation() {
        Monster monst = createMonster("Traveling monster", location1, new LinkedList<>());
        monsterService.relocateMonster(location1, monst);
        Assert.assertEquals(monst.getLocation(), location1);
    }

    @Test
    public void testGetOnlyOneLocationWithMostMonsterType() {
        when(monsterDao.findByType(MonsterType.LIGHTNING)).thenReturn(Arrays.asList(monster5));
        List<Location> locations = monsterService.getLocationsWithMostMonsterType(MonsterType.LIGHTNING);
        Assert.assertEquals(locations.size(), 1);
        Assert.assertEquals(locations.get(0), location2);
    }

    @Test
    public void testGetMoreLocationsWithMostMonsterType() {
        when(monsterDao.findByType(MonsterType.FAIRY)).thenReturn(Arrays.asList(monster2, monster4));
        List<Location> locations = monsterService.getLocationsWithMostMonsterType(MonsterType.FAIRY);
        Assert.assertEquals(locations.size(), 2);
        Assert.assertEquals(locations, Arrays.asList(location1,location2));
    }

    @Test
    public void testGetNoneLocationWithMostMonsterType() {
        when(monsterDao.findByType(MonsterType.METAHUMAN)).thenReturn(Arrays.asList());
        List<Location> locations = monsterService.getLocationsWithMostMonsterType(MonsterType.METAHUMAN);
        Assert.assertEquals(locations.size(), 0);
    }

    private Monster createMonster(String name, Location loc, List<MonsterType> types) {
        Monster mony = new Monster();
        mony.setLocation(loc);
        mony.setName(name);
        types.forEach((type) -> {
            mony.addType(type);
        });
        return mony;
    }
}
