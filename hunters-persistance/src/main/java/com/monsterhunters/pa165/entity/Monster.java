package com.monsterhunters.pa165.entity;

import com.monsterhunters.pa165.enums.MonsterType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Miroslava Voglova
 */
@Entity
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private Double height;

    private Double weight;

    private int power;

    @ManyToOne
    private Location location;

    @Enumerated
    @NotNull
    private List<MonsterType> types = new ArrayList<>();

    public Monster() {
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<MonsterType> getTypes() {
        return Collections.unmodifiableList(types);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.getName());
        hash = 89 * hash + Objects.hashCode(this.getHeight());
        hash = 89 * hash + Objects.hashCode(this.getWeight());
        hash = 89 * hash + this.getPower();
        hash = 89 * hash + Objects.hashCode(this.getLocation());
        hash = 89 * hash + Objects.hashCode(this.getTypes());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Monster)) {
            return false;
        }
        final Monster other = (Monster) obj;
        if (this.getPower() != other.getPower()) {
            return false;
        }
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.getHeight(), other.getHeight())) {
            return false;
        }
        if (!Objects.equals(this.getWeight(), other.getWeight())) {
            return false;
        }
        if (!Objects.equals(this.getLocation(), other.getLocation())) {
            return false;
        }
        return Objects.equals(this.getTypes(), other.getTypes());
    } 
}
