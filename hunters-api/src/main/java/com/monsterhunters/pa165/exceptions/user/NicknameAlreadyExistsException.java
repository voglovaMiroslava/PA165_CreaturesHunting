package com.monsterhunters.pa165.exceptions.user;

/**
 * Created by Snurka on 11/24/2016.
 */
public class NicknameAlreadyExistsException extends RuntimeException
{

    private final String nickname;

    public NicknameAlreadyExistsException(String nickname)
    {
        super(String.format("User with nickname %s is already registered", nickname));
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }

}
