package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Weapon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import javax.transaction.Transactional;
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
    public Weapon update(Weapon w) {
        if (w == null) throw new IllegalArgumentException(Weapon.class.getName());
        return em.merge(w);
    }

    @Override
    public void delete(Weapon w) {
        if (w == null) throw new IllegalArgumentException(Weapon.class.getName());
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
            return em.createQuery("select w from  Weapon w where w.name = :name",
                    Weapon.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

//    private List<Comment> findAssignedComments(Weapon w) {
//        if (w == null) throw new IllegalArgumentException(Weapon.class.getName());
//        try {
//            return em.createQuery("SELECT c from Comment c where c.weapon = :weapon", Comment.class)
//                    .setParameter("weapon", w)
//                    .getResultList();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }

}
