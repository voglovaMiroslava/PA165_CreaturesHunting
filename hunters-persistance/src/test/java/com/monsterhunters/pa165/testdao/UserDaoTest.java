package com.monsterhunters.pa165.testdao;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.dao.UserDao;
import com.monsterhunters.pa165.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Snurka on 10/30/2016.
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Test
    public void createUserTest() {
        User user = new User();
        user.setEmail("myNickname");
        user.setNickname("MyEmail@asdf.com");
        user.setAdmin(true);

        User user2 = new User();
        user2.setEmail("myNickname2");
        user2.setNickname("MyEmail2@asdf.com");
        user2.setAdmin(true);

        userDao.create(user);
        userDao.create(user2);

        User user3 = userDao.findByEmail("MyEmail@asdf.com");
        Assert.assertEquals(user3.getId(), new Long(1));

        User user4 = userDao.findByEmail("MyEmail@asdf.com");
        Assert.assertEquals(user4.getId(), new Long(2));
    }


}
