package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.dao.LocationDao;
import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.CommentService;
import com.monsterhunters.pa165.service.LocationService;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Tomas Durcak
 */
@ContextConfiguration(classes = MappingConfiguration.class)
public class LocationFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private LocationDao locationDao;

    @Mock
    private CommentDao commentDao;

    @Mock
    private WeaponDao weaponDao;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private LocationFacade locationFacade;

    @Autowired
    @InjectMocks
    private LocationService locationService;

    @Autowired
    @InjectMocks
    private CommentService commentService;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of createLocation method, of class LocationFacadeImpl.
     */
    @Test
    public void testCreateLocation() {
        Location klin = new Location("Klin", "Pekna dedinka v udoli.");
        LocationCreateDTO klinCreateDTO = new LocationCreateDTO();
        klinCreateDTO.setName(klin.getName());
        klinCreateDTO.setDescription(klin.getDescription());

        when(locationDao.create(any(Location.class))).thenReturn(true);
        when(locationDao.findById(any(Long.class))).thenReturn(klin);
        Long createdLocationId = locationFacade.createLocation(klinCreateDTO);
        assertEquals(createdLocationId, klin.getId());
    }

    /**
     * Test of getAllLocations method, of class LocationFacadeImpl.
     */
    @Test
    public void testGetAllLocations() {
        Location mutne = new Location("Mutne", "Pekna dedinka v udoli.");
        Location novot = new Location("Novot", "Hned za kopcom blizko dulova, very nice");
        Location breza = new Location("Breza", "Kedysi tam rastli brezy.");

        List<Location> expectedResult = new ArrayList<>();
        expectedResult.add(mutne);
        expectedResult.add(novot);
        expectedResult.add(breza);
        when(locationDao.findAll()).thenReturn(expectedResult);
        List<LocationDTO> foundLocations = locationFacade.getAllLocations();
        Assert.assertEquals(foundLocations.size(), expectedResult.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            Assert.assertEquals(foundLocations.get(i), mappingService.mapTo(expectedResult.get(i), LocationDTO.class));
        }
    }

    /**
     * Test of getLocationById method, of class LocationFacadeImpl.
     */
    @Test
    public void testGetLocationById() {
        Location klin = new Location("Klin", "Pekna dedinka v udoli.");
        when(locationDao.findById(any(Long.class))).thenReturn(klin);
        LocationDTO result = locationFacade.getLocationById(1l);
        assertEquals(result, mappingService.mapTo(klin, LocationDTO.class));
    }

    /**
     * Test of addComment method, of class LocationFacadeImpl.
     */
    @Test
    public void testDeleteLocation() {
        Location klin = new Location("Klin", "Pekna dedinka v udoli.");
        when(locationDao.delete(any(Location.class))).thenReturn(true);
        when(locationDao.findById(any(Long.class))).thenReturn(klin);
        boolean result = locationFacade.deleteLocation(1l);
        assertEquals(result, true);
    }

    /**
     * Test of addComment method, of class LocationFacadeImpl.
     */
    @Test
    public void testAddComment() {
        Comment comment = createComment();

        Location klin = new Location("Klin", "Pekna dedinka v udoli.");
        LocationCreateDTO klinCreateDTO = new LocationCreateDTO();
        klinCreateDTO.setName(klin.getName());
        klinCreateDTO.setDescription(klin.getDescription());

        when(locationDao.create(any(Location.class))).thenReturn(true);
        when(locationDao.findById(any(Long.class))).thenReturn(klin);
        locationFacade.createLocation(klinCreateDTO);

        assertEquals(0, klin.getComments().size());
        locationFacade.addComment(klin.getId(), comment.getId());
        assertEquals(klin.getComments().size(), 1);
    }

    /**
     * Test of removeComment method, of class LocationFacadeImpl.
     */
    @Test
    public void testRemoveComment() {
        Comment comment = createComment();

        Location klin = new Location("Klin", "Pekna dedinka v udoli.");
        LocationCreateDTO klinCreateDTO = new LocationCreateDTO();
        klinCreateDTO.setName(klin.getName());
        klinCreateDTO.setDescription(klin.getDescription());

        when(locationDao.create(any(Location.class))).thenReturn(true);
        when(locationDao.findById(any(Long.class))).thenReturn(klin);
        when(commentDao.findById(any(Long.class))).thenReturn(comment);
        locationFacade.createLocation(klinCreateDTO);

        locationFacade.addComment(klin.getId(), comment.getId());
        Set<Comment> commentList = klin.getComments();
        assertEquals(commentList.size(), 1);
        locationFacade.removeComment(klin.getId(), comment.getId());
        Set<Comment> commentList2 = klin.getComments();
        assertEquals(commentList2.size(), 0);
    }

    @Test
    public void testGetBestWeapon() {
        Location tapesovo = new Location("Tapesovo", "Hned za kopcom blizko dulova, very nice");

        Weapon weapon1 = createWeapon("Lite Smasher", 5, 5);
        weapon1.addEffectiveAgainst(MonsterType.ROCK);
        Weapon weapon2 = createWeapon("Ultra Smasher", 50, 5);
        weapon2.addEffectiveAgainst(MonsterType.GROUND);
        weapon2.addEffectiveAgainst(MonsterType.ROCK);

        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon1);
        weapons.add(weapon2);

        Monster monster1 = createMonster("Orc", 200, MonsterType.DRAGON);
        Monster monster2 = createMonster("Kavabonga", 250, MonsterType.GROUND);
        Monster monster3 = createMonster("Pika", 300, MonsterType.ROCK);

        monster1.setLocation(tapesovo);
        monster2.setLocation(tapesovo);
        monster3.setLocation(tapesovo);

        List<Monster> monsters = new ArrayList<>();
        monsters.add(monster1);
        monsters.add(monster2);
        monsters.add(monster3);

        when(weaponDao.findAll()).thenReturn(weapons);
        when(locationDao.getMonstersWithLocation(any(Location.class))).thenReturn(monsters);

        assertEquals(locationFacade.getBestWeapon(tapesovo.getId()), mappingService.mapTo(weapon2, WeaponDTO.class));
    }

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setId(2L);
        comment.setContent("This is another");
        comment.setUser(new User("NickName", "mail@user.com", "passHash", false));
        return comment;
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
