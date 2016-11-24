package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.UserAuthenticateDTO;
import com.monsterhunters.pa165.dto.UserChangePassDTO;
import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.exceptions.user.EmailAlreadyExistsException;
import com.monsterhunters.pa165.exceptions.user.NicknameAlreadyExistsException;
import com.monsterhunters.pa165.exceptions.user.UserDoesNotExistsException;
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
public class UserFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserFacade userFacade;

    @Test
    public void getUserByIdTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserDTO userDTO = userFacade.registerUser(userCreateDTO);
        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("Huhuuuu");
        userCreateDTO2.setPlainPassword("userPassword");
        UserDTO userDTO2 = userFacade.registerUser(userCreateDTO2);
        Assert.assertEquals(userFacade.getUserById(userDTO.getId()).getNickname(), userCreateDTO.getNickname());
        Assert.assertEquals(userFacade.getUserById(userDTO2.getId()).getNickname(), userCreateDTO2.getNickname());
    }

    @Test
    public void getUserByEmailTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserDTO userDTO = userFacade.registerUser(userCreateDTO);
        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("Huhuuuu");
        userCreateDTO2.setPlainPassword("userPassword");
        UserDTO userDTO2 = userFacade.registerUser(userCreateDTO2);
        UserDTO foundUserDTO = userFacade.getUserByEmail("aaa@aaa.com");
        Assert.assertEquals(foundUserDTO.getNickname(), userDTO2.getNickname());
    }

    @Test
    public void getUserByNicknameTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserDTO userDTO = userFacade.registerUser(userCreateDTO);
        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("Huhuuuu");
        userCreateDTO2.setPlainPassword("userPassword");
        UserDTO userDTO2 = userFacade.registerUser(userCreateDTO2);
        UserDTO foundUserDTO = userFacade.getUserByNickname("DrsnejTypek");
        Assert.assertEquals(foundUserDTO.getEmail(), userDTO.getEmail());
        UserDTO foundUserDTO2 = userFacade.getUserByNickname("NonExisting");
        Assert.assertNull(foundUserDTO2);
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

        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("Huhuuuu");
        userCreateDTO2.setPlainPassword("userPassword");
        UserDTO userDTO2 = userFacade.registerUser(userCreateDTO2);
        Assert.assertEquals(userDTO2.getEmail(), userCreateDTO2.getEmail());
        Assert.assertEquals(userDTO2.getNickname(), userCreateDTO2.getNickname());
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
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setEmail("aaa@aaa.com");
        userCreateDTO2.setNickname("Hlupak");
        userCreateDTO2.setPlainPassword("userPassword");
        userFacade.registerUser(userCreateDTO);
        userFacade.registerUser(userCreateDTO2);

        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setNickname(userCreateDTO.getNickname());
        userAuthenticateDTO.setPassword(userCreateDTO.getPlainPassword());
        Assert.assertTrue(userFacade.authenticateUser(userAuthenticateDTO));
        userAuthenticateDTO.setPassword("DummyPassword");
        Assert.assertFalse(userFacade.authenticateUser(userAuthenticateDTO));
    }

    @Test(expectedExceptions = UserDoesNotExistsException.class)
    public void authenticateFakeUserTest() {
        UserAuthenticateDTO fakeUserAuthenticateDTO = new UserAuthenticateDTO();
        fakeUserAuthenticateDTO.setNickname("hahaha");
        fakeUserAuthenticateDTO.setPassword("somePwd");
        userFacade.authenticateUser(fakeUserAuthenticateDTO);
    }

    @Test
    public void isAdminTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserDTO userDTO = userFacade.registerUser(userCreateDTO);
        Assert.assertFalse(userFacade.isAdmin(userDTO));
    }

    @Test
    public void removeTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        UserDTO userDTO = userFacade.registerUser(userCreateDTO);
        Assert.assertEquals(userFacade.getUserByEmail("asdf@asdf.com").getNickname(), userCreateDTO.getNickname());
        userFacade.remove(userDTO);
        Assert.assertNull(userFacade.getUserByEmail("asdf@asdf.com"));
    }

    @Test
    public void changePasswordTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("asdf@asdf.com");
        userCreateDTO.setNickname("DrsnejTypek");
        userCreateDTO.setPlainPassword("userPassword");
        userFacade.registerUser(userCreateDTO);
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setNickname(userCreateDTO.getNickname());
        userAuthenticateDTO.setPassword(userCreateDTO.getPlainPassword());
        Assert.assertTrue(userFacade.authenticateUser(userAuthenticateDTO));
        UserChangePassDTO userChangePassDTO = new UserChangePassDTO();
        userChangePassDTO.setUserAuthenticateDTO(userAuthenticateDTO);
        userChangePassDTO.setNewPassword("newPassword");
        UserAuthenticateDTO newUserAuthenticateDTO = new UserAuthenticateDTO();
        newUserAuthenticateDTO.setNickname(userCreateDTO.getNickname());
        newUserAuthenticateDTO.setPassword(userChangePassDTO.getNewPassword());
        Assert.assertFalse(userFacade.authenticateUser(newUserAuthenticateDTO));
        userFacade.changePassword(userChangePassDTO);
        Assert.assertTrue(userFacade.authenticateUser(newUserAuthenticateDTO));
    }

}
