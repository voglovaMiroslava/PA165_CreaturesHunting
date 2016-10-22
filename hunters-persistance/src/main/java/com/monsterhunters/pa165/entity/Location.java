/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterhunters.pa165.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 * Entity Location
 *
 * @author Tomas Durcak
 * @since 22.10.2016
 */
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String description;

    @ManyToMany
    private List<Monster> monsters = new ArrayList<Monster>();

    //TODO Later add list of comments with coresponding methods
    
    public Location() {
    }

    public Location(Long locationId) {
        this.id = locationId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Monster> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    public void addMonster(Monster monster) {
        this.monsters.add(monster);
    }

    public void setMonsters(Collection<Monster> monsters) {
        this.monsters = new ArrayList<>(monsters);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location other = (Location) obj;
        if (name == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!name.equals(other.getName())) {
            return false;
        }
        if (description == null) {
            if (other.getDescription() != null) {
                return false;
            }
        } else if (!description.equals(other.getDescription())) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "com.monsterhunters.pa165.entity.Location[ id=" + id + " ]";
//    }

}
