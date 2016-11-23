package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.CommentService;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.WeaponService;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = MappingConfiguration.class)
public class WeaponFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private WeaponService weaponService;

    @Mock
    private CommentService commentService;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private WeaponFacade weaponFacade = new WeaponFacadeImpl();

    private Weapon weaponOne;
    private Weapon weaponTwo;
    private WeaponDTO weaponDTOone;
    private WeaponDTO weaponDTOtwo;
    private List<Weapon> weapons = new ArrayList<>();
    private List<WeaponDTO> dtoWeapons = new ArrayList<>();
    private Long id1 = 1L;
    private Long id2 = 2L;
    private Comment comment;


    @BeforeMethod
    private void prepareWeapons() {
        weaponOne = createWeapon("TestWeapon", 24, 80);
        weaponOne.setId(id1);
        weaponTwo = createWeapon("TestWeaponTwo", 10, 95);
        weaponTwo.setId(id2);
        weaponDTOone = weaponToDTO(weaponOne);
        weaponDTOtwo = weaponToDTO(weaponTwo);
        weapons.add(weaponOne);
        weapons.add(weaponTwo);
        dtoWeapons.add(weaponDTOone);
        dtoWeapons.add(weaponDTOtwo);

    }

    @AfterMethod
    private void clearList() {
        weapons.clear();
        dtoWeapons.clear();
    }

    @BeforeClass
    private void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetWeaponById() {
        WeaponDTO weaponDTOtest = weaponDTOtwo;
        when(weaponService.findById(id2)).thenReturn(weaponTwo);
        when(mappingService.mapTo(weaponTwo, WeaponDTO.class)).thenReturn(weaponDTOtwo);

        assertEquals(weaponFacade.getWeaponById(id2), weaponDTOtest);

        verify(weaponService).findById(id2);
        verify(mappingService).mapTo(weaponTwo, WeaponDTO.class);
    }

    @Test
    public void shouldGetWeaponByName() {
        when(weaponService.findByName("TestWeapon")).thenReturn(weaponOne);
        when(mappingService.mapTo(weaponOne, WeaponDTO.class)).thenReturn(weaponDTOone);

        assertEquals(weaponFacade.getWeaponByName("TestWeapon"), weaponDTOone);

        verify(weaponService).findByName("TestWeapon");
        verify(mappingService).mapTo(weaponOne, WeaponDTO.class);
    }

    @Test
    public void shouldGetAllWeapons() {
        when(weaponService.findAll()).thenReturn(weapons);
        when(mappingService.mapTo(weapons, WeaponDTO.class)).thenReturn(dtoWeapons);

        assertEquals(weaponFacade.getAllWeapons(), dtoWeapons);

        verify(weaponService).findAll();
        verify(mappingService).mapTo(weapons, WeaponDTO.class);
    }

    @Test
    public void shouldCreateWeapon() {
        WeaponCreateDTO wCreateDTO = new WeaponCreateDTO();
        wCreateDTO.setName(weaponDTOtwo.getName());
        wCreateDTO.setAmmo(weaponDTOtwo.getAmmo());
        wCreateDTO.setDamage(weaponDTOtwo.getDamage());

        when(mappingService.mapTo(wCreateDTO, Weapon.class)).thenReturn(weaponTwo);
        when(weaponService.createWeapon(weaponTwo)).thenReturn(weaponTwo);

        assertEquals(weaponFacade.createWeapon(wCreateDTO), weaponTwo.getId());

        verify(mappingService).mapTo(wCreateDTO, Weapon.class);
        verify(weaponService).createWeapon(weaponTwo);
    }

    @Test
    public void shouldUpdateWeapon() {
        when(mappingService.mapTo(weaponDTOtwo, Weapon.class)).thenReturn(weaponTwo);
        when(weaponService.updateWeapon(weaponTwo)).thenReturn(weaponTwo);

        weaponFacade.updateWeapon(weaponDTOtwo);
        verify(mappingService).mapTo(weaponDTOtwo, Weapon.class);
        verify(weaponService).updateWeapon(weaponTwo);
    }

    @Test
    public void shouldDeleteWeapon() {
        when(weaponService.findById(id1)).thenReturn(weaponOne);
        doNothing().when(weaponService).deleteWeapon(any(Weapon.class));

        weaponFacade.deleteWeapon(weaponOne.getId());
        verify(weaponService, atLeastOnce()).findById(weaponOne.getId());
        verify(weaponService).deleteWeapon(weaponOne);
    }

    @Test
    public void shouldAddComment() {
        comment = new Comment();
        comment.setId(10L);
        comment.setContent("This is comment for super weapon");
        comment.setUser(new User("NickName", "mail@user.com", "passHash", false));

        when(weaponService.findById(weaponOne.getId())).thenReturn(weaponOne);
        when(commentService.findById(comment.getId())).thenReturn(comment);
        doNothing().when(weaponService).addComment(weaponOne, comment);

        weaponFacade.addComment(weaponOne.getId(), comment.getId());

        verify(weaponService).addComment(weaponOne, comment);
    }

    @Test
    public void shouldRemoveComment() {
        comment = new Comment();
        comment.setId(11L);
        comment.setContent("This is another");
        comment.setUser(new User("NickName", "mail@user.com", "passHash", false));
        weaponOne.addComment(comment);

        when(weaponService.findById(weaponOne.getId())).thenReturn(weaponOne);
        when(commentService.findById(comment.getId())).thenReturn(comment);
        doNothing().when(weaponService).removeComment(weaponOne, comment);

        weaponFacade.removeComment(weaponOne.getId(), comment.getId());

        verify(weaponService).removeComment(weaponOne, comment);
        verify(commentService, atLeastOnce()).findById(comment.getId());
    }

    @Test
    public void shouldAddEffectiveAgainst() {
        when(weaponService.findById(weaponOne.getId())).thenReturn(weaponOne);
        doNothing().when(weaponService).addEffectiveAgainst(weaponOne, MonsterType.DRAGON);

        weaponFacade.addEffectiveAgainst(weaponOne.getId(), MonsterType.DRAGON);

        verify(weaponService).addEffectiveAgainst(weaponOne, MonsterType.DRAGON);
    }

    @Test
    public void shouldRemoveEffectiveAgainst() {
        when(weaponService.findById(weaponOne.getId())).thenReturn(weaponOne);
        doNothing().when(weaponService).removeEffectiveAgainst(weaponOne, MonsterType.DRAGON);

        weaponFacade.removeEffectiveAgainst(weaponOne.getId(), MonsterType.DRAGON);

        verify(weaponService).removeEffectiveAgainst(weaponOne, MonsterType.DRAGON);
    }

    private WeaponDTO weaponToDTO(Weapon weapon) {
        WeaponDTO dto = new WeaponDTO();
        dto.setName(weapon.getName());
        dto.setAmmo(weapon.getAmmo());
        dto.setDamage(weapon.getDamage());
        dto.setId(weapon.getId());
        return dto;
    }

    private Weapon createWeapon(String name, int ammo, int damage) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setAmmo(ammo);
        weapon.setDamage(damage);
        return weapon;
    }

}
