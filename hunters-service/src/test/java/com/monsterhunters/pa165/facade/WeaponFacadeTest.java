package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.*;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = {MappingConfiguration.class})
public class WeaponFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    MonsterFacade monsterFacade;

    @Autowired
    CommentFacade commentFacade;

    @Autowired
    UserFacade userFacade;

    @Autowired
    WeaponFacade weaponFacade;

    private UserCreateDTO userCreateDTO;
    private Long commentId;

    @BeforeClass
    private void createUserAndComment() {
        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("mail@mail.com");
        userCreateDTO.setNickname("Rosaldo");
        userCreateDTO.setPlainPassword("password");
        userFacade.registerUser(userCreateDTO);

        CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setContent("This is it");
        commentCreateDTO.setUserId(userFacade.getUserByNickname("Rosaldo").getId());
        commentId = commentFacade.createComment(commentCreateDTO);
    }

    @AfterMethod
    private void delete() {
        List<WeaponDTO> weaponDTOS = weaponFacade.getAllWeapons();
        for (WeaponDTO weapon : weaponDTOS) {
            weaponFacade.deleteWeapon(weapon.getId());
        }
    }


    @Test
    public void shouldCreateWeapon() {
        WeaponCreateDTO weaponCreateDTO = createDTO("TestCreate", 12, 50);

        Set<MonsterType> monsterTypes = new HashSet<>();
        monsterTypes.add(MonsterType.DRAGON);
        monsterTypes.add(MonsterType.GROUND);
        weaponCreateDTO.setEffectiveAgainst(monsterTypes);
        Long id = weaponFacade.createWeapon(weaponCreateDTO);

        WeaponDTO weaponDTO = weaponFacade.getWeaponByName("TestCreate");
        assertEquals(weaponDTO.getId(), id);
        assertEquals(weaponDTO.getEffectiveAgainst().size(), 2);
        assertTrue(weaponDTO.getEffectiveAgainst().contains(MonsterType.DRAGON));
    }

    @Test
    public void shouldGetWeaponByName() {
        WeaponCreateDTO createDTO = createDTO("Shotgun", 5, 70);
        Long id = weaponFacade.createWeapon(createDTO);

        assertEquals(weaponFacade.getWeaponByName("Shotgun").getName(), "Shotgun");
    }

    @Test
    public void shouldGetWeaponById() {
        WeaponCreateDTO createDTO = createDTO("Gun", 20, 60);
        Long id = weaponFacade.createWeapon(createDTO);

        assertEquals(weaponFacade.getWeaponById(id), weaponFacade.getWeaponByName("Gun"));
    }

    @Test
    public void shouldGetAllWeapons() {
        WeaponCreateDTO createDTO1 = createDTO("Gun", 20, 60);
        Long id1 = weaponFacade.createWeapon(createDTO1);
        WeaponCreateDTO createDTO2 = createDTO("Gun2", 10, 90);
        Long id2 = weaponFacade.createWeapon(createDTO2);

        assertEquals(weaponFacade.getAllWeapons().size(), 2);
    }

    @Test
    public void shouldUpdateWeapon() {
        WeaponCreateDTO createDTO = createDTO("Gun", 20, 60);
        Long id = weaponFacade.createWeapon(createDTO);
        WeaponDTO weaponDTO = weaponFacade.getWeaponById(id);
        weaponDTO.setName("Shotgun");
        weaponDTO.setAmmo(10);
        weaponFacade.updateWeapon(weaponDTO);

        assertEquals(weaponFacade.getWeaponById(id).getName(), "Shotgun");
        assertEquals(weaponFacade.getWeaponById(id).getAmmo(), 10);
    }

    @Test
    public void shouldAddComment() {
        WeaponCreateDTO createDTO1 = createDTO("Pistol", 20, 60);
        Long id1 = weaponFacade.createWeapon(createDTO1);
        CommentDTO commentDTO = commentFacade.getCommentById(commentId);
        weaponFacade.addComment(id1, commentId);
        Set<CommentDTO> commentDTOS = weaponFacade.getWeaponById(id1).getComments();
        assertTrue(commentDTOS.contains(commentDTO));
    }

    @Test
    public void shouldRemoveComment() {
        WeaponCreateDTO createDTO1 = createDTO("AWP", 10, 90);
        Long id1 = weaponFacade.createWeapon(createDTO1);
        CommentDTO commentDTO = commentFacade.getCommentById(commentId);
        weaponFacade.addComment(id1, commentId);
        Set<CommentDTO> commentDTOS = weaponFacade.getWeaponById(id1).getComments();
        assertTrue(commentDTOS.contains(commentDTO));
        assertEquals(commentDTOS.size(),1);
        weaponFacade.removeComment(id1, commentId);
        commentDTOS = weaponFacade.getWeaponById(id1).getComments();
        assertEquals(commentDTOS.size(),0);
        assertFalse(commentDTOS.contains(commentDTO));

    }

    @Test
    public void shouldAddEffectiveAgainst() {
        WeaponCreateDTO createDTO1 = createDTO("AK47", 30, 80);
        Long id = weaponFacade.createWeapon(createDTO1);
        weaponFacade.addEffectiveAgainst(id, MonsterType.DRAGON);

        assertTrue(weaponFacade.getWeaponById(id).getEffectiveAgainst().contains(MonsterType.DRAGON));
    }

    @Test
    public void shouldRemoveEffectiveAgainst() {
        WeaponCreateDTO createDTO1 = createDTO("AK47", 30, 80);
        Set<MonsterType> monsterTypes = new HashSet<>();
        monsterTypes.add(MonsterType.DRAGON);
        monsterTypes.add(MonsterType.GROUND);
        createDTO1.setEffectiveAgainst(monsterTypes);
        Long id = weaponFacade.createWeapon(createDTO1);
        weaponFacade.removeEffectiveAgainst(id, MonsterType.DRAGON);

        assertFalse(weaponFacade.getWeaponById(id).getEffectiveAgainst().contains(MonsterType.DRAGON));
        assertEquals(weaponFacade.getWeaponById(id).getEffectiveAgainst().size(), 1);
    }

    @Test
    public void shouldGetKillableMonsters(){
        WeaponCreateDTO createDTO1 = createDTO("AK47", 30, 80);
        Set<MonsterType> monsterTypes = new HashSet<>();
        monsterTypes.add(MonsterType.DRAGON);
        monsterTypes.add(MonsterType.FIRE);
        createDTO1.setEffectiveAgainst(monsterTypes);
        Long id = weaponFacade.createWeapon(createDTO1);
        MonsterCreateDTO monster = new MonsterCreateDTO();
        monster.setName("Drako");
        monster.setTypes(monsterTypes);
        monster.setPower(100);
        monsterFacade.createMonster(monster);

        List<MonsterDTO> killableMonsters = weaponFacade.getKillableMonsters(id);
        assertEquals(killableMonsters.size(),1);
    }

    private WeaponCreateDTO createDTO(String name, int ammo, int damage) {
        WeaponCreateDTO weaponCreateDTO = new WeaponCreateDTO();
        weaponCreateDTO.setName(name);
        weaponCreateDTO.setAmmo(ammo);
        weaponCreateDTO.setDamage(damage);
        return weaponCreateDTO;
    }
}
