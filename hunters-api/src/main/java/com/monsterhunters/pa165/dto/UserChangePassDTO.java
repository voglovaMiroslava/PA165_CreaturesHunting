package com.monsterhunters.pa165.dto;

/**
 * Created by Snurka on 11/24/2016.
 */
public class UserChangePassDTO {

    private UserAuthenticateDTO userAuthenticateDTO;

    private String newPassword;

    public UserAuthenticateDTO getUserAuthenticateDTO() {
        return userAuthenticateDTO;
    }

    public void setUserAuthenticateDTO(UserAuthenticateDTO userAuthenticateDTO) {
        this.userAuthenticateDTO = userAuthenticateDTO;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNickname() {
        return userAuthenticateDTO.getNickname();
    }

    public void setNickname(String nickname) {
        this.userAuthenticateDTO.setNickname(nickname);
    }

    public String getOldPassword() {
        return userAuthenticateDTO.getPassword();
    }

    public void setOldPassword(String password) {
        this.userAuthenticateDTO.setPassword(password);
    }

}
