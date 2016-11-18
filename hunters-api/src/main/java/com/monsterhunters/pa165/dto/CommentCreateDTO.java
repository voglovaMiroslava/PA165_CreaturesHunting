package com.monsterhunters.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

public class CommentCreateDTO {

    @NotNull
    @Size(min=5, max=400)
    private String content;

    @NotNull
    private Long userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        CommentCreateDTO other = (CommentCreateDTO) obj;
        if (content == null) {
            if (other.getContent() != null)
                return false;
        } else if (!content.equals(other.getContent()))
            return false;
        else if (userId != other.userId)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        //result = prime * result * userId;
        return result;
    }
}
