package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Register.SignUpRequest;
import com.longg.apiretrofitdemo.Register.SignUpResponse;
import com.longg.apiretrofitdemo.Utils.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupApi {
    SignupApi SIGNUP_API = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignupApi.class);
    @POST("api/v1/auth/register")
    Call<SignUpResponse> signup(@Body SignUpRequest signupRequest);
}
