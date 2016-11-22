package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.UserAuthenticateDTO;
import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by Snurka on 11/20/2016.
 */
public interface UserFacade {

    /**
     * Returns list of all users
     *
     * @return list of UserDTO objects
     */
    List<UserDTO> getAllUsers();

    /**
     * Returns UserDTO with id
     *
     * @param id UserDTO id
     * @return UserDTO object
     */
    UserDTO getUserById(Long id);

    /**
     * Returns UserDTO with id
     *
     * @param email UserDTO email
     * @return UserDTO object
     */
    UserDTO getUserByEmail(String email);

    /**
     * Returns UserDTO with id
     *
     * @param nickname UserDTO nickname
     * @return UserDTO object
     */
    UserDTO getUserByNickname(String nickname);

    /**
     * Create UserDTO object
     *
     * @param userCreateDTO
     * @return userDTO id
     */
    UserDTO registerUser(UserCreateDTO userCreateDTO);

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     */
    boolean authenticateUser(UserAuthenticateDTO userAuthenticateDTO);

    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(UserDTO userDTO);




}
