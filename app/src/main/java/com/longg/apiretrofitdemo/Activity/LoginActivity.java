package com.longg.apiretrofitdemo.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.longg.apiretrofitdemo.Login.LoginRequest;
import com.longg.apiretrofitdemo.Login.LoginResponse;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.SharedPreferences.UserNamePass;
import com.longg.apiretrofitdemo.api.LoginApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtusername, edtpassword;
    Button btnlogin;
    TextView tvsignup;
    public static String token = "";
    public static String username = "";
    public static int checklogin=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtusername = findViewById(R.id.edt_username);
        edtpassword = findViewById(R.id.edt_password);
        tvsignup = findViewById(R.id.click_sign_up);
        UserNamePass userNamePass = new UserNamePass();
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnlogin = findViewById(R.id.btn_login);

        if(DataLocalManager.getUserName() != null){
            edtusername.setText(DataLocalManager.getUserName().getUsername());
            edtpassword.setText(DataLocalManager.getUserName().getPassword());
        }
        else{

        }

        btnlogin.setOnClickListener(v -> {
            if(edtpassword.getText() == null ){
                edtpassword.setError("Khong duoc de trong!");
                edtpassword.setFocusable(true);
            }
            else if (edtusername.getText() == null ){
                edtusername.setError("Khong duoc de trong!");
                edtusername.setFocusable(true);
            }
            else {
                LoginRequest loginRequest = new LoginRequest(edtusername.getText().toString(), edtpassword.getText().toString());
                Call<LoginResponse> call = LoginApi.loginApi.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.code() == 200){
                            checklogin ++;
                            LoginResponse loginResponse = response.body();
                            String accessToken = loginResponse.getAccessToken();
                            token = accessToken;
                            Log.e("a", token);
                            userNamePass.setUsername(edtusername.getText().toString());
                            userNamePass.setPassword(edtpassword.getText().toString());
                            DataLocalManager.setUserName(userNamePass);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            new AlertDialog.Builder(LoginActivity.this).setTitle("Error").setMessage("Tài khoản hoặc mật khẩu không chính xác!").show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }

}