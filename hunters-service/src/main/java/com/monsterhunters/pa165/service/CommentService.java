package com.monsterhunters.pa165.service;

import java.util.*;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import org.springframework.stereotype.Service;

/**
 * An interface that defines a service access to the {@link Comment} entity.
 *
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

@Service
public interface CommentService {

    /** Method create new Comment
     *
     * @param c is comment which will be created
     * @return created comment
     */
    Comment createComment(Comment c);

    /** Delete specified Comment
     *
     * @param c is comment which should be deleted
     */
    void deleteComment(Comment c);

    /** Method find comment with specified id
     *
     * @param id is id of comment
     * @return found comment
     */
    Comment findById(Long id);

    /** Method find list of all comments
     *
     * @return list of comments
     */
    List<Comment> findAll();

    /** Method find comment by specified User
     *
     * @param user is user who comments want to find
     * @return list of comments
     */
    List<Comment> findByUser(User user);

}
