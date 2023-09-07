package com.longg.apiretrofitdemo.Register;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public SignUpRequest(String email, String firstName, String lastName, String username, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
