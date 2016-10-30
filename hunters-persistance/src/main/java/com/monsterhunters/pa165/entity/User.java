package com.monsterhunters.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Snurka on 10/30/2016.
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String nickname;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean isAdmin;

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        if (isAdmin != user.isAdmin) {
            return false;
        }
        if (!id.equals(user.id)) {
            return false;
        }
        if (!nickname.equals(user.nickname)) {
            return false;
        }
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nickname.hashCode();
        result = 89 * result + email.hashCode();
        result = 313 * result + (isAdmin ? 1 : 0);
        return result;
    }
}