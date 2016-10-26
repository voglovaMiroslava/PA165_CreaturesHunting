package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Monster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Miroslava Voglova
 */
@Transactional
@Repository
public class MonsterDaoImpl implements MonsterDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Monster findById(Long id) {
        return em.find(Monster.class, id);
    }

    @Override
    public void create(Monster m) {
        em.persist(m);
    }

    @Override
    public void update(Monster m) {
        em.merge(m);
    }

    @Override
    public void delete(Monster m) {
        em.remove(m);
    }

    @Override
    public List<Monster> findAll() {
        return em.createQuery("Select m from Monster m", Monster.class).getResultList();
    }

    @Override
    public Monster findByName(String name) {
        try {
            return em.createQuery("Select m from Monster m where m.name = :name", Monster.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
