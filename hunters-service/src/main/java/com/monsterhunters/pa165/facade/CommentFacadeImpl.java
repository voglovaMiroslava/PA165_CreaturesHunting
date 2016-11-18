package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.CommentCreateDTO;
import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.service.CommentService;
import com.monsterhunters.pa165.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

public class CommentFacadeImpl implements CommentFacade {

    @Autowired
    private CommentService commentService;

//    @Autowired
//    private UserService userService;

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createComment(CommentCreateDTO c) {
        Comment mappedComment = mappingService.mapTo(c, Comment.class);
        Comment newComment = commentService.createComment(mappedComment);
        return newComment.getId();
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentService.findById(commentId);
        commentService.deleteComment(comment);
    }

    @Override
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment == null)
            return null;
        else {
            return mappingService.mapTo(comment, CommentDTO.class);
        }
    }

    @Override
    public List<CommentDTO> getAllComments() {
        List <Comment> comments = commentService.findAll();
        if (comments == null)
            return null;
        else {
            return mappingService.mapTo(comments, CommentDTO.class);
        }
    }

//    @Override
//    public List<CommentDTO> getCommentsByUserNickname(String nickname) {
//        User user = userService.findByNickname(nickname);
//        List<Comment> comments = commentService.findByUser(user);
//        if (comments == null)
//            return null;
//        else {
//            return mappingService.mapTo(comments, CommentDTO.class);
//        }
//    }
}
