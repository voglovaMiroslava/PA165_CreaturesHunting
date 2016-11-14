package com.monsterhunters.pa165.dto;

//import java.util.HashSet;
//import java.util.Set;
//import com.monsterhunters.pa165.dto.CommentDTO;
/**
 *
 * @author Tomas Durcak
 */
public class LocationDTO {

    private Long id;
    private String name;
    private String description;
 //   private Set<CommentDTO> comments = new HashSet<>();

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

//    public Set<CommentDTO> getComments() {
//        return comments;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public void addComment(CommentDTO comment) {
//        comments.add(comment);
//    }

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
