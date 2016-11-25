package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CommentService}.
 * <p>
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment createComment(Comment c) throws HuntersServiceException {
        if (c == null) {
            throw new IllegalArgumentException("Comment is null");
        }
        try {
            commentDao.create(c);
        } catch (Throwable e) {
            throw new HuntersServiceException("Something went wrong. " +
                    "Comment " + c + " cannot be created", e);
        }
        return c;
    }

    @Override
    public void deleteComment(Comment c) throws HuntersServiceException {
        if (c == null) {
            throw new IllegalArgumentException("Comment is null");
        }
        try {
            commentDao.delete(c);
        } catch (Throwable e) {
            throw new HuntersServiceException("Comment " + c + " cannot be deleted", e);
        }

    }

    @Override
    public Comment findById(Long id) throws HuntersServiceException {
        try {
            return commentDao.findById(id);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get comment with id:" + id, e);
        }
    }

    @Override
    public List<Comment> findAll() throws HuntersServiceException {
        try {
            return commentDao.findAll();
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get list of comments", e);
        }
    }

    @Override
    public List<Comment> findByUser(User user) throws HuntersServiceException {
        try {
            return commentDao.findByUser(user);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get comment of User: " + user.getNickname(), e);
        }
    }
}
