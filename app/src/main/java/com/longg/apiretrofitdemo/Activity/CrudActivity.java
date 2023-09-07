package com.longg.apiretrofitdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.longg.apiretrofitdemo.R;

public class CrudActivity extends AppCompatActivity {

    Button btnCategory, btnfood, btnacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        btnCategory = findViewById(R.id.btn_category_all_op);
        btnfood = findViewById(R.id.btn_food_all_op);
        btnacc = findViewById(R.id.btn_account);

        btnCategory.setOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, CategoriesActivity.class);
            startActivity(intent);
        });
        btnfood.setOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, FoodActivity.class);
            startActivity(intent);
        });
        btnacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrudActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}