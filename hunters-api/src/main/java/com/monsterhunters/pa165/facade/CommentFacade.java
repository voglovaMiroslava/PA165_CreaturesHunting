package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.CommentCreateDTO;
import com.monsterhunters.pa165.dto.CommentDTO;

import java.util.List;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

public interface CommentFacade {

    Long createComment(CommentCreateDTO c);

    void deleteComment(Long commentId);

    CommentDTO getCommentById(Long commentId);

    List<CommentDTO> getAllComments();

//    List<CommentDTO> getCommentsByUserNickname(String nickname);

}
