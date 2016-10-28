package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * LocationDao implementation
 *
 * @author Tomas Durcak
 */
@Repository
@Transactional
public class LocationDaoImpl implements LocationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Location findById(Long id) {
        return em.find(Location.class, id);
    }

    @Override
    public Location findByName(String name) {
        try {
            return em.createQuery("select c from Location c where c.name = :name",
                    Location.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public void create(Location location) {
        em.persist(location);
        //em.flush();
    }

    private List<Monster> getMonstersWithLocation(Location l) {
        try {
            return em.createQuery("Select m from Monster m where m.location = :location", Monster.class)
                    .setParameter("location", l)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(Location location) throws IllegalArgumentException {
        List<Monster> mList= getMonstersWithLocation(location);
        for(Monster monster: mList)
            em.remove(monster);
        
        em.remove(location);
    }

    @Override
    public void update(Location location) {
        em.merge(location);
    }

    @Override
    public List<Location> findAll() {
        return em.createQuery("select l from Location l", Location.class)
                .getResultList();
    }
}
