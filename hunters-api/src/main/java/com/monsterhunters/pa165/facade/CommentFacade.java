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

    /** Method create new Comment from CommentCreateDTO
     *
     * @param c is CommentCreateDTO object
     * @return return id of created comment
     */
    Long createComment(CommentCreateDTO c);

    /** Method delete specified comment
     *
     * @param commentId is id of comment which should be deleted
     */
    void deleteComment(Long commentId);

    /** Method get commentDTO specified by id
     *
     * @param commentId is id of comment
     * @return found CommentDTO
     */
    CommentDTO getCommentById(Long commentId);

    /** Method get all DTOcomments
     *
     * @return list of all CommentDTO
     */
    List<CommentDTO> getAllComments();

    /** Method find comments specified by user nickname
     *
     * @param nickname is nickname of user
     * @return list of all DTOcomments
     */
    List<CommentDTO> getCommentsByUserNickname(String nickname);

}
