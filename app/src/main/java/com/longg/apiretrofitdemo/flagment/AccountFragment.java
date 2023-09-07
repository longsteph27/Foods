package com.longg.apiretrofitdemo.flagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.longg.apiretrofitdemo.Activity.ChangeAvtActivity;
import com.longg.apiretrofitdemo.Activity.LoginActivity;
import com.longg.apiretrofitdemo.Activity.MainActivity;
import com.longg.apiretrofitdemo.Activity.UpdateProfileActivity;
import com.longg.apiretrofitdemo.Model.UserInAccount;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.api.AccountApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    UserInAccount user;
    View mView;
    ImageView imgavt;
    TextView tvname, tvemail;
    LinearLayout editprofile, changepass, changeavt, logout;
    MainActivity mMainActivity;
    private ProgressDialog mprogressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        callProfileApi(LoginActivity.token, DataLocalManager.getUserName().getUsername());
        mMainActivity = (MainActivity) getActivity();
        mprogressDialog = new ProgressDialog(mMainActivity);
        mprogressDialog.setMessage("Please wait....");
        mprogressDialog.show();
        user = new UserInAccount();
        setFirst(mView);
        //fillData();
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
        changeavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, ChangeAvtActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, LoginActivity.class);
                startActivity(intent);
            }
        });
        return mView;
    }

    private void fillData() {
        tvemail.setText(DataLocalManager.getUser().getUser().getEmail());
        tvname.setText(DataLocalManager.getUser().getUser().getLastName());
        if (DataLocalManager.getUser().getUser().getImage() != null) {
            Glide.with(mView).load(DataLocalManager.getUser().getUser().getImage()).into(imgavt);
        }
    }

    private void setFirst(View mView) {
        editprofile = mView.findViewById(R.id.edit_profile);
        changepass = mView.findViewById(R.id.change_password);
        changeavt = mView.findViewById(R.id.change_avatar);
        tvemail = mView.findViewById(R.id.tv_email_profile);
        tvname = mView.findViewById(R.id.tv_name_profile);
        logout = mView.findViewById(R.id.log_out);
        imgavt = mView.findViewById(R.id.image_avt);
    }

    private void callProfileApi(String token, String username) {
        AccountApi.PROFILE_API.getinfo(
                "Bearer " + token,
                username).enqueue(new Callback<UserInAccount>() {
            @Override
            public void onResponse(Call<UserInAccount> call, Response<UserInAccount> response) {
                user = response.body();
                setpage(mView);
                saveDate(user);
                //Toast.makeText(mMainActivity, "Call API Success", Toast.LENGTH_SHORT).show();
                mprogressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserInAccount> call, Throwable t) {
                Toast.makeText(mMainActivity, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDate(UserInAccount user) {
        DataLocalManager.setUser(user);
    }

    private void setpage(View mView) {
        tvname.setText(user.getUser().getLastName());
        tvemail.setText(user.getUser().getEmail());
        if (user.getUser().getImage() != null) {
            Glide.with(mView).load(user.getUser().getImage()).into(imgavt);
        }
    }
}