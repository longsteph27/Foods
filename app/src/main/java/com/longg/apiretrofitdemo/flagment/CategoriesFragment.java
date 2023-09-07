package com.longg.apiretrofitdemo.flagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longg.apiretrofitdemo.Aapter.CategoriesApdapter;
import com.longg.apiretrofitdemo.Aapter.FoodsApdapter;
import com.longg.apiretrofitdemo.Activity.LoginActivity;
import com.longg.apiretrofitdemo.Activity.MainActivity;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.api.CategoriesApi;
import com.longg.apiretrofitdemo.api.DeleteCategoryApi;
import com.longg.apiretrofitdemo.api.FoodApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {
    private View mView;
    private MainActivity mMainActivity;
    private RecyclerView rcvallcate;
    private List<Categories> arrcate;
    FloatingActionButton create;
    private ProgressDialog mprogressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_categories, container, false);
        mMainActivity = (MainActivity) getActivity();
        mprogressDialog = new ProgressDialog(mMainActivity);
        mprogressDialog.setMessage("Please wait....");
        mprogressDialog.show();
        create = mView.findViewById(R.id.btn_create_category);
        arrcate = new ArrayList<>();
        rcvallcate = mView.findViewById(R.id.rcvCategories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvallcate.setLayoutManager(linearLayoutManager);
        /*create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCategoryApi.DELETE_CATEGORY_API.deletecate("Bearer " + LoginActivity.token,
                        23).enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {
                        if(response.code() == 200){

                            Toast.makeText(mMainActivity, "ok", Toast.LENGTH_SHORT).show();
                        }
                        if(response.code() == 400){
                            Toast.makeText(mMainActivity, "Khong the xoa", Toast.LENGTH_SHORT).show();
                        }
                        if(response.code() == 401){
                            Toast.makeText(mMainActivity, "401", Toast.LENGTH_SHORT).show();
                        }
                        if(response.code() == 403){
                            Toast.makeText(mMainActivity, "403", Toast.LENGTH_SHORT).show();
                        }
                        if(response.code() == 404){
                            Toast.makeText(mMainActivity, "404", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                        Toast.makeText(mMainActivity,"Can't Delete", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
        CallApi(LoginActivity.token);

        return mView;
    }
    private void CallApi(String token) {
        //ApiGetFood apiGetFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGetFood.class);
        Log.e("a", LoginActivity.token);
        CategoriesApi.CATEGORIES_API.getCategories("Bearer " + token).enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (LoginActivity.checklogin==1){
                    Toast.makeText(mMainActivity, "Call Api successful", Toast.LENGTH_SHORT).show();
                }
                LoginActivity.checklogin++;
                arrcate = response.body();
                CategoriesApdapter categoriesApdapter = new CategoriesApdapter(arrcate, mMainActivity);
                rcvallcate.setAdapter(categoriesApdapter);
                mprogressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(mMainActivity, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}