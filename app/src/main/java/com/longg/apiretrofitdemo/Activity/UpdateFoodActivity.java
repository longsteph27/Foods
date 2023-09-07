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
import com.longg.apiretrofitdemo.Update.UpdateRequest;
import com.longg.apiretrofitdemo.Update.UpdateResponse;
import com.longg.apiretrofitdemo.api.CategoriesApi;
import com.longg.apiretrofitdemo.api.UpdateFoodApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFoodActivity extends AppCompatActivity {

    EditText edtid, edtname, edtdescription, edtenergyPerServing,
            edtfat, edtprotein, edtcarb, edtcategoryId, edtcategoryName;
    Button btnupdate, btnselectimg, btncancel;
    ImageView imggallery, imgcategory;
    TextView txtvnamecate;
    AutoCompleteTextView auto_cpl_txt;
    TextInputLayout inputLayout;
    private List<String> strs;
    private List<Categories> arrcate;
    private Uri mUri;
    Foods foods = new Foods();
    Category category = new Category();
    private ProgressDialog mprogressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setFields();
        setText();
        edtid.setEnabled(false);
        arrcate = new ArrayList<>();
        strs = new ArrayList<>();
        category.setId(Integer.parseInt(getIntent().getExtras().getString("cate_id")));
        CallApiCate(LoginActivity.token);
        auto_cpl_txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCateInfo((String) parent.getItemAtPosition(position), arrcate);
            }
        });
        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait....");
        UpdateRequest updateRequest = new UpdateRequest();

        btnupdate.setOnClickListener(v -> {

            if (checkError()) {
            } else {
                setFoods();
                callApi(LoginActivity.token);
            }

        });
        btnselectimg.setOnClickListener(v -> {
            ImagePicker.with(UpdateFoodActivity.this)
                    .crop()
                    /*.compress(1024)
                    .maxResultSize(1080, 1080)*/
                    .start();
        });

        btncancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateFoodActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean checkError() {

        if (edtname.getText() == null) {
            edtname.setError("Thiếu thông tin");
            edtname.setFocusable(true);
            return true;
        }
        if (String.valueOf(edtenergyPerServing.getText()) == null || !checkFloat(edtenergyPerServing.getText().toString())) {
            edtenergyPerServing.setError("Thiếu thông tin EnergyPerServing");
            edtenergyPerServing.setFocusable(true);
            return true;
        }
        if (String.valueOf(edtcarb.getText()) == null || !checkFloat(edtcarb.getText().toString())) {
            edtcarb.setError("Thiếu thông tin Carb");
            edtcarb.setFocusable(true);
            return true;
        }

        if (!checkFloat(edtfat.getText().toString())) {
            edtcarb.setError("Thiếu thông tin Carb");
            edtcarb.setFocusable(true);
            return true;
        }

        if (!checkFloat(edtprotein.getText().toString())) {
            edtcarb.setError("Thiếu thông tin Carb");
            edtcarb.setFocusable(true);
            return true;
        }

        return false;

    }

    private boolean checkInt(String i){
        try {
            Integer.parseInt(i);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean checkFloat(String i){
        try {
            Float.parseFloat(i);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void setCateInfo(String cateName, List<Categories> categories) {
        categories.forEach((item) -> {
            if (item.getName() == cateName) {
                //category.setName(cateName);
                //category.setImage(item.getImage());
                category.setId(item.getId());
                Glide.with(context).load(item.getImage()).into(imgcategory);
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

    private void CallApiCate(String token) {
        CategoriesApi.CATEGORIES_API.getCategories("Bearer " + token).enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (LoginActivity.checklogin == 1) {
                    Toast.makeText(UpdateFoodActivity.this, "Call Api successful", Toast.LENGTH_SHORT).show();
                }
                LoginActivity.checklogin++;
                arrcate = response.body();
                strs = getNameCate(arrcate);
                ArrayAdapter<String> cateAdapter = new ArrayAdapter<>(UpdateFoodActivity.this, R.layout.list_item, strs);
                auto_cpl_txt.setAdapter(cateAdapter);
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(UpdateFoodActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
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

    private void callApi(String token) {
        if (mUri == null) {
            mprogressDialog.show();
            RequestBody requestBodyFoods = RequestBody.create(MediaType.parse("multipart/form-data"), foods.toJson());
            UpdateFoodApi.updatefoodApi.update(
                    "Bearer " + token,
                    requestBodyFoods,
                    null).enqueue(new Callback<UpdateResponse>() {
                @Override
                public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                    if (response.code() == 200) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateFoodActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateFoodActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (response.code() == 201) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateFoodActivity.this, "Created", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code() == 400) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateFoodActivity.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UpdateResponse> call, Throwable t) {
                    mprogressDialog.dismiss();
                    Toast.makeText(UpdateFoodActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateFoodActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            mprogressDialog.show();
            RequestBody requestBodyFoods = RequestBody.create(MediaType.parse("multipart/form-data"), foods.toJson());
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            Log.e("File: ", strRealPath);
            File fileImg = new File(strRealPath);
            RequestBody requestBodyImageFoods = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
            MultipartBody.Part multiPartBodyImg = MultipartBody.Part.createFormData("image", fileImg.getName(), requestBodyImageFoods);
            UpdateFoodApi.updatefoodApi.update(
                    "Bearer " + token,
                    requestBodyFoods,
                    multiPartBodyImg).enqueue(new Callback<UpdateResponse>() {
                @Override
                public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                    if (response.code() == 200) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateFoodActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateFoodActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (response.code() == 201) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateFoodActivity.this, "Created", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code() == 400) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateFoodActivity.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UpdateResponse> call, Throwable t) {
                    mprogressDialog.dismiss();
                    Toast.makeText(UpdateFoodActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateFoodActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    }


    public void setText() {
        edtcarb.setText(getIntent().getExtras().getString("carb"));
        //edtcategoryId.setText(getIntent().getExtras().getString("cate_id"));
        Log.e("img", getIntent().getExtras().getString("img"));
        Glide.with(context).load(getIntent().getExtras().getString("cate_image")).into(imgcategory);
        //txtvnamecate.setText(getIntent().getExtras().getString("cate_name"));
        auto_cpl_txt.setText(getIntent().getExtras().getString("cate_name"));
        edtdescription.setText(getIntent().getExtras().getString("description"));
        edtenergyPerServing.setText(getIntent().getExtras().getString("energyPerServing"));
        edtfat.setText(getIntent().getExtras().getString("Fat"));
        edtid.setText(getIntent().getExtras().getString("id"));
        Glide.with(context).load(getIntent().getExtras().getString("img")).into(imggallery);
        edtname.setText(getIntent().getExtras().getString("name"));
        edtprotein.setText(getIntent().getExtras().getString("protein"));
    }

    private void setFoods() {
        foods.setCarb(Float.parseFloat(edtcarb.getText().toString()));
        foods.setDescription(edtdescription.getText().toString());
        foods.setFat(Float.parseFloat(edtfat.getText().toString()));
        foods.setName(edtname.getText().toString());
        foods.setId(Integer.parseInt(edtid.getText().toString()));
        foods.setProtein(Float.parseFloat(edtprotein.getText().toString()));
        foods.setEnergyPerServing(Float.parseFloat(edtenergyPerServing.getText().toString()));
        foods.setCategory(category);
    }

    private void setFields() {
        btncancel = findViewById(R.id.btn_cancle_update);
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
        btnupdate = findViewById(R.id.btn_save_update);
        auto_cpl_txt = findViewById(R.id.auto_complete_txt);
        inputLayout = findViewById(R.id.txtInputLayout);
        txtvnamecate = findViewById(R.id.cate_name_txt);
    }
}
