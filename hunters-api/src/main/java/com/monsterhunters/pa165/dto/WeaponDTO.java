package com.monsterhunters.pa165.dto;

import com.monsterhunters.pa165.enums.MonsterType;

import java.util.*;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

public class WeaponDTO {

    private Long id;

    private String name;

    private int gunReach;

    private int ammo;

    private int damage;

    private Set<MonsterType> effectiveAgainst = new HashSet<>();

    private Set<CommentDTO> comments = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGunReach() {
        return gunReach;
    }

    public void setGunReach(int gunReach) {
        this.gunReach = gunReach;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Set<MonsterType> getEffectiveAgainst() {
        return effectiveAgainst;
    }

    public void setEffectiveAgainst(Set<MonsterType> effectiveAgainst) {
        this.effectiveAgainst = effectiveAgainst;
    }

    public void addEffectiveAgainst(MonsterType monsterType) {effectiveAgainst.add(monsterType);}

    public void removeEffectiveAgainst(MonsterType monsterType) {effectiveAgainst.remove(monsterType);}

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    public void addComment(CommentDTO comment) {
        comments.add(comment);
    }

    public void removeComment(CommentDTO comment) {comments.remove(comment);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        WeaponDTO other = (WeaponDTO) obj;
        if (name == null) {
            if (other.getName() != null)
                return false;
        } else if (!name.equals(other.getName()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "WeaponDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gunReach=" + gunReach +
                ", ammo=" + ammo +
                ", damage=" + damage +
                ", effectiveAgainst=" + effectiveAgainst +
                ", comments=" + comments +
                '}';
    }
}
