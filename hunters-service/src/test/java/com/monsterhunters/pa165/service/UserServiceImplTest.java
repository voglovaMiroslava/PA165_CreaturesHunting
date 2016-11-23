package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.UserDao;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Snurka on 11/20/2016.
 */

@ContextConfiguration(classes = {MappingConfiguration.class})
public class UserServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private User createTestingUser() {
        return new User("testingUser", "test@mail.com", "testingHash", false);
    }

    private List<User> createTestingUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", "u1@gmail.com", "testingHash1", false));
        userList.add(new User("user2", "u2@gmail.com", "testingHash2", false));
        userList.add(new User("user3", "u3@gmail.com", "testingHash3", true));
        userList.add(new User("user4", "u4@gmail.com", "testingHash4", false));
        userList.add(new User("user5", "u5@gmail.com", "testingHash5", true));
        return userList;
    }

    @Test
    public void findByIdTest() {
        User user = createTestingUser();
        when(userDao.findById(any(Long.class))).thenReturn(user);
        Assert.assertEquals(user, userService.findById(-1L));
    }

    @Test
    public void findByNicknameTest() {
        User user = createTestingUser();
        when(userDao.findByNickname(any(String.class))).thenReturn(user);
        Assert.assertEquals(user, userService.findByNickname("nickname"));
    }

    @Test
    public void findByEmailTest() {
        User user = createTestingUser();
        when(userDao.findByEmail(any(String.class))).thenReturn(user);
        Assert.assertEquals(user, userService.findByEmail("email"));
    }

    @Test
    public void isAdminTest() {
        User user = createTestingUser();
        user.setAdmin(false);
        when(userDao.findById(any(Long.class))).thenReturn(user);
        Assert.assertEquals(userService.isAdmin(user), false);
        user.setAdmin(true);
        Assert.assertEquals(userService.isAdmin(user), true);
    }

    @Test
    public void registerUserTest() {
        User user = createTestingUser();
        String plainPassword = "plainPassword";
        userService.registerUser(user, plainPassword);
        when(userDao.findAll()).thenReturn(new ArrayList<User>(){{add(user);}});
        List<User> userList = userService.findAll();
        Assert.assertEquals(userList.size(), 1);
        Assert.assertTrue(user == userList.get(0));
        Assert.assertNotEquals(userList.get(0).getPasswordHash(), plainPassword);
    }

    @Test
    public void findAllTest() {
        List<User> userList = createTestingUserList();
        when(userDao.findAll()).thenReturn(userList);
        List<User> foundUsers = userService.findAll();
        Assert.assertEquals(foundUsers.size(), userList.size());
        for (int i = 0; i < foundUsers.size(); i++) {
            Assert.assertEquals(foundUsers.get(i), userList.get(i));
        }
    }

    @Test
    public void authenticateTest() {
        User user = createTestingUser();
        userService.registerUser(user, "myPassword");
        Assert.assertTrue(userService.authenticate(user, "myPassword"));
        Assert.assertFalse(userService.authenticate(user, "asdf"));
    }


//    @Test
//    public void createUserTest() {
//        String nickname = "DrsnejTypek";
//        String backupNickname = nickname; // Just kill me, now I don't believe anything. Is this needed? :D
//        User user = new User();
//        user.setNickname(nickname);
//        String email = "adsf@sdfadf.com";
//        String backupEmail = email;
//        user.setEmail(email);
//        user.setAdmin(false);
//        String password = "mojeHeslo";
//        String backupPassword = password;
//        userService.registerUser(user, password);
//        User foundUser = userService.findByNickname("DrsnejTypek");
//        Assert.assertNotNull(foundUser);
//        Assert.assertEquals(foundUser.getNickname(), backupNickname);
//        Assert.assertEquals(foundUser.getEmail(), backupEmail);
//        Assert.assertEquals(foundUser.isAdmin(), false);
//        Assert.assertNotEquals(foundUser.getPasswordHash(), backupPassword);
//    }
}
