package com.longg.apiretrofitdemo.flagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longg.apiretrofitdemo.Aapter.FoodsApdapter;
import com.longg.apiretrofitdemo.Activity.LoginActivity;
import com.longg.apiretrofitdemo.Activity.MainActivity;
import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.SharedPreferences.DataLocalManager;
import com.longg.apiretrofitdemo.api.FoodApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodFragment extends Fragment {
    private View mView;
    private MainActivity mMainActivity;
    private RecyclerView rcvallfood;
    private List<Foods> arrfoods;
    private ProgressDialog mprogressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_food, container, false);
        mMainActivity = (MainActivity) getActivity();
        mprogressDialog = new ProgressDialog(mMainActivity);
        mprogressDialog.setMessage("Please wait....");
        mprogressDialog.show();
        arrfoods = new ArrayList<>();
        rcvallfood = mView.findViewById(R.id.rcvfoods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvallfood.setLayoutManager(linearLayoutManager);
        CallApi(LoginActivity.token);
        return mView;
    }
    private void CallApi(String token) {
        //ApiGetFood apiGetFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGetFood.class);
        Log.e("a", LoginActivity.token);
        FoodApi.foodApi.getfood("Bearer " + token).enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                if (LoginActivity.checklogin==1){
                    Toast.makeText(mMainActivity, "Hello " + DataLocalManager.getUser().getUser().getLastName(), Toast.LENGTH_SHORT).show();
                }
                LoginActivity.checklogin++;
                arrfoods = response.body();
                FoodsApdapter foodsApdapter = new FoodsApdapter(arrfoods, mMainActivity);
                rcvallfood.setAdapter(foodsApdapter);
                mprogressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Foods>> call, Throwable t) {
                Toast.makeText(mMainActivity, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}