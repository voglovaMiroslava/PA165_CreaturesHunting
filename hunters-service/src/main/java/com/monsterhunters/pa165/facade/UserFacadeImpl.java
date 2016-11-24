package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.dto.UserAuthenticateDTO;
import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.exceptions.user.UserDoesNotExistsException;
import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Snurka on 11/20/2016.
 */

@Service
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private MappingService mappingService;

    @Autowired
    private UserService userService;

    @Override
    public List<UserDTO> getAllUsers() {
        return mappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return checkNullAndMap(userService.findById(id));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return checkNullAndMap(userService.findByEmail(email));
    }

    @Override
    public UserDTO getUserByNickname(String nickname) {
        return checkNullAndMap(userService.findByNickname(nickname));
    }

    @Override
    public UserDTO registerUser(UserCreateDTO userCreateDTO) {
        User user =  mappingService.mapTo(userCreateDTO, User.class);
        userService.registerUser(user, userCreateDTO.getPlainPassword());
        return UserDTO.create(user);
    }

    @Override
    public boolean authenticateUser(UserAuthenticateDTO userAuthenticateDTO) {
        User user = userService.findByNickname(userAuthenticateDTO.getNickname());
        if(user == null) {
            throw new UserDoesNotExistsException(userAuthenticateDTO.getNickname());
        }
        return userService.authenticate(
                user,
                userAuthenticateDTO.getPassword()
        );
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        return userService.isAdmin(mappingService.mapTo(userDTO, User.class));
    }

        private UserDTO checkNullAndMap(User user) {
        return (user == null) ? null : mappingService.mapTo(user, UserDTO.class);
    }
}
