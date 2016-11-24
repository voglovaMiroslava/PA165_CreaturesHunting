package com.monsterhunters.pa165.exceptions.user;

/**
 * Created by Snurka on 11/24/2016.
 */
public class UserDoesNotExistsException extends RuntimeException{

    private final String nickname;

    public UserDoesNotExistsException(String nickname)
    {
        super(String.format("User with nickname %s does not exists", nickname));
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
}
