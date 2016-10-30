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

/**
 * Created by Snurka on 10/30/2016.
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Test
    public void createUserTest() {
        User user = new User("myNickname", "MyEmail@asdf.com", true);
        User user2 = new User("myNickname2", "MyEmail2@asdf.com", true);

        userDao.create(user);
        userDao.create(user2);

        User user3 = userDao.findByNickname("myNickname");
        Assert.assertEquals(user3.getNickname(), "myNickname");

        User user4 = userDao.findByEmail("MyEmail@asdf.com");
        Assert.assertEquals(user4.getNickname(), "myNickname2");
    }

}
