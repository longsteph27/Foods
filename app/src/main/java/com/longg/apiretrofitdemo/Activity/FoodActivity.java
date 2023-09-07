package com.longg.apiretrofitdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.longg.apiretrofitdemo.Aapter.FoodsApdapter;
import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.api.FoodApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {
    private RecyclerView rcvallfood;
    private List<Foods> arrfoods;
    FloatingActionButton create;
    BottomNavigationView botnavgtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main);
        arrfoods = new ArrayList<>();
        rcvallfood = findViewById(R.id.rcvfoods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvallfood.setLayoutManager(linearLayoutManager);
        CallApi(LoginActivity.token);
    }

    private void CallApi(String token) {
        //ApiGetFood apiGetFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGetFood.class);
        Log.e("a", LoginActivity.token);
        FoodApi.foodApi.getfood("Bearer " + token).enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                if (LoginActivity.checklogin==1){
                    Toast.makeText(FoodActivity.this, "Call Api successful", Toast.LENGTH_SHORT).show();
                }
                LoginActivity.checklogin++;
                arrfoods = response.body();
                FoodsApdapter foodsApdapter = new FoodsApdapter(arrfoods, getApplicationContext());
                rcvallfood.setAdapter(foodsApdapter);
            }

            @Override
            public void onFailure(Call<List<Foods>> call, Throwable t) {
                Toast.makeText(FoodActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}