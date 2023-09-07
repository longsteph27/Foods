package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Model.Account;
import com.longg.apiretrofitdemo.Model.User;
import com.longg.apiretrofitdemo.Model.UserInAccount;
import com.longg.apiretrofitdemo.Utils.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AccountApi {
    AccountApi PROFILE_API = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccountApi.class);
    @GET("api/v1/accounts/{username}")
    Call<UserInAccount> getinfo(@Header("Authorization") String token,
                       @Path("username") String username);
}
