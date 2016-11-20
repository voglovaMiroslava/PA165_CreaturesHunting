package com.monsterhunters.pa165.dto;

/**
 * Created by Snurka on 11/20/2016.
 */
public class UserAuthenticateDTO {

    private String nickname;
    private String password;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
