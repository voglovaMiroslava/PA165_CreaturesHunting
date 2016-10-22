package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Weapon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * This class implements Data Access Object of Entity Weapon
 * Created by babcang on 22.10.2016.
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
        em.persist(w);
    }

    @Override
    public void delete(Weapon w) {
        em.remove(em.contains(w) ? w : em.merge(w));
    }

    @Override
    public List<Weapon> findAll() {
        return em.createQuery("select w from Weapon w", Weapon.class)
                .getResultList();
    }

    @Override
    public Weapon findByName(String name) {
        try {
            return em
                    .createQuery("select w from  Weapon w where name = :name",
                            Weapon.class).setParameter(":name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public Weapon update(Weapon w) {
        return em.merge(w);
    }
}
