package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Snurka on 10/30/2016.
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createUserTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", "myPasswordHash", true);
        User user2 = new User("myNickname2", "MyEmail2@asdf.com", "myPasswordHash2", true);

        userDao.create(user);
        userDao.create(user2);

        User user3 = userDao.findByNickname("myNickname");
        Assert.assertEquals(user3.getNickname(), "myNickname");

        User user4 = userDao.findByEmail("MyEmail@asdf.com");
        Assert.assertEquals(user4.getNickname(), "myNickname");
    }

    @Test
    public void deleteUserTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", "myPasswordHash", true);
        userDao.create(user);
        User user2 = userDao.findByNickname("myNickname");
        Assert.assertEquals(user2.getNickname(), "myNickname");
        userDao.delete(user);
        User user3 = userDao.findByNickname("myNickname");
        Assert.assertNull(user3);
    }

    @Test
    public void updateUserTest() {
        User user = new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true);
        userDao.create(user);
        em.flush();
        user.setNickname("newNickanme");
        userDao.update(user);
        User user2 = userDao.findByEmail("vahy@jeDrsnej.com");
        Assert.assertEquals(user2.getNickname(), "newNickanme");
    }

    @Test
    public void findAllUsersTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", "myPasswordHash", true);
        User user2 = new User("myNickname2", "MyEmail2@asdf.com", "myPasswordHash2", true);
        userDao.create(user);
        userDao.create(user2);
        List<User> users = userDao.findAll();
        Assert.assertEquals(users.size(), 2);
        Assert.assertEquals(users.stream().filter(x -> x.getNickname().equals("myNickname")).count(), 1);
        Assert.assertEquals(users.stream().filter(x -> x.getNickname().equals("myNickname2")).count(), 1);
    }

    @Test
    public void findByIdTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", "myPasswordHash", true);
        User user2 = new User("myNickname2", "MyEmail2@asdf.com", "myPasswordHash2", true);
        userDao.create(user);
        userDao.create(user2);

        Assert.assertEquals(userDao.findById(1L).getNickname(), "myNickname");
        Assert.assertEquals(userDao.findById(2L).getNickname(), "myNickname2");
        Assert.assertNull(userDao.findById(-1L));
    }

    @Test
    public void findByEmailTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", "myPasswordHash", true);
        User user2 = new User("myNickname2", "MyEmail2@asdf.com", "myPasswordHash2", true);
        userDao.create(user);
        userDao.create(user2);

        Assert.assertEquals(userDao.findByEmail("MyEmail2@asdf.com").getNickname(), "myNickname2");
        Assert.assertEquals(userDao.findByEmail("MyEmail@asdf.com").getNickname(), "myNickname");
        Assert.assertNull(userDao.findByEmail("asdf"));
    }

    @Test
    public void findByNicknameTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", "myPasswordHash", true);
        User user2 = new User("myNickname2", "MyEmail2@asdf.com", "myPasswordHash2", true);
        userDao.create(user);
        userDao.create(user2);

        Assert.assertEquals(userDao.findByNickname("myNickname").getEmail(), "MyEmail@asdf.com");
        Assert.assertEquals(userDao.findByNickname("myNickname2").getEmail(), "MyEmail2@asdf.com");
        Assert.assertNull(userDao.findByNickname("asdf"));
    }

}
