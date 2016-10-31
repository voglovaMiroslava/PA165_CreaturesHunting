package com.monsterhunters.pa165.domain.dao;

import com.monsterhunters.pa165.domain.entity.Comment;
import com.monsterhunters.pa165.domain.entity.Weapon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * This class implements Data Access Object of Entity Weapon
 * Created by babcang on 22.10.2016.
 *
 * @author Babcan G
 */

@Repository
@Transactional
public class WeaponDaoImpl implements WeaponDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Weapon findById(Long id) {
        return em.find(Weapon.class, id);
    }

    @Override
    public void create(Weapon w) {
        if (w == null) throw new IllegalArgumentException(Weapon.class.getName());
        em.persist(w);
    }

    @Override
    public void update(Weapon w) {
        if (w == null) throw new IllegalArgumentException(Weapon.class.getName());
        em.merge(w);
    }

    @Override
    public void delete(Weapon w) {
        if (w == null) throw new IllegalArgumentException(Weapon.class.getName());
        for (Comment c : w.getComments()) {
            em.remove(em.contains(c) ? c : em.merge(c));
        }
        em.remove(em.contains(w) ? w : em.merge(w));
    }

    @Override
    public List<Weapon> findAll() {
        return em.createQuery("SELECT w FROM Weapon w", Weapon.class)
                .getResultList();
    }

    @Override
    public Weapon findByName(String name) {
        if (name.isEmpty() || name == null) throw new IllegalArgumentException(Weapon.class.getName());
        try {
            return em.createQuery("select w from  Weapon w where name = :name",
                    Weapon.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
