package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.entity.Weapon;
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
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = MappingConfiguration.class)
public class WeaponServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private WeaponDao weaponDao;

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
        when(weaponDao.findAll()).thenReturn(expectedWeapons);

        assertEquals(weaponService.findAll(), expectedWeapons);
        verify(weaponDao).findAll();
    }


    //Simple weapon init with parameters
    private Weapon createWeapon(String name, int damage, int ammo) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setDamage(damage);
        weapon.setAmmo(ammo);
        return weapon;
    }

}
