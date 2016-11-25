package com.monsterhunters.pa165.entity;

import com.monsterhunters.pa165.enums.MonsterType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by babcang on 22.10.2016.
 *
 * @author Babcan G
 */

@Entity
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private int gunReach;

    @Column
    private int ammo;

    @Column
    private int damage;

    @ElementCollection(targetClass = MonsterType.class)
    @Enumerated(EnumType.STRING)
    private Set<MonsterType> effectiveAgainst = new HashSet<>();

    @OneToMany()
    @JoinColumn(name="Weapon_FK")
    private Set<Comment> comments = new HashSet<>();

    public Weapon() {
    }

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
        return Collections.unmodifiableSet(effectiveAgainst);
    }

    public void setEffectiveAgainst(Set<MonsterType> effectiveAgainst) {
        this.effectiveAgainst = effectiveAgainst;
    }

    public void addEffectiveAgainst(MonsterType monsterType) {
        effectiveAgainst.add(monsterType);
    }

    public void removeEffectiveAgainst(MonsterType monsterType) {
        effectiveAgainst.remove(monsterType);
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment c) {
        comments.add(c);
    }

    public void removeComment(Comment c) {
        comments.remove(c);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Weapon))
            return false;

        Weapon other = (Weapon) o;
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
        return "Weapon{" +
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
