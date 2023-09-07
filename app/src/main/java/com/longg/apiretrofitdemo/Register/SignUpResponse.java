package com.longg.apiretrofitdemo.Register;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
