package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.dto.CommentCreateDTO;
import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import com.monsterhunters.pa165.service.CommentService;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by babcang
 *
 * @author Babcan G
 */
@Transactional
@ContextConfiguration(classes = MappingConfiguration.class)
public class CommentFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CommentFacade commentFacade;

    private Long id1 = 1L;
    private UserCreateDTO userCreateDTO;
    private Comment comment1;
    private Comment comment2;
    private CommentDTO commentDTO1;
    private CommentDTO commentDTO2;
    private List<Comment> comments;
    private List<CommentDTO> DTOcomments;

    @BeforeClass
    private void setup() {
        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("mail@mail.com");
        userCreateDTO.setNickname("nickname");
        userCreateDTO.setPlainPassword("password");
        userFacade.registerUser(userCreateDTO);
    }

    @AfterMethod
    private void delete() {
        List<CommentDTO> commentsDTO = commentFacade.getAllComments();
        for (CommentDTO comment : commentsDTO) {
            commentFacade.deleteComment(comment.getId());
        }
    }

    @Test
    public void shouldCreateComment() {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", id1);
        Long id = commentFacade.createComment(commentCreateDTO);

        assertEquals(commentFacade.getCommentById(id).getId(), id);
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", id1);
        Long id = commentFacade.createComment(commentCreateDTO);
        commentFacade.deleteComment(id);

        assertEquals(commentFacade.getAllComments().size(), 0);
    }

    @Test
    public void shouldGetCommentById() {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", id1);
        Long id = commentFacade.createComment(commentCreateDTO);
        CommentDTO commentDTO = commentFacade.getAllComments().get(0);

        assertEquals(commentFacade.getCommentById(id), commentDTO);
        assertNull(commentFacade.getCommentById(2L));
    }

    @Test
    public void shouldGetAllComment() {
        CommentCreateDTO commentCreateDTO1 = createDTO("This is comment", id1);
        Long idOne = commentFacade.createComment(commentCreateDTO1);
        CommentCreateDTO commentCreateDTO2 = createDTO("This is second comment", id1);
        Long idTwo = commentFacade.createComment(commentCreateDTO2);

        assertEquals(commentFacade.getAllComments().size(), 2);
    }

    @Test
    public void shouldGetCommentByUserNickname() {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", id1);
        Long id = commentFacade.createComment(commentCreateDTO);
        String nikcname = userCreateDTO.getNickname();
        CommentDTO commentDTO = commentFacade.getCommentById(id);

        assertTrue(commentFacade.getCommentsByUserNickname(nikcname).contains(commentDTO));
        assertEquals(commentFacade.getCommentsByUserNickname("Rintintin").size(), 0);
    }

    private CommentCreateDTO createDTO(String content, Long id) {
        CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setContent(content);
        commentCreateDTO.setUserId(id);
        return commentCreateDTO;
    }
}
