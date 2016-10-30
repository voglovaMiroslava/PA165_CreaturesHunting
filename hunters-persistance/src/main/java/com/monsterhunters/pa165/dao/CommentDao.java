package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;

import java.util.List;

/**
 * Created by Snurka on 10/30/2016.
 */
public interface CommentDao {

    Comment findById(Long id);

    Comment findByUser(User user);

    List<Comment> getAll();

    void create(Comment comment);

    void delete(Comment comment);

    void update(Comment comment);

}
