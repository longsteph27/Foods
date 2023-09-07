package com.longg.apiretrofitdemo.SharedPreferences;

import android.content.Context;

import com.google.gson.Gson;
import com.longg.apiretrofitdemo.Model.User;
import com.longg.apiretrofitdemo.Model.UserInAccount;

public class DataLocalManager {

    private static final String FREF_USER_NAME_PASS = "FREF_USER_NAME_PASS";
    private static final String FREF_USER = "FREF_USER";
    private static DataLocalManager intance;
    private Get_Set_SharedPreferences getUserSharedPreferences;

    public static void init(Context context){
        intance = new DataLocalManager();
        intance.getUserSharedPreferences = new Get_Set_SharedPreferences(context);
    }
    public static DataLocalManager getInstance(){
        if(intance == null){
            intance = new DataLocalManager();
        }
        return intance;
    }
    public static void setUserName(UserNamePass usernp){
        Gson gson = new Gson();
        String struspass = gson.toJson(usernp);
        DataLocalManager.getInstance().getUserSharedPreferences.putStringvalue(FREF_USER_NAME_PASS, struspass);
    }

    public static UserNamePass getUserName(){
        String str =  DataLocalManager.getInstance().getUserSharedPreferences.getStringValue(FREF_USER_NAME_PASS);
        Gson gson = new Gson();
        UserNamePass userNamePass = gson.fromJson(str, UserNamePass.class);
        return userNamePass;
    }
    public static void setUser(UserInAccount user){
        Gson gson = new Gson();
        String struser = gson.toJson(user);
        DataLocalManager.getInstance().getUserSharedPreferences.putStringvalue(FREF_USER, struser);
    }
    public static UserInAccount getUser(){
        String str =  DataLocalManager.getInstance().getUserSharedPreferences.getStringValue(FREF_USER);
        Gson gson = new Gson();
        UserInAccount user = gson.fromJson(str, UserInAccount.class);
        return user;
    }
}
