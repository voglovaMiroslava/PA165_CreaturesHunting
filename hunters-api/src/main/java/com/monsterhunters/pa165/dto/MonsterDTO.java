package com.monsterhunters.pa165.dto;

import com.monsterhunters.pa165.enums.MonsterType;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Miroslava Voglova
 */
public class MonsterDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 400)
    private String name;

    @Min(0)
    private Double height;
    
    @Min(0)
    private Double weight;
    
    @Min(0)
    private int power;
    
    @NotNull
    private LocationDTO location;

    @NotNull
    private Set<MonsterType> types;

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
