package com.monsterhunters.pa165.dto;

import com.monsterhunters.pa165.enums.MonsterType;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Miroslava Voglova
 */
public class MonsterDTO {
    private Long id;
    
    private String name;

    private Double height;

    private Double weight;

    private int power;
    
    private LocationDTO location;
    
    private Set<MonsterType> types;
    
    public Long getId() {
        return id;
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public Set<MonsterType> getTypes() {
        return types;
    }

    public void setTypes(Set<MonsterType> types) {
        this.types = types;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MonsterDTO other = (MonsterDTO) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    
}
