package com.monsterhunters.pa165.domain.dao;

import com.monsterhunters.pa165.domain.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by Snurka on 10/30/2016.
 */
public interface UserDao {

    User findById(UUID id);

    User findByNickname(String nickname);

    User findByEmail(String email);

    List<User> findAll();

    void create(User user);

    void delete(User user);

    void update(User user);

}
