package com.monsterhunters.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Snurka on 11/24/2016.
 */
public class UserChangePassDTO {

    @NotNull
    private String nickname;
    @NotNull
    private String oldPassword;
    @NotNull
    @Size(min = 7, max = 20)
    // just overkill for this app
    //@Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "Please provide")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String password) {
        this.oldPassword = password;
    }

}
