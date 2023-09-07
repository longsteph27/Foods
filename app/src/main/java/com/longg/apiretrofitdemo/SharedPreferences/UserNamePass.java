package com.longg.apiretrofitdemo.SharedPreferences;

public class UserNamePass {
    String username, password;

    public UserNamePass(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserNamePass() {
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
}
