package com.longg.apiretrofitdemo.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.longg.apiretrofitdemo.Model.User;
import com.longg.apiretrofitdemo.Model.UserInAccount;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.RealPathUtil;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.api.UpdateProfileApi;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAvtActivity extends AppCompatActivity {
    Button btnavt, btnsave, btncancel;
    ImageView imgedit;
    Uri mUri;
    UserInAccount userInAccount;
    private ProgressDialog mprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avt);
        imgedit = findViewById(R.id.image_edit_avt_gallery);
        btnsave = findViewById(R.id.btn_save_update);
        btncancel = findViewById(R.id.btn_back);
        btnavt = findViewById(R.id.btn_select_avt);
        userInAccount = DataLocalManager.getUser();
        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait....");
        if (DataLocalManager.getUser().getUser().getImage() != null) {
            Glide.with(this).load(DataLocalManager.getUser().getUser().getImage()).into(imgedit);
        }
        btnavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ChangeAvtActivity.this)
                        .crop()
                        /*.compress(1024)
                        .maxResultSize(1080, 1080)*/
                        .start();
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUri!= null){
                    callApiAvt(LoginActivity.token);
                }
                else {
                    new AlertDialog.Builder(ChangeAvtActivity.this).setTitle("Error").setMessage("Pls pick a image").show();
                }
            }
        });
        btncancel.setOnClickListener(v -> {
            Intent intent = new Intent(ChangeAvtActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void callApiAvt(String token) {
        mprogressDialog.show();
        User user = userInAccount.getUser();
        RequestBody requestBodyUser = RequestBody.create(MediaType.parse("multipart/form-data"), user.toJson());
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        Log.e("Long", strRealPath);
        File fileImg = new File(strRealPath);
        RequestBody requestBodyImageAvt = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
        MultipartBody.Part multiPartBodyImg = MultipartBody.Part.createFormData("image", fileImg.getName(), requestBodyImageAvt);

        UpdateProfileApi.PROFILE_API.update(
                "Bearer " + token,
                requestBodyUser,
                multiPartBodyImg).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    mprogressDialog.dismiss();
                    Toast.makeText(ChangeAvtActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeAvtActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                if (response.code() == 201) {
                    mprogressDialog.dismiss();
                    Toast.makeText(ChangeAvtActivity.this, "Created", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 400) {
                    mprogressDialog.dismiss();
                    Toast.makeText(ChangeAvtActivity.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                }
            }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    mprogressDialog.dismiss();
                    Toast.makeText(ChangeAvtActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeAvtActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        mUri = uri;
        imgedit.setImageURI(uri);
    }
}