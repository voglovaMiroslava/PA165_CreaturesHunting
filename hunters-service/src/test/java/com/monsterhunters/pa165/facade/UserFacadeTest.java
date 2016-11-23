package com.monsterhunters.pa165.facade;

import com.monsterhunters.pa165.service.MappingService;
import com.monsterhunters.pa165.service.UserService;
import com.monsterhunters.pa165.service.config.MappingConfiguration;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

/**
 * Created by Snurka on 11/23/2016.
 */
@ContextConfiguration(classes = {MappingConfiguration.class})
public class UserFacadeTest {

    // TODO: Finish me :) 

    @Mock
    private MappingService mappingService;

    @Mock
    private UserService userService;

    @Test
    public void getUserByIdTest() {

    }

    @Test
    public void getUserByEmailTest() {

    }

    @Test
    public void getUserByNicknameTest() {

    }

    @Test
    public void registerUserTest() {

    }

    @Test
    public void authenticateUserTest() {

    }

    @Test
    public void isAdminTest() {

    }




}
