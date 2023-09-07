package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.Utils.Utils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CreateCategoryApi {
    CreateCategoryApi createCateApi = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CreateCategoryApi.class);
    @Multipart
    @POST("api/v1/categories/create")
    Call<Categories> createCate(
            @Header("Authorization") String token,
            @Part("category") RequestBody category,
            @Part MultipartBody.Part image);
}
