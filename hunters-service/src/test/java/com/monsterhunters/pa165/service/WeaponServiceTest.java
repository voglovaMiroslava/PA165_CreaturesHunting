package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = MappingConfiguration.class)
public class WeaponServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private WeaponDao weaponDao;

    @Mock
    private MonsterDao monsterDao;

    @Autowired
    @InjectMocks
    private WeaponService weaponService;

    private List<Weapon> expectedWeapons = new ArrayList<>();
    private long id1 = 1L;
    private long id2 = 2L;

    @BeforeMethod
    public void prepareWeaponsForTest() {
        Weapon weapon1 = createWeapon("AK47", 90, 30);
        weapon1.setId(id1);
        Weapon weapon2 = createWeapon("P90", 55, 50);
        weapon2.setId(id2);
        expectedWeapons.add(weapon1);
        expectedWeapons.add(weapon2);
    }

    @AfterMethod
    public void clearList() {
        expectedWeapons.clear();
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateNewWeapon() {
        int startSize = expectedWeapons.size();
        Weapon newWeapon = createWeapon("Bizon", 40, 64);

        doAnswer(invoke -> {
            Weapon mockingWeapon = invoke.getArgumentAt(0, Weapon.class);
            mockingWeapon.setId((long) (expectedWeapons.size() + 1));
            expectedWeapons.add(mockingWeapon);
            return mockingWeapon;
        }).when(weaponDao).create(newWeapon);

        weaponService.createWeapon(newWeapon);
        verify(weaponDao).create(newWeapon);
        assertEquals(expectedWeapons.size(), startSize + 1);
    }

    @Test
    public void shouldDeleteWeapon() {
        int startSize = expectedWeapons.size();
        doAnswer(invoke -> {
            Weapon mockingWeapon = invoke.getArgumentAt(0, Weapon.class);
            expectedWeapons.remove(mockingWeapon);
            return mockingWeapon;
        }).when(weaponDao).delete(any(Weapon.class));
        Weapon weaponToDelete = expectedWeapons.get(0);
        weaponService.deleteWeapon(weaponToDelete);

        verify(weaponDao).delete(weaponToDelete);
        assertEquals(expectedWeapons.size(), startSize - 1);
    }

    @Test
    public void shouldUpdateWeapon() {
        Weapon weaponToUpdate;
        weaponToUpdate = expectedWeapons.get(0);
        doAnswer(invoke -> {
            Weapon mockingWeapon = invoke.getArgumentAt(0, Weapon.class);
            expectedWeapons.set(0, mockingWeapon);
            return mockingWeapon;
        }).when(weaponDao).update(weaponToUpdate);
        weaponToUpdate.setName("NewName");
        weaponService.updateWeapon(weaponToUpdate);

        verify(weaponDao).update(weaponToUpdate);
        assertEquals(expectedWeapons.get(0), weaponToUpdate);
    }

    @Test
    public void shouldFindWeaponById() {
        Weapon foundWeapon;
        when(weaponDao.findById(id1)).thenReturn(expectedWeapons.get(0));
        foundWeapon = weaponService.findById(id1);

        verify(weaponDao).findById(id1);
        assertEquals(foundWeapon, expectedWeapons.get(0));
        assertEquals(foundWeapon.getId(), expectedWeapons.get(0).getId());
        assertEquals(weaponService.findById(id2), null);
    }

    @Test
    public void shouldntFindByNonExistentId() {
        Weapon foundWeapon;
        when(weaponDao.findById(id1)).thenReturn(expectedWeapons.get(0));
        foundWeapon = weaponService.findById(1111L);

        verify(weaponDao).findById(1111L);
        assertEquals(foundWeapon, null);
    }

    @Test
    public void shouldFindWeaponByName() {
        Weapon foundWeapon;
        when(weaponDao.findByName("P90")).thenReturn(expectedWeapons.get(1));
        foundWeapon = weaponService.findByName("P90");

        verify(weaponDao).findByName("P90");
        assertEquals(foundWeapon, expectedWeapons.get(1));
    }

    @Test
    public void shouldFindAll() {
        Weapon testWeapon = createWeapon("Grenade", 95, 1);
        expectedWeapons.add(testWeapon);
        when(weaponDao.findAll()).thenReturn(Collections.unmodifiableList(expectedWeapons));

        assertEquals(weaponService.findAll(), expectedWeapons);
        verify(weaponDao).findAll();
    }

    @Test
    public void shouldAddEffectiveAgainst() {
        Weapon weapon = createWeapon("Kalach", 60, 30);
        weaponService.addEffectiveAgainst(weapon, MonsterType.DRAGON);
        assertTrue(weapon.getEffectiveAgainst().contains(MonsterType.DRAGON));
    }

    @Test(expectedExceptions = {HuntersServiceException.class})
    public void shoulndtAddTypeThatIsAlreadyInList() throws Exception {
        Weapon weapon = createWeapon("Kalach", 60, 30);
        weapon.addEffectiveAgainst(MonsterType.DRAGON);
        weaponService.addEffectiveAgainst(weapon, MonsterType.DRAGON);
        assertTrue(weapon.getEffectiveAgainst().contains(MonsterType.DRAGON));
    }

    @Test
    public void shouldRemoveEffectiveAgainst(){
        Weapon weapon = createWeapon("Kalach", 60, 30);
        weapon.addEffectiveAgainst(MonsterType.GROUND);
        weaponService.removeEffectiveAgainst(weapon, MonsterType.GROUND);
        assertFalse(weapon.getEffectiveAgainst().contains(MonsterType.GROUND));
    }

    @Test(expectedExceptions = {HuntersServiceException.class})
    public void shouldntRemoveNotContainingEffectiveAgainst() throws Exception{
        Weapon weapon = createWeapon("Kalach", 60, 30);
        weapon.addEffectiveAgainst(MonsterType.GROUND);
        weaponService.removeEffectiveAgainst(weapon, MonsterType.DRAGON);
        assertEquals(weapon.getEffectiveAgainst().size(), 1);
    }

    @Test
    public void shouldAddComment() {
        Weapon weapon = createWeapon("Kalach", 60, 30);
        Comment comment = new Comment();
        comment.setContent("This is my comment");

        weaponService.addComment(weapon, comment);
        assertTrue(weapon.getComments().contains(comment));
        assertEquals(weapon.getComments().size(), 1);
    }

    @Test
    public void shouldRemoveComment() {
        Weapon weapon = createWeapon("Kalach", 60, 30);
        Comment comment = new Comment();
        comment.setContent("This is my comment");
        weapon.addComment(comment);

        weaponService.removeComment(weapon, comment);
        assertFalse(weapon.getComments().contains(comment));
        assertEquals(weapon.getComments().size(), 0);
    }

    @Test(expectedExceptions = {HuntersServiceException.class})
    public void shouldntRemoveCommentNotBelongingToWeapon(){
        Weapon weapon = createWeapon("Kalach", 60, 30);
        Comment comment = new Comment();
        comment.setUser(new User("Nickname","mail@mail.com","password",false));
        comment.setContent("This is my comment");
        weapon.addComment(comment);

        Weapon weapon2 = createWeapon("AWP", 99, 10);
        Comment comment2 = new Comment();
        comment2.setUser(new User("Nickname","mail@mail.com","password",false));
        comment2.setContent("This is my second comment");
        weapon2.addComment(comment2);

        weaponService.removeComment(weapon, comment2);
        assertTrue(weapon.getComments().contains(comment));
        assertTrue(weapon2.getComments().contains(comment2));
        assertEquals(weapon.getComments().size(), 1);
        assertEquals(weapon2.getComments().size(), 1);
    }

    @Test
    public void shouldFindKillableMonsters() {
        Weapon weapon = createWeapon("Ultra Smasher", 50, 5);
        weapon.addEffectiveAgainst(MonsterType.GROUND);
        weapon.addEffectiveAgainst(MonsterType.ROCK);

        List<Monster> monsters = new ArrayList<>();
        Monster monster1 = createMonster("Orc", 200, MonsterType.DRAGON);
        Monster monster2 = createMonster("Kavabonga", 250, MonsterType.GROUND);
        Monster monster3 = createMonster("Pika", 300, MonsterType.ROCK);
        Monster monster4 = createMonster("Multi", 160, MonsterType.GROUND);
        monster4.addType(MonsterType.FIRE);
        monsters.add(monster1);
        monsters.add(monster2);
        monsters.add(monster3);
        monsters.add(monster4);

        when(monsterDao.findAll()).thenReturn(monsters);

        List<Monster> killableMonsters = weaponService.findKillableMonsters(weapon);
        assertEquals(killableMonsters.size(), 2);
        assertTrue(killableMonsters.contains(monster2));
        assertTrue(killableMonsters.contains(monster4));
        verify(monsterDao).findAll();
    }


    //Simple weapon init with parameters
    private Weapon createWeapon(String name, int damage, int ammo) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setDamage(damage);
        weapon.setAmmo(ammo);
        return weapon;
    }

    //Simple monster init with parameters
    private Monster createMonster(String name, int power, MonsterType mType) {
        Monster monster = new Monster();
        monster.setName(name);
        monster.setPower(power);
        monster.addType(mType);
        return monster;
    }

}
