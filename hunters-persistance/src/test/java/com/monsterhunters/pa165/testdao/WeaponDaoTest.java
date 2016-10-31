package com.monsterhunters.pa165.testdao;

import com.monsterhunters.pa165.config.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.domain.dao.WeaponDao;
import com.monsterhunters.pa165.domain.entity.Comment;
import com.monsterhunters.pa165.domain.entity.User;
import com.monsterhunters.pa165.domain.entity.Weapon;
import com.monsterhunters.pa165.domain.enums.MonsterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Miroslava Voglova
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@Transactional
public class WeaponDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private WeaponDao weaponDao;

    @PersistenceContext
    public EntityManager em;

    private Comparator<Weapon> byName
            = (Weapon o1, Weapon o2) -> o1.getName().compareTo(o2.getName());

    private List<Weapon> expectedWeapons = new ArrayList<>();

    @BeforeMethod
    public void init() {
        createExpectedWeapon("Fairy dragon slayer",
                Arrays.asList(MonsterType.DRAGON, MonsterType.FAIRY));
        createExpectedWeapon("Fluffy unicorn",
                Arrays.asList(
                        MonsterType.LIGHTNING,
                        MonsterType.UNDEAD,
                        MonsterType.WATER,
                        MonsterType.METAHUMAN,
                        MonsterType.FLYING));
        createExpectedWeapon("Super uber destroyer", new ArrayList<>());
        expectedWeapons.sort(byName);
    }

    @AfterMethod(alwaysRun = true)
    public void clean() {
        expectedWeapons.clear();
    }

    @Test
    public void testFindById() {
        Long expId = expectedWeapons.get(0).getId();
        Weapon wep = weaponDao.findById(expId);
        Assert.assertEquals(wep, expectedWeapons.get(0));
    }

    @Test
    public void testFindAll() {
        List<Weapon> weapons = weaponDao.findAll();
        Assert.assertEquals(weapons.size(), expectedWeapons.size(), "Number of weapons  in db and number of weapons expected is not same.");
        weapons.sort(byName);
        Assert.assertEquals(weapons, expectedWeapons);
    }

    @Test
    public void testFindByExistentName() {
        Weapon expectedWep = expectedWeapons.get(1);
        Weapon wep = weaponDao.findByName(expectedWep.getName());
        Assert.assertEquals(wep, expectedWep);
    }

    @Test
    public void testFindByNonExistentName() {
        Weapon wep = weaponDao.findByName("Weapon that never existed.");
        Assert.assertEquals(wep, null);
    }

    @Test
    public void testCreate() {
        Weapon wep = new Weapon();
        String weaponName = "Super special weapon with cool stats.";
        wep.setName(weaponName);
        weaponDao.create(wep);

        TypedQuery<Weapon> queryForCreated = em.createQuery("SELECT w FROM Weapon w where name = :name", Weapon.class);
        Weapon wepFromDB = queryForCreated.setParameter("name", weaponName).getSingleResult();
        Assert.assertEquals(wepFromDB, wep, "Weapon from db and created weapon are not same.");

        TypedQuery<Weapon> queryForOthers = em.createQuery("SELECT w FROM Weapon w where name <> :name", Weapon.class);
        List<Weapon> otherWeps = queryForOthers.setParameter("name", weaponName).getResultList();
        otherWeps.sort(byName);
        Assert.assertEquals(otherWeps, expectedWeapons, "Create of weapon damaged other entries in database.");
    }

    @Test
    public void testDeleteExistent() {
        Weapon wepForDelete = expectedWeapons.get(1);
        expectedWeapons.remove(1);
        weaponDao.delete(wepForDelete);

        List<Weapon> wepsFromDB = em.createQuery("SELECT w FROM Weapon w", Weapon.class).getResultList();
        wepsFromDB.sort(byName);
        Assert.assertEquals(wepsFromDB, expectedWeapons);
    }

    @Test
    public void testDeleteNonExistent() {
        Weapon newWep = new Weapon();
        newWep.setName("Something that was never before");
        weaponDao.delete(newWep);

        List<Weapon> wepsFromDB = em.createQuery("SELECT w FROM Weapon w", Weapon.class).getResultList();
        wepsFromDB.sort(byName);
        Assert.assertEquals(wepsFromDB, expectedWeapons, "Deleting non existent weapon damaged entries in database.");
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testDeleteNull() {
        try {
            weaponDao.delete(null);
        } catch (InvalidDataAccessApiUsageException e) {
            List<Weapon> wepsFromDB = em.createQuery("SELECT w FROM Weapon w", Weapon.class).getResultList();
            wepsFromDB.sort(byName);
            Assert.assertEquals(wepsFromDB, expectedWeapons, "Deleting null weapon damaged entries in database.");
            throw e;
        }
    }

    @Test(expectedExceptions = NoResultException.class)
    public void testDeleteWithComments() {
        User user = new User("nickname", "email", "passwordHash", true);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent("Original content.");
        Weapon wep = new Weapon();
        wep.setName("Weapon with comments");
        wep.addComment(comment);
        em.persist(user);
        em.persist(comment);
        em.persist(wep);

        weaponDao.delete(wep);
        Comment commentFromDB = em.createQuery("SELECT c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", comment.getId())
                .getSingleResult();
        Assert.fail("Comment which belongs to the deleted weapon should have been deleted.");
    }

    @Test
    public void testUpdate() {
        Weapon expectedWep = expectedWeapons.get(2);
        String newName = "Not so much weapon I was before.";
        expectedWep.setName(newName);
        expectedWep.setAmmo(1000);
        weaponDao.update(expectedWep);

        TypedQuery<Weapon> query = em.createQuery("SELECT w FROM Weapon w WHERE w.name = :name", Weapon.class);
        query.setParameter("name", newName);
        Weapon wep = query.getSingleResult();

        Assert.assertEquals(wep.getName(), newName);
        Assert.assertEquals(expectedWep.getAmmo(), 1000);
        Assert.assertEquals(wep, expectedWep);
    }

    private void createExpectedWeapon(String name, List<MonsterType> types) {
        Weapon wep = new Weapon();
        wep.setName(name);
        for (MonsterType type : types) {
            wep.addEffectiveAgainst(type);
        }
        expectedWeapons.add(wep);
        em.persist(wep);
    }

}
