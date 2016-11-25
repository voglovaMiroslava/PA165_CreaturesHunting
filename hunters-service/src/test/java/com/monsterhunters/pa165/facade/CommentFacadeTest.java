package com.monsterhunters.pa165.facade;


import com.monsterhunters.pa165.dto.CommentCreateDTO;
import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@ContextConfiguration(classes = MappingConfiguration.class)
public class CommentFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CommentFacade commentFacade;

    private UserDTO userDTO;
    private CommentDTO comment1;
    private CommentDTO comment2;

    @BeforeMethod
    private void setup() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("mail@mail.com");
        userCreateDTO.setNickname("nickname");
        userCreateDTO.setPlainPassword("password");
        userDTO = userFacade.registerUser(userCreateDTO);
        assertTrue(userFacade.getAllUsers().contains(userDTO));
    }


    @Test
    public void shouldCreateComment() {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", userDTO.getId());
        Long id = commentFacade.createComment(commentCreateDTO);
        comment1 = commentFacade.getCommentById(id);
        assertEquals(comment1.getId(), id);
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", userDTO.getId());
        Long id = commentFacade.createComment(commentCreateDTO);
        commentFacade.deleteComment(id);

        assertEquals(commentFacade.getAllComments().size(), 0);
    }

    @Test
    public void shouldGetCommentById() {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", userDTO.getId());
        Long id = commentFacade.createComment(commentCreateDTO);
        comment1 = commentFacade.getAllComments().get(0);

        assertEquals(commentFacade.getCommentById(id), comment1);
        assertNull(commentFacade.getCommentById(2L));
    }

    @Test
    public void shouldGetAllComment() {
        CommentCreateDTO commentCreateDTO1 = createDTO("This is comment", userDTO.getId());
        Long idOne = commentFacade.createComment(commentCreateDTO1);
        comment1 = commentFacade.getCommentById(idOne);
        CommentCreateDTO commentCreateDTO2 = createDTO("This is second comment", userDTO.getId());
        Long idTwo = commentFacade.createComment(commentCreateDTO2);
        comment2 = commentFacade.getCommentById(idTwo);

        assertEquals(commentFacade.getAllComments().size(), 2);
    }

    @Test
    public void shouldGetCommentByUserNickname() {
        CommentCreateDTO commentCreateDTO = createDTO("This is comment", userDTO.getId());
        Long id = commentFacade.createComment(commentCreateDTO);
        String nickname = userDTO.getNickname();
        comment1 = commentFacade.getCommentById(id);

        assertTrue(commentFacade.getCommentsByUserNickname(nickname).contains(comment1));
        assertEquals(commentFacade.getCommentsByUserNickname("Rintintin").size(), 0);
    }

    private CommentCreateDTO createDTO(String content, Long id) {
        CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setContent(content);
        commentCreateDTO.setUserId(id);
        return commentCreateDTO;
    }
}
