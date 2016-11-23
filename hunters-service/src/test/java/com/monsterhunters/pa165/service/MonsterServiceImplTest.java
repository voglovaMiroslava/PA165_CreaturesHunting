package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import java.util.Arrays;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
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

    }

    @Test
    public void testCreateNotValidMonster() {

    }

    @Test
    public void testDeleteMonster() {
    }

    @Test
    public void testDeleteNonExistentMonster() {
    }

    @Test
    public void testUpdateMonster() {
    }

    @Test
    public void testFindById() {
    }

    @Test
    public void testFindByNonExistentId() {
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void testAddType() {
    }

    @Test
    public void testAddAlreadyHavingType() {
    }

    @Test
    public void testRemoveType() {
    }

    @Test
    public void testRemoveNotHavingType() {
    }

    @Test
    public void testRelocateMonster() {
    }

    @Test
    public void testRelocateMonsterToSameLocation() {
    }

    @Test
    public void testGetOnlyOneLocationWithMostMonsterType() {
    }

    @Test
    public void testGetMoreLocationsWithMostMonsterType() {

    }

    @Test
    public void testGetNoneLocationWithMostMonsterType() {

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
