package com.longg.apiretrofitdemo.api;

import com.longg.apiretrofitdemo.Model.User;
import com.longg.apiretrofitdemo.Update.UpdateResponse;
import com.longg.apiretrofitdemo.Utils.Utils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UpdateProfileApi {
    UpdateProfileApi PROFILE_API = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UpdateProfileApi.class);
    @Multipart
    @PUT("api/v1/users/update")
    Call<User> update(
            @Header("Authorization") String token,
            @Part("user") RequestBody user,
            @Part MultipartBody.Part image);
}
