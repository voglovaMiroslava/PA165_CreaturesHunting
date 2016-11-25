package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.dao.MonsterDao;
import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link WeaponService}.
 * <p>
 * Created by babcang
 *
 * @author Babcan G
 */

@Service
public class WeaponServiceImpl implements WeaponService {

    @Autowired
    private WeaponDao weaponDao;

    @Autowired
    MonsterDao monsterDao;

    @Override
    public Weapon createWeapon(Weapon w) throws HuntersServiceException {
        if (w == null) {
            throw new IllegalArgumentException("Weapon is null");
        }
        try {
            weaponDao.create(w);
        } catch (Throwable e) {
            throw new HuntersServiceException("Something went wrong. " +
                    "Weapon " + w.getName() + " cannot be created", e);
        }
        return w;
    }

    @Override
    public void deleteWeapon(Weapon w) throws HuntersServiceException {
        if (w == null) {
            throw new IllegalArgumentException("Weapon is null");
        }
        try {
            weaponDao.delete(w);
        } catch (Throwable e) {
            throw new HuntersServiceException("Weapon " + w.getName() + " cannot be deleted", e);

        }
    }

    @Override
    public Weapon updateWeapon(Weapon w) throws HuntersServiceException {
        if (w == null) {
            throw new IllegalArgumentException("Weapon is null");
        }
        try {
            weaponDao.update(w);
        } catch (Throwable e) {
            throw new HuntersServiceException("Weapon " + w.getName() + " cannot be updated", e);

        }
        return w;
    }

    @Override
    public void addComment(Weapon w, Comment c) throws HuntersServiceException {
        if (w.getComments().contains(c)) {
            throw new HuntersServiceException("Same comment already exists for this weapon."
                    + " Weapon ID:" + w.getId() + " Comment ID:" + c.getId());
        }
        try {
            w.addComment(c);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot add comment " + c +
                    " to weapon " + w, e);
        }
    }

    @Override
    public void removeComment(Weapon w, Comment c) throws HuntersServiceException {
        try {
            if (w.getComments().contains(c)) {
                w.removeComment(c);
            } else {
                throw new HuntersServiceException("Cannot remove comment " + c +
                        " from weapon " + w + ". Comment doesn't belong to weapon.");
            }
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot remove comment " + c +
                    " from weapon " + w, e);
        }
    }

    @Override
    public void addEffectiveAgainst(Weapon w, MonsterType m) throws HuntersServiceException {
        if (w.getEffectiveAgainst().contains(m)) {
            throw new HuntersServiceException("Monster type is already at list of effective against."
                    + " Weapon ID:" + w.getId() + " Monster type:" + m.toString());
        }
        try {
            w.addEffectiveAgainst(m);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot add monster type " + m +
                    " to weapon " + w, e);
        }

    }

    @Override
    public void removeEffectiveAgainst(Weapon w, MonsterType m) throws HuntersServiceException {
        try {
            if (w.getEffectiveAgainst().contains(m)) {
                w.removeEffectiveAgainst(m);
            } else {
                throw new HuntersServiceException("Cannot remove monster type " + m +
                        " from weapon " + w + ". It doesn't belong to weapon.");
            }
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot remove monster type " + m +
                    " from weapon " + w, e);
        }
    }

    @Override
    public List<Weapon> findAll() throws HuntersServiceException {
        try {
            return weaponDao.findAll();
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get list of weapons", e);
        }
    }

    @Override
    public Weapon findById(Long id) throws HuntersServiceException {
        try {
            return weaponDao.findById(id);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get weapon with id:" + id, e);
        }
    }

    @Override
    public Weapon findByName(String wName) throws HuntersServiceException {
        try {
            return weaponDao.findByName(wName);
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get weapon with name:" + wName, e);
        }
    }

    @Override
    public List<Monster> findKillableMonsters(Weapon w) {
        if (w == null) {
            throw new IllegalArgumentException("Weapon is null");
        }
        List<Monster> monsters;
        List<Monster> killableMonsters = new ArrayList<>();
        try {
            monsters = monsterDao.findAll();
        } catch (Throwable e) {
            throw new HuntersServiceException("Cannot get list of monsters", e);
        }
        for (Monster m : monsters) {
            if (IsMonsterKillable(w, m)) {
                killableMonsters.add(m);
            }
        }
        return killableMonsters;
    }

    //Test if weapon is able to kill monster. 
    private boolean IsMonsterKillable(Weapon w, Monster m) {
        if (w == null) {
            throw new IllegalArgumentException("Weapon is null");
        }
        if (m == null) {
            throw new IllegalArgumentException("Monster is null");
        }
        int powerOfMonster = m.getPower();
        int weaponAmmo = w.getAmmo();
        int weaponDamage = w.getDamage();
        for (MonsterType mType : m.getTypes()) {
            if (w.getEffectiveAgainst().contains(mType)) {
                do {
                    powerOfMonster = powerOfMonster - weaponDamage;
                    weaponAmmo--;
                } while ((powerOfMonster > 0) && (weaponAmmo > 0));
                if (powerOfMonster <= 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
