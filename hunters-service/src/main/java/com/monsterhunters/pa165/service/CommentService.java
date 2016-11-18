package com.monsterhunters.pa165.service;

import java.util.*;
import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.User;
import org.springframework.stereotype.Service;

/**
 * An interface that defines a service access to the {@link Comment} entity.
 *
 * Created by babcang on 17.11.2016.
 *
 * @author Babcan G
 */

@Service
public interface CommentService {

    Comment createComment(Comment c);

    void deleteComment(Comment c);

    Comment findById(Long id);

    List<Comment> findAll();

    List<Comment> findByUser(User user);

}
