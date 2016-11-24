package com.monsterhunters.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Snurka on 10/30/2016.
 */

@Entity
@Table(name = "MonsterUser")
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
    private String passwordHash;

    @NotNull
    @Column(nullable = false)
    private boolean isAdmin;

    public User(String nickname, String email, String passwordHash, boolean isAdmin) {
        this.nickname = nickname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User))  {
            return false;
        }
        User user = (User) o;
        if (!getNickname().equals(user.getNickname()))  {
            return false;
        }
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = getNickname().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }
}
