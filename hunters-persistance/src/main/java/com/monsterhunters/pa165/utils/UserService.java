package com.monsterhunters.pa165.utils;

import com.monsterhunters.pa165.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {

    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a user with hashed password.
     */
    public User createUser(String nickname, String email, String password, boolean isAdmin)
    {
        String passwordHash = passwordEncoder.encode(password);
        return new User(nickname, email, passwordHash, isAdmin);
    }

}
