package com.monsterhunters.pa165.testdao;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.entity.Weapon;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Miroslava Voglova
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class TestWeaponDao extends AbstractTestNGSpringContextTests {

    @Inject
    private WeaponDao weaponDao;
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    private List<Weapon> expectedWeapons = new ArrayList<>();
    
    
    @BeforeMethod
    public void init(){
        EntityManager em = emf.createEntityManager();
        
        Weapon wep = new Weapon();
        wep.setName("name");
        em.persist(wep);
        
        expectedWeapons.add(wep);
        
        
    }
    
    @AfterMethod(alwaysRun = true)
    public void clean(){
        EntityManager em = emf.createEntityManager();
        em.createQuery("DELETE FROM Weapon");
        expectedWeapons.clear();
    }
    
    @Test
    public void testFindById(){
        //I feel so empty...
    }
    
    
    @Test
    public void testFindAll(){
        List<Weapon> weapons = weaponDao.findAll();
        Assert.assertEquals(weapons.size(), expectedWeapons.size(), "Number of weapons  in db and number of weapons expected is not same.");

        Assert.assertEquals(weapons, expectedWeapons);
    }
    
    @Test
    public void testCreate(){
        Weapon wep = new Weapon();
        String weaponName = "Super special weapon with cool stats." ;
        wep.setName(weaponName);
        weaponDao.create(wep);
        
        EntityManager em = emf.createEntityManager();
        TypedQuery<Weapon> query = em.createQuery("SELECT w FROM Weapon w where name = :name", Weapon.class);
        query.setParameter("name", weaponName);
        List<Weapon> weps = query.getResultList();
        Assert.assertEquals(weps.size(), 1);
        
        
    }
    
    @Test
    public void testDeleteExistent(){
    
    }
    
    @Test void testDeleteNonExistent(){
    }
    
    @Test
    public void testUpdateExistent(){
    
    }
    
    @Test
    public void testUpdateNonExistent(){
    
    }
    
    private void checkEquality(Weapon actual, Weapon expected){
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getDamage(), expected.getDamage());
        Assert.assertEquals(actual.getGunReach(), expected.getGunReach());
        Assert.assertEquals(actual, expected);
    }
    
}
