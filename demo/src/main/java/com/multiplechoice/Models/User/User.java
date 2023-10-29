package com.multiplechoice.Models.User;

public class User {
    private String iduser;
    private String realName;
    private String username;
    private String password;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String score;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private boolean isAdmin;

    public String getIduser() {
        return iduser;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

}
