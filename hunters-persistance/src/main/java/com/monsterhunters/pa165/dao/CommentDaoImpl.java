package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * This class implements Data Access Object of Entity Comment
 * Created by babcang on 29.10.2016.
 *
 * @author Babcan G
 */

@Repository
@Transactional
public class CommentDaoImpl implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public void create(Comment c) {
        if (c == null) {
            throw new IllegalArgumentException(Comment.class.getName());
        }
        em.persist(c);
    }

    @Override
    public void update(Comment c) {
        if (c == null) {
            throw new IllegalArgumentException(Comment.class.getName());
        }
        em.merge(c);
    }

    @Override
    public void delete(Comment c) {
        if (c == null) {
            throw new IllegalArgumentException(Comment.class.getName());
        }
        em.remove(em.contains(c) ? c : em.merge(c));
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public List<Comment> findByUser(User user) {
        try {
            return em.createQuery("SELECT c from Comment c where c.user= :user", Comment.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
