package com.monsterhunters.pa165.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tomas Durcak
 */
public class LocationDTO {

    private Long id;
    
    @NotNull
    @Size(min = 3, max = 15)
    private String name;
    
    @NotNull
    @Size(min = 3, max = 50)
    private String description;
    
    private Set<CommentDTO> comments = new HashSet<>();

    public LocationDTO() {
    }

    public LocationDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<CommentDTO> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addComment(CommentDTO comment) {
        this.comments.add(comment);
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
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
        if (!(obj instanceof LocationDTO)) {
            return false;
        }
        LocationDTO other = (LocationDTO) obj;
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
