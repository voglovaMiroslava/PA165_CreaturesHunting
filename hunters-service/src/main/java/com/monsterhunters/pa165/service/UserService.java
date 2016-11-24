package com.monsterhunters.pa165.service;

import com.monsterhunters.pa165.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Snurka on 11/20/2016.
 */
@Service
public interface UserService {

    /**
     * Returns found User
     *
     * @param id UserId
     * @return Found User
     */
    User findById(Long id);

    /**
     * Returns list of all users
     *
     * @return list of Users objects
     */
    List<User> findAll();

    /**
     * Returns found User
     *
     * @param nickname
     * @return User
     */
    User findByNickname(String nickname);


    /**
     * Returns found User
     *
     * @param email
     * @return User
     */
    User findByEmail(String email);

    /**
     * Registers new user
     *
     * @param user object
     */
    void registerUser(User user, String plainPassword);

    /**
     * Verifies new user
     * @param user
     * @param plainPassword
     * @return
     */
    boolean authenticate(User user, String plainPassword);

    /**
     * changes password to new one
     * @param user
     * @param plainOldPassword
     * @param plainNewPassword
     */
    void changePassword(User user, String plainNewPassword);

    /**
     * Removes User
     *
     * @param user object
     */
    void remove(User user);


    /**
     * check if user is admin
     * @param user
     * @return
     */
    boolean isAdmin(User user);
}
