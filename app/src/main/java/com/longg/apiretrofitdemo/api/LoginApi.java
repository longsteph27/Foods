package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Login.LoginRequest;
import com.longg.apiretrofitdemo.Login.LoginResponse;
import com.longg.apiretrofitdemo.Utils.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    LoginApi loginApi = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi.class);
    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

