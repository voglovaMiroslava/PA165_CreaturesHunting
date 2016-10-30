package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.User;

import java.util.List;

/**
 * Created by Snurka on 10/30/2016.
 */
public interface UserDao {

    User findById(Long id);

    User findByNickname(String nickname);

    User findByEmail(String email);

    List<User> getAll();

    void create(User user);

    void delete(User user);

    void update(User user);

}
