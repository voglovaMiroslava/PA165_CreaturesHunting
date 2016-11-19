package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by babcang on 19.11.2016.
 *
 * @author Babcan G
 */
@ContextConfiguration(classes = MappingConfiguration.class)
public class MappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MappingService mappingService;

    private List<Weapon> weapons = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();


    @BeforeMethod
    public void CreateWeaponsWithComment(){

        User userOne;
        userOne = new User("User1", "user1@user.com", "myPasswordHash", true);

        Comment commentOne = new Comment();
        commentOne.setContent("This is comment one");
        commentOne.setUser(userOne);
        comments.add(commentOne);

        Comment commentTwo = new Comment();
        commentTwo.setContent("This is comment two");
        commentTwo.setUser(userOne);
        comments.add(commentTwo);

        Comment commentThree = new Comment();
        commentThree.setContent("This is comment three");
        commentThree.setUser(userOne);
        comments.add(commentThree);

        Weapon weaponOne = new Weapon();
        weaponOne.setName("Weapon1");
        weaponOne.addComment(commentOne);
        weapons.add(weaponOne);

        Weapon weaponTwo = new Weapon();
        weaponTwo.setName("Weapon2");
        weaponTwo.addComment(commentTwo);
        weaponTwo.addComment(commentThree);
        weapons.add(weaponTwo);
    }

    @Test
    public void shouldMapInnerWeaponComments(){
        List<WeaponDTO> cdtos = mappingService.mapTo(weapons, WeaponDTO.class);
        assertEquals(cdtos.get(1).getComments().size(),2);
        assertEquals(cdtos.get(0).getComments().size(),1);
    }


}
