package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.Utils.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DeleteCategoryApi {
    DeleteCategoryApi DELETE_CATEGORY_API = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteCategoryApi.class);
    @DELETE("api/v1/categories/delete/{id}")
    Call<Void> deletecate(@Header("Authorization") String token,
                                @Path("id") int id);
}
