package com.longg.apiretrofitdemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.longg.apiretrofitdemo.Model.UserInAccount;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.api.AccountApi;
import com.longg.apiretrofitdemo.flagment.AccountFragment;
import com.longg.apiretrofitdemo.flagment.CategoriesFragment;
import com.longg.apiretrofitdemo.flagment.FoodFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btncreate;
    FoodFragment foodFragment = new FoodFragment();
    CategoriesFragment categoriesFragment = new CategoriesFragment();
    BottomNavigationView botnavgtview;
    UserInAccount user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botnavgtview = findViewById(R.id.navigation_view);
        btncreate = findViewById(R.id.btn_create);
        user = new UserInAccount();
        //callProfileApi(LoginActivity.token, DataLocalManager.getUserName().getUsername());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, foodFragment).commit();
        botnavgtview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, foodFragment)
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.category_menu:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, categoriesFragment)
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new AccountFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                }
                return false;
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreate();
            }
        });
    }

    private void showDialogCreate() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout_create, null);
        TextView createfood = view.findViewById(R.id.create_food);
        TextView createcate = view.findViewById(R.id.create_category);
        createcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCate = new Intent(MainActivity.this, CreateCategoryActivity.class);
                startActivity(intentCate);
            }
        });
        createfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateFoodActivity.class);
                startActivity(intent);
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
    /*private void callProfileApi(String token, String username) {
        AccountApi.PROFILE_API.getinfo(
                "Bearer " + token,
                username).enqueue(new Callback<UserInAccount>() {
            @Override
            public void onResponse(Call<UserInAccount> call, Response<UserInAccount> response) {
                if(response.code() == 200){
                    user = response.body();
                    DataLocalManager.setUser(user);
                    Toast.makeText(MainActivity.this, "Call API Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInAccount> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}