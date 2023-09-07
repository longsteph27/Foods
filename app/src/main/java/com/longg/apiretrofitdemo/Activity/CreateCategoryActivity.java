package com.longg.apiretrofitdemo.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.RealPathUtil;
import com.longg.apiretrofitdemo.api.CreateCategoryApi;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCategoryActivity extends AppCompatActivity {
    EditText edtname;
    ImageView imgcategory;
    Button btncreate, btnselectimg, btncancelcreate;
    private Uri mUri;
    Categories categories = new Categories();
    private ProgressDialog mprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        btncancelcreate = findViewById(R.id.btn_back);
        edtname = findViewById(R.id.edt_name_cate);
        btnselectimg = findViewById(R.id.btn_select_img);
        imgcategory = findViewById(R.id.img_from_gallery);
        btncreate = findViewById(R.id.btn_save_create_cate);
        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait....");
        btnselectimg.setOnClickListener(v ->{
            ImagePicker.with(CreateCategoryActivity.this)
                    .crop()
                    //.compress(1024)
                    //.maxResultSize(1080, 1080)
                    .start();
        });
        btncancelcreate.setOnClickListener(v ->{
            Intent intent = new Intent(CreateCategoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btncreate.setOnClickListener(v -> {
            if(mUri != null && edtname.getText().toString() != null){
                setCate();
                callApiCreate(LoginActivity.token);
            }
            else {
                new AlertDialog.Builder(this).setTitle("Error").setMessage("Thieu anh hoac thong tin").show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        mUri = uri;
        imgcategory.setImageURI(uri);
    }

    private void callApiCreate(String token) {
        mprogressDialog.show();
        RequestBody requestBodyCate = RequestBody.create(MediaType.parse("multipart/form-data"), categories.toJson());
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        Log.e("Long", strRealPath);
        File fileImg = new File(strRealPath);
        RequestBody requestBodyImageCate = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
        MultipartBody.Part multiPartBodyImg = MultipartBody.Part.createFormData("image", fileImg.getName(), requestBodyImageCate);
        CreateCategoryApi.createCateApi.createCate(
                "Bearer " + token,
                requestBodyCate,
                multiPartBodyImg).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if(response.code() == 200){
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateCategoryActivity.this, "Tao thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateCategoryActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                if(response.code() == 201){
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateCategoryActivity.this, "Khong hop le 201", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 400){
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateCategoryActivity.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 401){
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateCategoryActivity.this, "Khong hop le 401", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 403){
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateCategoryActivity.this, "Khong hop le 403", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 404){
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateCategoryActivity.this, "Khong hop le 404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(CreateCategoryActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCate() {
        categories.setName(edtname.getText().toString());
    }
}