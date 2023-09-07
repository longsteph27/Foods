package com.longg.apiretrofitdemo.Model;

public class Account {
    String username, password, role;
    UserInAccount user;

    public Account() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserInAccount getUser() {
        return user;
    }

    public void setUser(UserInAccount userInAccount) {
        this.user = userInAccount;
    }
}
