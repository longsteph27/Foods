package com.longg.apiretrofitdemo.Activity;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.Model.Category;
import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.RealPathUtil;
import com.longg.apiretrofitdemo.api.CategoriesApi;
import com.longg.apiretrofitdemo.api.CreateFoodApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFoodActivity extends AppCompatActivity {
    EditText edtid, edtname, edtdescription, edtenergyPerServing,
            edtfat, edtprotein, edtcarb;
    Button btncreate, btnselectimg, btncancelcreate;
    ImageView imggallery, imgcategory;
    TextView txtvnamecate;
    AutoCompleteTextView auto_cpl_txt;
    TextInputLayout inputLayout;
    private List<String> strs;
    private List<Categories> arrcate;
    Foods foods = new Foods();
    private Uri mUri;
    Category category = new Category();
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setFields();
        arrcate = new ArrayList<>();
        strs = new ArrayList<>();
        CallApiCate(LoginActivity.token);
        auto_cpl_txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCateInfo((String) parent.getItemAtPosition(position), arrcate);
            }
        });
        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait....");


        btnselectimg.setOnClickListener(v -> {
            ImagePicker.with(CreateFoodActivity.this)
                    .crop()
                    //.compress(1024)
                    //.maxResultSize(1080, 1080)
                    .start();
        });

        btncancelcreate.setOnClickListener(v -> {
            Intent intent = new Intent(CreateFoodActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btncreate.setOnClickListener(v -> {

            if (checkError()) {
            } else {
                setFoods(foods);
                callApi(LoginActivity.token, foods);
            }
        });
    }

    private boolean checkError() {
        if (mUri == null) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Không có ảnh, vui lòng chọn lại!").show();
            return true;
        }
        if (edtname.getText() == null) {
            edtname.setError("Thiếu thông tin");
            edtname.setFocusable(true);
            return true;
        }
        if (String.valueOf(edtenergyPerServing.getText()) == null) {
            edtenergyPerServing.setError("Thiếu thông tin EnergyPerServing");
            edtenergyPerServing.setFocusable(true);
            return true;
        }
        if (String.valueOf(edtcarb.getText()) == null) {
            edtcarb.setError("Thiếu thông tin Carb");
            edtcarb.setFocusable(true);
            return true;
        }

        return false;

    }

    private void setCateInfo(String cateName, List<Categories> categories) {
        categories.forEach((item) -> {
            if (item.getName() == cateName) {
                //category.setName(cateName);
                //category.setImage(item.getImage());
                category.setId(item.getId());
                Glide.with(context).load(item.getImage()).into(imgcategory);
                txtvnamecate.setText(item.getName());
            }
        });
    }

    private void CallApiCate(String token) {
        CategoriesApi.CATEGORIES_API.getCategories("Bearer " + token).enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (LoginActivity.checklogin == 1) {
                    Toast.makeText(CreateFoodActivity.this, "Call Api successful", Toast.LENGTH_SHORT).show();
                }
                LoginActivity.checklogin++;
                arrcate = response.body();
                strs = getNameCate(arrcate);
                ArrayAdapter<String> cateAdapter = new ArrayAdapter<>(CreateFoodActivity.this, R.layout.list_item, strs);
                auto_cpl_txt.setAdapter(cateAdapter);
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(CreateFoodActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getNameCate(List<Categories> categories) {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            str.add(categories.get(i).getName());
        }
        return str;
    }

    private void callApi(String token, Foods foods) {
        mprogressDialog.show();
        RequestBody requestBodyFoods = RequestBody.create(MediaType.parse("multipart/form-data"), foods.toJson());
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        Log.e("Long", strRealPath);
        File fileImg = new File(strRealPath);
        RequestBody requestBodyImageFoods = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
        MultipartBody.Part multiPartBodyImg = MultipartBody.Part.createFormData("image", fileImg.getName(), requestBodyImageFoods);
        CreateFoodApi.CREATEAPI.create(
                "Bearer " + token,
                requestBodyFoods,
                multiPartBodyImg).enqueue(new Callback<Foods>() {
            @Override
            public void onResponse(Call<Foods> call, Response<Foods> response) {
                if (response.code() == 200) {
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateFoodActivity.this, "Call Api Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateFoodActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                if (response.code() == 201) {
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateFoodActivity.this, "Khong hop le 201", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 400) {
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateFoodActivity.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 401) {
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateFoodActivity.this, "Khong hop le 401", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 403) {
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateFoodActivity.this, "Khong hop le 403", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 404) {
                    mprogressDialog.dismiss();
                    Toast.makeText(CreateFoodActivity.this, "Khong hop le 404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Foods> call, Throwable t) {
                mprogressDialog.dismiss();
                Toast.makeText(CreateFoodActivity.this, "Call Api fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        mUri = uri;
        imggallery.setImageURI(uri);
    }

    private void setFoods(Foods foods) {
        foods.setCarb(Float.parseFloat(edtcarb.getText().toString()));
        foods.setDescription(edtdescription.getText().toString());
        foods.setFat(Float.parseFloat(edtfat.getText().toString()));
        foods.setName(edtname.getText().toString());
        //foods.setId(Integer.parseInt(edtid.getText().toString()));
        foods.setProtein(Float.parseFloat(edtprotein.getText().toString()));
        foods.setEnergyPerServing(Float.parseFloat(edtenergyPerServing.getText().toString()));
        //category.setName(edtcategoryName.getText().toString());
        foods.setCategory(category);
    }

    private void setFields() {
        btncancelcreate = findViewById(R.id.btn_cancle_create);
        btnselectimg = findViewById(R.id.btn_select_img);
        imggallery = findViewById(R.id.img_from_gallery);
        edtcarb = findViewById(R.id.edt_carb);
        edtdescription = findViewById(R.id.edt_description);
        edtfat = findViewById(R.id.edt_fat);
        edtid = findViewById(R.id.edt_id);
        edtname = findViewById(R.id.edt_name);
        edtenergyPerServing = findViewById(R.id.edt_energyPerServing);
        edtprotein = findViewById(R.id.edt_protein);
        //edtcategoryId = findViewById(R.id.edt_category_id);
        imgcategory = findViewById(R.id.img_category);
        //edtcategoryName = findViewById(R.id.edt_category_name);
        btncreate = findViewById(R.id.btn_save_create_food);
        auto_cpl_txt = findViewById(R.id.auto_complete_txt);
        inputLayout = findViewById(R.id.txtInputLayout);
        txtvnamecate = findViewById(R.id.cate_name_txt);
    }
}