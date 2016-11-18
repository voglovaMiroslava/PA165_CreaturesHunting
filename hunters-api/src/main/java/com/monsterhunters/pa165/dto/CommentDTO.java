package com.monsterhunters.pa165.dto;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

public class CommentDTO {

    private Long id;

    private UserDTO user;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        CommentDTO other = (CommentDTO) obj;
        if (content == null) {
            if (other.getContent() != null)
                return false;
        } else if (!content.equals(other.getContent()))
            return false;
        else if (!user.equals(other.getUser()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                '}';
    }
}
