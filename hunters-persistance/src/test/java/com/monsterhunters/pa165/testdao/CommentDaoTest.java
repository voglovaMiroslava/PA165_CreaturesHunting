package com.monsterhunters.pa165.testdao;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.dao.UserDao;
import com.monsterhunters.pa165.entity.Comment;
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
 * Created by Snurka on 10/31/2016.
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CommentDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @PersistenceContext
    public EntityManager em;

    private Comment createTestComment(String content, User user) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        return comment;
    }

    @Test
    public void deleteCommentTest() {
        User user = new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true);
        Comment comment = createTestComment("VahyJeDrsnejTypek", user);
        userDao.create(user);
        commentDao.create(comment);
        em.flush();
        commentDao.delete(comment);
        em.flush();
        Assert.assertEquals(commentDao.findAll().size(), 0);
    }

    @Test
    public void findAllTest() {
        User user = new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true);
        User user2 = new User("VelkejTypek", "VelkejTypek@jeDrsnej.com", "asdf123", true);
        Comment comment = createTestComment("VahyJeDrsnejTypek", user);
        Comment comment2 = createTestComment("VahyJeDobrejTypek", user);
        Comment comment3 = createTestComment("VahyJekrutoprisnejTypek", user);
        Comment comment4 = createTestComment("adf", user2);
        userDao.create(user);
        userDao.create(user2);
        Assert.assertEquals(commentDao.findAll().size(), 0);
        commentDao.create(comment);
        em.flush();
        Assert.assertEquals(commentDao.findAll().size(), 1);
        commentDao.create(comment2);
        em.flush();
        Assert.assertEquals(commentDao.findAll().size(), 2);
        commentDao.create(comment3);
        em.flush();
        Assert.assertEquals(commentDao.findAll().size(), 3);
        commentDao.create(comment4);
        em.flush();
        Assert.assertEquals(commentDao.findAll().size(), 4);
        List<Comment> commentList = commentDao.findAll();
        Assert.assertEquals(commentList.stream().filter(x -> x.getUser().getId().equals(user.getId())).count(), 3);
        Assert.assertEquals(commentList.stream().filter(x -> x.getUser().getId().equals(user2.getId())).count(), 1);
    }

//    @Test
//    public void findbyUserTest() {
//        new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true))
//        commentDao.create(createTestComment("VahyJeDrsnejTypek", );
//    }

}
