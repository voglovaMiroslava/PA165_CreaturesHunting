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
    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @PersistenceContext
    public EntityManager em;

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
        List<User> userList = createUserDomain();
        List<Comment> commentList = createCommentDomain(userList);
        assertCreateBasicDomainModelAndAssert(userList, commentList);
        List<Comment> commentListFound = commentDao.findAll();
        Assert.assertEquals(commentListFound.stream().filter(x -> x.getUser().getId().equals(userList.get(0).getId())).count(), 3);
        Assert.assertEquals(commentListFound.stream().filter(x -> x.getUser().getId().equals(userList.get(1).getId())).count(), 1);
    }

    @Test
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

    @Test
    public void findByIdTest() {
        List<User> userList = createUserDomain();
        List<Comment> commentList = createCommentDomain(userList);
        assertCreateBasicDomainModelAndAssert(userList, commentList);
        for (Comment comment : commentList) {
            Assert.assertTrue(commentDao.findById(comment.getId()).getContent().equals(comment.getContent()));
        }
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
