package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

/**
 * Tests of MonsterDaoIml
 *
 * @author Tomas Durcak
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MonsterDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    MonsterDao monsterDao;

    @Autowired
    LocationDao locationDao;

    private Monster dragon;

    private Location loc;

    @BeforeMethod
    public void setUpClass() {
        loc = new Location();
        loc.setName("Island");
        loc.setDescription("Cold and wet.");

        locationDao.create(loc);
        Assert.assertNotNull(locationDao.findById(loc.getId()));

        dragon = new Monster();
        dragon.setName("Dragon");
        dragon.setLocation(loc);
        dragon.addType(MonsterType.FIRE);

        monsterDao.create(dragon);
        Assert.assertNotNull(monsterDao.findById(dragon.getId()));
    }

    /**
     * Test of findByName method, of monsterDao class.
     */
    @Test
    public void testFindById() {
        Monster found = monsterDao.findById(dragon.getId());
        Assert.assertEquals(found.getName(), "Dragon");
        Assert.assertEquals(dragon, found);
    }

    /**
     * Test of findByName method, of monsterDao class.
     */
    @Test
    public void testFindByName() {
        Assert.assertNotNull(monsterDao.findByName("Dragon"));
        Assert.assertEquals(monsterDao.findByName("Dragon"), dragon);
        Assert.assertNull(monsterDao.findByName("asdffsfsd"));
    }

    /**
     * Test of create method, of monsterDao class.
     */
    @Test
    public void testCreate() {
        Monster troll = createTroll();
        monsterDao.create(troll);

        Monster sameMonster = monsterDao.findById(troll.getId());
        Assert.assertEquals(troll, sameMonster);
    }

    /**
     * Test of update method, of monsterDao class.
     */
    @Test
    public void testUpdate() {
        dragon.setName("drago");
        monsterDao.update(dragon);

        Monster sameMonster = monsterDao.findByName("drago");
        Assert.assertNotNull(sameMonster);
        Assert.assertEquals(sameMonster.getId(), dragon.getId());
    }

    /**
     * Test of delete method, of monsterDao class.
     */
    @Test
    public void testDelete() {
        Assert.assertNotNull(monsterDao.findById(dragon.getId()));
        monsterDao.delete(dragon);
        Assert.assertNull(monsterDao.findById(dragon.getId()));
    }

    /**
     * Test of findAll method, of monsterDao class.
     */
    @Test
    public void testFindAll() {
        Monster troll = createTroll();
        monsterDao.create(troll);

        List<Monster> foundMonsters = monsterDao.findAll();

        List<Monster> expectedResult = new ArrayList();
        expectedResult.add(dragon);
        expectedResult.add(troll);

        Assert.assertEquals(foundMonsters.size(), expectedResult.size());
        Assert.assertTrue(expectedResult.containsAll(foundMonsters));
    }

    @Test
    public void testFindByTypeExistent() {
        prepareTestTypeData();
        List<Monster> monsters = monsterDao.findByType(MonsterType.FIRE);
        Assert.assertEquals(monsters.size(), 2);
        for (Monster monster : monsters) {
            Assert.assertTrue(monster.getName().equals("Fire lady")
                    || monster.getName().equals("Dragon"));
        }
    }

    @Test
    public void testFindByTypeNonExistent() {
        List<Monster> monsters = monsterDao.findByType(MonsterType.FAIRY);
        Assert.assertEquals(monsters.size(), 0);
    }

    private void prepareTestTypeData() {
        Location loca = new Location();
        loca.setName("Desert");
        loca.setDescription("Cold and dry.");
        em.persist(loca);

        Monster mony = new Monster();
        mony.setName("Fire lady");
        mony.setLocation(loca);
        mony.addType(MonsterType.FIRE);
        em.persist(mony);
    }

    private Monster createTroll() {
        Monster troll = new Monster();
        troll.setName("Troll");
        troll.setLocation(loc);
        troll.addType(MonsterType.GROUND);
        return troll;
    }
}
