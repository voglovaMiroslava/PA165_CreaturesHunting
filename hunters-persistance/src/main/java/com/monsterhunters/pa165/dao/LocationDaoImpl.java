package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.Location;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * LocationDao implementation
 *
 * @author Tomas Durcak
 */
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
                    Location.class).setParameter(":name", name)
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

    @Override
    public void delete(Location location) throws IllegalArgumentException {
        em.remove(location);
    }
    
    @Override
    public Location update(Location location) {
        return em.merge(location);
    }

    @Override
    public List<Location> findAll() {
        return em.createQuery("select l from Location l", Location.class)
                .getResultList();
    }

//    @Override
//    public List<Location> getLocationWithMonsterType(Type type) {
//        return em.createQuery("select l from Location l join l.monsters m where :monsterType in m.type",
//                Location.class).setParameter(":monsterType", type)
//                .getResultList();
//    }
}
