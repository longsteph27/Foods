package com.longg.apiretrofitdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.longg.apiretrofitdemo.Model.User;
import com.longg.apiretrofitdemo.Model.UserInAccount;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.api.UpdateProfileApi;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    ImageView imggallerry_avt;
    TextView tvname, tvemail, tvdob;
    EditText edtfirstname, edtlastname, edtweight, edtheight;
    Button btnsave, btncancel;
    RadioGroup rdogr;
    RadioButton radioButton, rdbmale, rdbfemale;;
    DatePickerDialog datePickerDialog;
    private User user = new User();
    UserInAccount userInAccount = DataLocalManager.getUser();
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setfields();
        initDatePicker();
        tvdob.setText(getDay());
        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait....");
        setEditText();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEditext()){
                    saveUser();
                    callApiUpdate(LoginActivity.token);
                }
                else {

                }
            }
        });
        btncancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private boolean checkEditext() {

        if(edtfirstname.getText().toString() == null){
            edtfirstname.setError("Empty");
            return true;
        }if(edtlastname.getText().toString() == null){
            edtlastname.setError("Empty");
            return true;
        }
        return false;
    }

    private void saveUser() {
        int radioId = rdogr.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        if("Male".equals(radioButton.getText().toString())) user.setGender(true);
        else user.setGender(false);
        user.setId(DataLocalManager.getUser().getUser().getId());
        user.setWeight(Integer.parseInt(edtweight.getText().toString()));
        user.setHeight(Integer.parseInt(edtheight.getText().toString()));
        user.setFirstName(edtfirstname.getText().toString());
        user.setLastName(edtlastname.getText().toString());
        user.setEmail(tvemail.getText().toString());
        user.setDob(tvdob.getText().toString());


    }

    private void callApiUpdate(String token) {
            mprogressDialog.show();
            RequestBody requestBodyUser = RequestBody.create(MediaType.parse("multipart/form-data"), user.toJson());
            UpdateProfileApi.PROFILE_API.update(
                    "Bearer " + token,
                    requestBodyUser,
                    null).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        userInAccount.setUser(user);
                        DataLocalManager.setUser(userInAccount);
                        Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (response.code() == 201) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, "Created", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code() == 400) {
                        mprogressDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    mprogressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
    }

    private void setEditText() {
        tvname.setText(DataLocalManager.getUser().getUser().getLastName());
        tvemail.setText(DataLocalManager.getUser().getUser().getEmail());
        edtfirstname.setText(DataLocalManager.getUser().getUser().getFirstName());
        edtlastname.setText(DataLocalManager.getUser().getUser().getLastName());
        edtheight.setText(Integer.toString(DataLocalManager.getUser().getUser().getHeight()));
        edtweight.setText(Integer.toString(DataLocalManager.getUser().getUser().getWeight()));
        tvdob.setText(DataLocalManager.getUser().getUser().getDob());
        if(DataLocalManager.getUser().getUser().isGender()) rdbmale.setChecked(true);
        else rdbfemale.setChecked(true);
        if (DataLocalManager.getUser().getUser().getImage() != null) {
            Glide.with(this).load(DataLocalManager.getUser().getUser().getImage()).into(imggallerry_avt);
        }
    }

    private void setfields() {
        btnsave = findViewById(R.id.btn_save_update);
        btncancel = findViewById(R.id.btn_back);
        edtfirstname = findViewById(R.id.tv_first_name);
        edtlastname = findViewById(R.id.tv_last_name);
        tvname = findViewById(R.id.tv_name);
        tvemail = findViewById(R.id.tv_email);
        tvdob = findViewById(R.id.tv_dob);
        edtweight = findViewById(R.id.tv_weight);
        edtheight = findViewById(R.id.tv_height);
        imggallerry_avt = findViewById(R.id.image_edit_avt);
        rdogr = findViewById(R.id.radio_group);
        tvdob = findViewById(R.id.tv_dob);
        rdbmale = findViewById(R.id.tv_gender_male);
        rdbfemale = findViewById(R.id.tv_gender_female);
    }
    private String getDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        return year + "-" + getmoth(month) + "-" + getday(day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + getmoth(month) + "-" + getday(day);
                tvdob.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
    public String getday(int day){
        if(day > 0 && day < 10){
            return "0"+day;
        }
        else return String.valueOf(day);
    }
    public String getmoth(int month){
        if(month > 0 && month < 10){
            return "0"+month;
        }
        else return String.valueOf(month);
    }
    public void openDatePicker(View view){
        datePickerDialog.show();
    }
    public void checkButtonView(View v){
    }
}