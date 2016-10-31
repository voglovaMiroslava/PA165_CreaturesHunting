package com.monsterhunters.pa165.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity Location
 *
 * @author Tomas Durcak
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
    @Column(nullable = false)
    private String description;

    @OneToMany
    private Set<Comment> comments = new HashSet<>();

    public Location() {
    }

    public Location(Long locationId) {
        this.id = locationId;
    }

    public Long getId() {
        return id;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
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

}
