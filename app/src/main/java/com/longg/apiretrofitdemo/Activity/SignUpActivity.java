package com.longg.apiretrofitdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.longg.apiretrofitdemo.Login.LoginResponse;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.Register.SignUpRequest;
import com.longg.apiretrofitdemo.Register.SignUpResponse;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.SharedPreferences.UserNamePass;
import com.longg.apiretrofitdemo.api.SignupApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText edtusername, edtpassword, edtemail, edtfirstname, edtlastname;
    Button btnsignup, btnback;
    public static String username="";
    public static String token = "";
    public static int checklogin=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtemail = findViewById(R.id.edt_email);
        edtfirstname = findViewById(R.id.edt_first_name);
        edtlastname = findViewById(R.id.edt_last_name);
        edtusername = findViewById(R.id.edt_username);
        edtpassword = findViewById(R.id.edt_password);
        btnsignup = findViewById(R.id.btn_sign_up);
        btnback = findViewById(R.id.btn_back);

        UserNamePass userNamePass = new UserNamePass();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditext()){
                    userNamePass.setUsername(edtusername.getText().toString());
                    userNamePass.setPassword(edtpassword.getText().toString());
                    callApiSingup(userNamePass);
                }else {

                }

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkEditext() {
        String emailIn = edtemail.getText().toString();
        if(!emailIn.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()){
            edtemail.setError("email ko hop le");
            return true;
        }
        if(edtfirstname.getText().toString() != null){
            edtfirstname.setError("Empty");
            return true;
        }if(edtlastname.getText().toString() != null){
            edtlastname.setError("Empty");
            return true;
        }
        if(edtusername.getText().toString() != null){
            edtusername.setError("Empty");
            return true;
        }
        if(edtpassword.getText().toString() != null){
            edtpassword.setError("Empty");
            return true;
        }
        return false;
    }

    private void callApiSingup(UserNamePass userNamePass) {
        SignUpRequest signupRequest = new SignUpRequest(edtemail.getText().toString(),edtfirstname.getText().toString(), edtlastname.getText().toString(), edtusername.getText().toString(), edtpassword.getText().toString());
        Call<SignUpResponse> call = SignupApi.SIGNUP_API.signup(signupRequest);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse signUpResponse = response.body();
                String accessToken = signUpResponse.getAccessToken();
                token = accessToken;
                DataLocalManager.setUserName(userNamePass);
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this,"Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}