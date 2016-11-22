package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.UserDao;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

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

//    @BeforeClass
//    public void Setup() {
//        MockitoAnnotations.initMocks(this);
//
//        when(userDao.create(any(User.class))).then();
//    }

    @Test
    public void createUserTest() {
        String nickname = "DrsnejTypek";
        String backupNickname = nickname; // Just kill me, now I don't believe anything. Is this needed? :D
        User user = new User();
        user.setNickname(nickname);
        String email = "adsf@sdfadf.com";
        String backupEmail = email;
        user.setEmail(email);
        user.setAdmin(false);
        String password = "mojeHeslo";
        String backupPassword = password;
        userService.registerUser(user, password);
        User foundUser = userService.findByNickname("DrsnejTypek");
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(foundUser.getNickname(), backupNickname);
        Assert.assertEquals(foundUser.getEmail(), backupEmail);
        Assert.assertEquals(foundUser.isAdmin(), false);
        Assert.assertNotEquals(foundUser.getPasswordHash(), backupPassword);
    }
}
