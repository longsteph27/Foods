package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.Utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CategoriesApi {
    CategoriesApi CATEGORIES_API = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriesApi.class);
    @GET("api/v1/categories/all")
    Call<List<Categories>> getCategories(@Header("Authorization") String token);
}
