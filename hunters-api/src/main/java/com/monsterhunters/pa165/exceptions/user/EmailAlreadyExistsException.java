package com.monsterhunters.pa165.exceptions.user;

/**
 * Created by Snurka on 11/24/2016.
 */
public class EmailAlreadyExistsException extends RuntimeException
{

    private final String email;

    public EmailAlreadyExistsException(String email)
    {
        super(String.format("User with email %s is already registered", email));
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

}
