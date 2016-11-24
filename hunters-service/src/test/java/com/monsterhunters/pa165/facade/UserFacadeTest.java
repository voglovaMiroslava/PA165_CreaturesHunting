package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.exceptions.user.EmailAlreadyExistsException;
import com.monsterhunters.pa165.exceptions.user.NicknameAlreadyExistsException;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Snurka on 11/23/2016.
 */
@ContextConfiguration(classes = {MappingConfiguration.class})
public class UserFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserFacade userFacade;

    @Test
    public void getUserByIdTest() {
    }

    @Test
    public void getUserByEmailTest() {

    }

    @Test
    public void getUserByNicknameTest() {

    }

    @Test
    public void registerUserTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserDTO userDTO = userFacade.registerUser(userCreateDTO);
        Assert.assertEquals(userDTO.getEmail(), userCreateDTO.getEmail());
        Assert.assertEquals(userDTO.getNickname(), userCreateDTO.getNickname());
        Assert.assertEquals(userDTO.getId(), new Long(1));

        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("Huhuuuu");
        userCreateDTO2.setPlainPassword("userPassword");
        UserDTO userDTO2 = userFacade.registerUser(userCreateDTO2);
        Assert.assertEquals(userDTO2.getEmail(), userCreateDTO2.getEmail());
        Assert.assertEquals(userDTO2.getNickname(), userCreateDTO2.getNickname());
        Assert.assertEquals(userDTO2.getId(), new Long(2));
    }

    @Test(expectedExceptions = EmailAlreadyExistsException.class)
    public void registerAlreadyExistingEmailTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("asdf@asdf.com");
        userCreateDTO2.setNickname("Huhuuuu");
        userCreateDTO2.setPlainPassword("userPassword");
        userFacade.registerUser(userCreateDTO);
        userFacade.registerUser(userCreateDTO2);
    }

    @Test(expectedExceptions = NicknameAlreadyExistsException.class)
    public void registerAlreadyExistingNicknameTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("DrsnejTypek");
        userCreateDTO2.setPlainPassword("userPassword");
        userFacade.registerUser(userCreateDTO);
        userFacade.registerUser(userCreateDTO2);
    }

    @Test
    public void authenticateUserTest() {

    }

    @Test
    public void isAdminTest() {

    }




}
