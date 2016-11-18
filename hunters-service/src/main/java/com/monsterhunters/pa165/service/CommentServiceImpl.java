package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CommentService}.
 *
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment createComment(Comment c) {
        commentDao.create(c);
        return c;
    }

    @Override
    public void deleteComment(Comment c) {
        commentDao.delete(c);
    }

    @Override
    public Comment findById(Long id) {
        return commentDao.findById(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public List<Comment> findByUser(User user) {
        return commentDao.findByUser(user);
    }
}
