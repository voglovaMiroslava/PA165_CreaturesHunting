package com.monsterhunters.pa165.dao;

import com.monsterhunters.pa165.entity.User;

import java.util.List;

/**
 * Created by Snurka on 10/30/2016.
 */
public interface UserDao {

    /**
     * Find user by id
     *
     * @param id is generated id of user
     * @return user specified by id
     */
    User findById(Long id);

    /**
     * Find user by nickaname
     *
     * @param nickname is name of user
     * @return user specified by nickname
     */
    User findByNickname(String nickname);

    /**
     * Find user by email
     *
     * @param email is email of user
     * @return user specified by email
     */
    User findByEmail(String email);


    /**
     * return all users
     *
     * @return list of users
     */
    List<User> findAll();

    /**
     * Persists User into DB
     *
     * @param user object of type User
     */
    void create(User user);

    /**
     * Deletes User from DB
     *
     * @param user object of type User
     */
    void delete(User user);


    /**
     * Updates User into DB
     *
     * @param user object of type User
     */
    void update(User user);

}
