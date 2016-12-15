package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;

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
    public boolean create(Location location) {
        if (em.contains(location)) {
            return false;
        }
        em.persist(location);
        //em.flush();
        return true;
    }
    
    @Override
    public List<Monster> getMonstersWithLocation(Location l) {
        try {
            return em.createQuery("Select m from Monster m where m.location = :location", Monster.class)
                    .setParameter("location", l)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean delete(Location location) throws NullPointerException {
        if (location == null) {
            throw new NullPointerException("Location is null. Nothing to delete");
        }
        if (!em.contains(location)) {
            return false;
        }

        List<Monster> monsterList = getMonstersWithLocation(location);
        for (Monster monster : monsterList) {
            em.remove(monster);
        }

        for (Comment comment : location.getComments()) {
            em.remove(comment);
        }
        em.remove(location);
        return true;
    }

    @Override
    public Location update(Location location) throws IllegalArgumentException {
        Location loc = findByName(location.getName());
        
        if(loc != null && !Objects.equals(location.getId(), loc.getId()))
            throw new IllegalArgumentException("Location with that name allready exists.");
        return em.merge(location);
    }

    @Override
    public List<Location> findAll() {
        return em.createQuery("select l from Location l", Location.class)
                .getResultList();
    }
}
