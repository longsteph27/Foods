package com.longg.apiretrofitdemo.Activity;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

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

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.Model.Category;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.RealPathUtil;
import com.longg.apiretrofitdemo.api.UpdateCategoryApi;
import com.longg.apiretrofitdemo.api.UpdateFoodApi;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCategoryActivity extends AppCompatActivity {
    EditText edtcategoryName;
    Button btnupdate, btnselectimg, btncancel;
    ImageView imggallerycate;
    Categories categories = new Categories();
    private Uri mUri;
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        setfirst();
        setEditText();
        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait....");
        btnupdate.setOnClickListener(v -> {
            if (edtcategoryName.getText() != null) {
                setCate();
                callApi(LoginActivity.token);
            } else {
                new AlertDialog.Builder(this).setTitle("Error").setMessage("Empty category").show();
                edtcategoryName.setFocusable(true);
            }
        });
        btnselectimg.setOnClickListener(v -> {
            ImagePicker.with(UpdateCategoryActivity.this)
                    .crop()
                    /*.compress(1024)
                    .maxResultSize(1080, 1080)*/
                    .start();
        });

        btncancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateCategoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        mUri = uri;
        imggallerycate.setImageURI(uri);
    }

    private void callApi(String token) {
        if (mUri == null) {
            mprogressDialog.show();
            RequestBody requestBodyCategory = RequestBody.create(MediaType.parse("multipart/form-data"), categories.toJson());
            UpdateCategoryApi.UPDATE_CATEGORY_API.updateCate(
                    "Bearer " + token,
                    requestBodyCategory,
                    null).enqueue(new Callback<Categories>() {
                @Override
                public void onResponse(Call<Categories> call, Response<Categories> response) {
                    if (response.code() == 200) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateCategoryActivity.this, "Update Succesful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateCategoryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else Toast.makeText(UpdateCategoryActivity.this, "Update unsuccesful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Categories> call, Throwable t) {
                    Toast.makeText(UpdateCategoryActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mprogressDialog.show();
            RequestBody requestBodyCategory = RequestBody.create(MediaType.parse("multipart/form-data"), categories.toJson());
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            Log.e("Long", strRealPath);
            File fileImg = new File(strRealPath);
            RequestBody requestBodyImageCate = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
            MultipartBody.Part multiPartBodyImg = MultipartBody.Part.createFormData("image", fileImg.getName(), requestBodyImageCate);
            UpdateCategoryApi.UPDATE_CATEGORY_API.updateCate(
                    "Bearer " + token,
                    requestBodyCategory,
                    multiPartBodyImg).enqueue(new Callback<Categories>() {
                @Override
                public void onResponse(Call<Categories> call, Response<Categories> response) {
                    if (response.code() == 200) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateCategoryActivity.this, "Update Succesful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateCategoryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (response.code() == 201) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateCategoryActivity.this, "Created", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Categories> call, Throwable t) {
                    Toast.makeText(UpdateCategoryActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setCate() {
        categories.setId(Integer.parseInt(getIntent().getExtras().getString("cate_id")));
        categories.setName(edtcategoryName.getText().toString());
    }

    private void setfirst() {
        btncancel = findViewById(R.id.btn_back);
        btnselectimg = findViewById(R.id.btn_select_img);
        imggallerycate = findViewById(R.id.img_category_from_gallery);
        btnupdate = findViewById(R.id.btn_save_update_category);
        edtcategoryName = findViewById(R.id.edt_name_category);
    }

    private void setEditText() {
        edtcategoryName.setText(getIntent().getExtras().getString("cate_name"));
        if (getIntent().getExtras().getString("cate_image") != null) {
            Glide.with(context).load(getIntent().getExtras().getString("cate_image")).into(imggallerycate);
        }
        //edtcategoryId.setText(getIntent().getExtras().getString("cate_id"));
    }
}