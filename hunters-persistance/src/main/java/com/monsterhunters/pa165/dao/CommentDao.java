package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;

import java.util.List;

/**
 * Created by babcang on 29.10.2016.
 *
 * @author Babcan G
 */
public interface CommentDao {

    /**
     * Find comment by id
     *
     * @param id is generated id of comment
     * @return comment specified by id
     */
    public Comment findById(Long id);

    /**
     * Create new comment - persist to db
     *
     * @param c is the instance of new comment
     */
    public void create(Comment c);

    /**
     * Update information about comment
     *
     * @param c indicates which instance want to update
     */
    public void update(Comment c);

    /**
     * Delete comment from db
     *
     * @param c indicates which instance want to delete
     */
    public void delete(Comment c);

    /**
     * Return list of all comments
     *
     * @return list of all comments
     */
    public List<Comment> findAll();

    /**
     * Return list of all comments created by specified user
     *
     * @param user is user who comments we want to find
     * @return list of comments from user
     */
    public List<Comment> findByUser(User user);

}


