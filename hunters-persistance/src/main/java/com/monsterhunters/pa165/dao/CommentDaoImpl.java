package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Snurka on 10/30/2016.
 */
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment findById(Long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public Comment findByUser(User user) {
        try {
            return entityManager.createQuery("select c from Comment c where c.user= :user", Comment.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Comment> getAll() {
        return entityManager.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public void create(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void delete(Comment comment) {
        entityManager.remove(comment);
    }

    @Override
    public void update(Comment comment) {
        entityManager.merge(comment);
    }
}
