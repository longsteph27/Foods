package com.longg.apiretrofitdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longg.apiretrofitdemo.Aapter.CategoriesApdapter;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.api.CategoriesApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView rcvallcate;
    private List<Categories> arrcate;
    FloatingActionButton create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        arrcate = new ArrayList<>();
        rcvallcate = findViewById(R.id.rcvCategories);
        create = findViewById(R.id.btn_create_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvallcate.setLayoutManager(linearLayoutManager);
        CallApi(LoginActivity.token);
        create.setOnClickListener(v -> {
            Intent intent = new Intent(CategoriesActivity.this, CreateFoodActivity.class);
            startActivity(intent);
        });
    }



    private void CallApi(String token) {
        //ApiGetFood apiGetFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGetFood.class);
        Log.e("a", LoginActivity.token);
        CategoriesApi.CATEGORIES_API.getCategories("Bearer " + token).enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (LoginActivity.checklogin==1){
                    Toast.makeText(CategoriesActivity.this, "Call Api successful", Toast.LENGTH_SHORT).show();
                }
                LoginActivity.checklogin++;
                arrcate = response.body();
                CategoriesApdapter categoriesApdapter = new CategoriesApdapter(arrcate, getApplicationContext());
                rcvallcate.setAdapter(categoriesApdapter);
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
