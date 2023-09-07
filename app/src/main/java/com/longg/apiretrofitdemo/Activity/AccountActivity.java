package com.longg.apiretrofitdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longg.apiretrofitdemo.R;

public class AccountActivity extends AppCompatActivity {
    TextView tvname, tvemail;
    LinearLayout editprofile, changepass, changeavt, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        editprofile = findViewById(R.id.edit_profile);
        changepass = findViewById(R.id.change_password);
        changeavt = findViewById(R.id.change_avatar);
        tvemail = findViewById(R.id.tv_email_profile);
        logout = findViewById(R.id.log_out);
        setSelect();
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setSelect() {

    }
}