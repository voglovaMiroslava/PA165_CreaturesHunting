package com.monsterhunters.pa165.dao;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snurka on 10/31/2016.
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CommentDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createDeleteCommentTest() {
        User user = new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true);
        Comment comment = createTestComment("VahyJeDrsnejTypek", user);
        userDao.create(user);
        commentDao.create(comment);
        em.flush();
        commentDao.delete(comment);
        em.flush();
        Assert.assertEquals(commentDao.findAll().size(), 0);
    }

    @Test(dependsOnMethods = "createDeleteCommentTest")
    public void updateCommentTest() {
        User user = new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true);
        Comment comment = createTestComment("VahyJeDrsnejTypek", user);
        userDao.create(user);
        commentDao.create(comment);
        em.flush();
        comment.setContent("asdf");
        commentDao.update(comment);
        em.flush();
        List<Comment> commentList = commentDao.findByUser(user);
        Assert.assertEquals(commentList.size(), 1);
        Assert.assertEquals(commentList.get(0).getContent(), comment.getContent());
    }

    @Test(dependsOnMethods = "createDeleteCommentTest")
    public void findAllTest() {
        List<User> userList = createUserDomain();
        List<Comment> commentList = createCommentDomain(userList);
        assertCreateBasicDomainModelAndAssert(userList, commentList);
        List<Comment> commentListFound = commentDao.findAll();
        Assert.assertEquals(commentListFound.stream().filter(x -> x.getUser().getId().equals(userList.get(0).getId())).count(), 3);
        Assert.assertEquals(commentListFound.stream().filter(x -> x.getUser().getId().equals(userList.get(1).getId())).count(), 1);
    }

    @Test(dependsOnMethods = "createDeleteCommentTest")
    public void findByUserTest() {
        List<User> userList = createUserDomain();
        List<Comment> commentList = createCommentDomain(userList);
        assertCreateBasicDomainModelAndAssert(userList, commentList);
        List<Comment> commentListFound = commentDao.findByUser(userList.get(0));
        Assert.assertEquals(commentListFound.size(), 3);
        Assert.assertEquals(commentListFound.stream().filter(x -> x.getUser().getId().equals(userList.get(0).getId())).count(), 3);
        List<Comment> commentListFound2 = commentDao.findByUser(userList.get(1));
        Assert.assertEquals(commentListFound2.size(), 1);
        Assert.assertEquals(commentListFound2.stream().filter(x -> x.getUser().getId().equals(userList.get(1).getId())).count(), 1);
    }

    @Test(dependsOnMethods = "createDeleteCommentTest")
    public void findByIdTest() {
        List<User> userList = createUserDomain();
        List<Comment> commentList = createCommentDomain(userList);
        assertCreateBasicDomainModelAndAssert(userList, commentList);
        for (Comment comment : commentList) {
            Assert.assertTrue(commentDao.findById(comment.getId()).getContent().equals(comment.getContent()));
        }
    }

    @Test
    public void NonExistingEntityTest() {
        Assert.assertTrue(commentDao.findById(-1L) == null);
    }

    @Test
    public void deleteNonExistentEntityTest() {
        User user = new User("Lalala", "asdf@asdfadf.com", "asdf123", false);
        Comment comment = createTestComment("JeduVTanku", user);
        userDao.create(user);
        commentDao.delete(comment);
    }

    @Test
    public void findByNonExistingUser() {
        User user = new User("Lalala", "asdf@asdfadf.com", "asdf123", false);
        Comment comment = createTestComment("JeduVTanku", user);
        userDao.create(user);
        commentDao.delete(comment);
        User user2 = new User("wakawaka", "yeah@yeah.yeah", "its time for africa", false);
        Assert.assertTrue(commentDao.findByUser(user2).isEmpty());
    }

    private List<User> createUserDomain() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Vahy", "vahy@jeDrsnej.com", "asdf123", true));
        userList.add(new User("VelkejTypek", "VelkejTypek@jeDrsnej.com", "asdf123", true));
        return userList;
    }

    private List<Comment> createCommentDomain(List<User> userList) {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(createTestComment("VahyJeDrsnejTypek", userList.get(0)));
        commentList.add(createTestComment("VahyJeDobrejTypek", userList.get(0)));
        commentList.add(createTestComment("VahyJeKrutoprisnejTypek", userList.get(0)));
        commentList.add(createTestComment("adf", userList.get(1)));
        return commentList;
    }

    private Comment createTestComment(String content, User user) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        return comment;
    }

    private void assertCreateBasicDomainModelAndAssert(List<User> userList, List<Comment> commentList) {
        for (User user : userList) {
            userDao.create(user);
        }
        Assert.assertEquals(commentDao.findAll().size(), 0);
        for (int i = 0; i < commentList.size(); i++) {
            commentDao.create(commentList.get(i));
            em.flush();
            Assert.assertEquals(commentDao.findAll().size(), i + 1);
        }
    }

}
