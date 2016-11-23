package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = MappingConfiguration.class)
public class CommentServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CommentDao commentDao;

    @Autowired
    @InjectMocks
    private CommentService commentService;

    private List<Comment> expectedComments = new ArrayList<>();

    private User testUser = new User("user", "user@user.com", "myPasswordHash", false);

    @BeforeMethod
    private void prepareCommentForTest() {
        Comment comment1 = createComment("This is comment one");
        Comment comment2 = createComment("This is comment two");
        comment1.setId(1L);
        comment2.setId(2L);
        expectedComments.add(comment1);
        expectedComments.add(comment2);
    }

    @AfterMethod
    private void clearList() {
        expectedComments.clear();
    }

    @BeforeClass
    private void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateComment() {
        int startSize = expectedComments.size();
        Comment newComment = createComment("This is new Comment");

        doAnswer(invoke -> {
            Comment mockingComment = invoke.getArgumentAt(0, Comment.class);
            mockingComment.setId((long) (expectedComments.size() + 1));
            expectedComments.add(mockingComment);
            return mockingComment;
        }).when(commentDao).create(newComment);

        commentService.createComment(newComment);
        verify(commentDao).create(newComment);
        assertEquals(expectedComments.size(), startSize + 1);
    }

    @Test
    public void shouldDeleteComment() {
        int startSize = expectedComments.size();
        doAnswer(invoke -> {
            Comment mockingComment = invoke.getArgumentAt(0, Comment.class);
            expectedComments.remove(mockingComment);
            return mockingComment;
        }).when(commentDao).delete(any(Comment.class));
        Comment commentToDelete = expectedComments.get(0);
        commentService.deleteComment(commentToDelete);

        verify(commentDao).delete(commentToDelete);
        assertEquals(expectedComments.size(), startSize - 1);
    }

    @Test
    public void shouldFindById() {
        Comment foundComment;
        when(commentDao.findById(1L)).thenReturn(expectedComments.get(0));
        foundComment = commentService.findById(1L);

        verify(commentDao).findById(1L);
        assertEquals(foundComment, expectedComments.get(0));
    }

    @Test
    public void shouldFindAllComments() {
        when(commentDao.findAll()).thenReturn(expectedComments);

        assertEquals(commentService.findAll(), Collections.unmodifiableList(expectedComments));
        verify(commentDao).findAll();
    }

    @Test
    public void shouldFindByUser() {
        when(commentDao.findByUser(testUser)).thenReturn(expectedComments);

        assertEquals(commentService.findByUser(testUser), expectedComments);
        verify(commentDao).findByUser(testUser);
    }

    //simple method to create comment
    private Comment createComment(String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(testUser);
        return comment;
    }

}
