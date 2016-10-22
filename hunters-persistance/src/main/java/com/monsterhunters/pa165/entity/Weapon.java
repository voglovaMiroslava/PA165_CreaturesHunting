package com.monsterhunters.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by babcang on 22.10.2016.
 * @author Babcan G
 */

@Entity
public class Weapon {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false,unique=true)
    private String name;

    @Column
    private int gunReach;

    @NotNull
    @Column(nullable=false)
    private int ammo;

    @Column
    private int damage;


    //TODO create new list and add methods
/*
    @ManyToMany
    private List<Type> effectiveAgainst;
*/

    @OneToMany()
    private List<Comment> comments;


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

/*
    public List<Type> getEffectiveAgainst() {
        return effectiveAgainst;
    }

    public void setEffectiveAgainst(List<Type> effectiveAgainst) {
        this.effectiveAgainst = effectiveAgainst;
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null )
            return false;
        if(!(o instanceof Weapon))
            return false;

        Weapon other = (Weapon) o;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }else if (!name.equals(other.getName()))
            return false;
        if (ammo != other.getAmmo())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name==null) ? 0 : name.hashCode());
        result = prime * result + ammo;
        return result;
    }
}
