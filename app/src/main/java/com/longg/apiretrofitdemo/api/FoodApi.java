package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.Utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

//https://calories--tracking--app.herokuapp.com/api/v1/categories/all
public interface FoodApi {
    FoodApi foodApi = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi.class);
    @GET("api/v1/foods/all")
    Call<List<Foods>> getfood(@Header("Authorization") String token);
}

