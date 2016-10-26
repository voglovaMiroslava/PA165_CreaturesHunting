package com.monsterhunters.pa165.testdao;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceException;
//import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
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
public class MonsterDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    MonsterDao monsterDao;

    @Autowired
    LocationDao locationDao;

    private Monster dragon;
    private Monster troll;
    private Location loc;

    @BeforeMethod
    public void setUpClass() {
        dragon = new Monster();
        troll = new Monster();
        loc = new Location();

        loc.setName("Island");
        loc.setDescription("Cold and wet.");
        locationDao.create(loc);

        // loc.addMonster(troll);
        dragon.setHeight(120.45);
        dragon.setName("Dragon");
        dragon.setPower(1000);
        dragon.setWeight(8000.0);
        dragon.setLocation(loc);
        dragon.addType(MonsterType.FIRE);

        troll.setName("Troll");
        troll.setHeight(80.5);
        troll.setWeight(542.0);
        troll.setPower(200);
        troll.setLocation(loc);
        troll.addType(MonsterType.GROUND);

        monsterDao.create(dragon);
        //loc.addMonster(dragon);
    }

    /**
     * Test of findByName method, of monsterDao class.
     */
    @Test
    public void testFindById() {
        Monster sameMonster = monsterDao.findById(dragon.getId());
        Assert.assertEquals(dragon, sameMonster);
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
        monsterDao.create(troll);
        Monster sameMonster = monsterDao.findById(troll.getId());
        Assert.assertEquals(troll, sameMonster);
        monsterDao.delete(troll);
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

        dragon.setName("Dragon");
        monsterDao.update(dragon);
    }

    /**
     * Test of delete method, of monsterDao class.
     */
    @Test
    public void testDelete() {
        monsterDao.create(troll);
        Assert.assertNotNull(monsterDao.findById(troll.getId()));
        monsterDao.delete(troll);
        Assert.assertNull(monsterDao.findById(troll.getId()));
    }

    /**
     * Test of findAll method, of monsterDao class.
     */
    @Test
    public void testFindAll() {
        monsterDao.create(troll);

        List<Monster> foundMonsters = monsterDao.findAll();

        List<Monster> expectedResult = new ArrayList();
        expectedResult.add(dragon);
        expectedResult.add(troll);

        Assert.assertEquals(foundMonsters.size(), expectedResult.size());
//        for (int i = 0; i < expectedResult.size(); i++) {
//            Assert.assertEquals(expectedResult.get(i), foundMonsters.get(i));
//        }
        monsterDao.delete(troll);
    }

}
