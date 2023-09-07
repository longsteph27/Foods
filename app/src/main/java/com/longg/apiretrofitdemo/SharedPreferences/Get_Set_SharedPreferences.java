package com.longg.apiretrofitdemo.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Get_Set_SharedPreferences {
    private static final String Get_Set_SharedPreferences = "Get_Set_SharedPreferences";
    private Context mcontext;

    public Get_Set_SharedPreferences(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void putStringvalue(String key, String value){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Get_Set_SharedPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Get_Set_SharedPreferences, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}
