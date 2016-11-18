package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.dao.CommentDao;
import com.monsterhunters.pa165.dao.WeaponDao;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link WeaponService}.
 *
 * Created by babcang
 *
 * @author Babcan G
 */

@Service
public class WeaponServiceImpl implements WeaponService {

    @Autowired
    private WeaponDao weaponDao;

    @Override
    public Weapon createWeapon(Weapon w) {
        weaponDao.create(w);
        return w;
    }

    @Override
    public void deleteWeapon(Weapon w) {
        weaponDao.delete(w);
    }

    @Override
    public Weapon updateWeapon(Weapon w){
        weaponDao.update(w);
        return w;
    }

    @Override
    public void addComment(Weapon w, Comment c) {
        if (w.getComments().contains(c)) {
            throw new HuntersServiceException("Same comment already exists for this weapon."
                    + " Weapon ID:" + w.getId() + " Comment ID:" + c.getId());
        }
        w.addComment(c);
    }

    @Override
    public void removeComment(Weapon w, Comment c) {
        w.removeComment(c);
    }

    @Override
    public void addEffectiveAgainst(Weapon w, MonsterType m) {
        if (w.getEffectiveAgainst().contains(m)) {
            throw new HuntersServiceException("Monster type is already at list of effective against."
                    + " Weapon ID:" + w.getId() + " Monster type:" + m.toString());
        }
        w.addEffectiveAgainst(m);
    }

    @Override
    public void removeEffectiveAgainst(Weapon w, MonsterType m) {
        w.removeEffectiveAgainst(m);
    }

    @Override
    public List<Weapon> findAll() {
        return weaponDao.findAll();
    }

    @Override
    public Weapon findById(Long id) {
        return weaponDao.findById(id);
    }

    @Override
    public Weapon findByName(String wName) {
        return weaponDao.findByName(wName);
    }
}
