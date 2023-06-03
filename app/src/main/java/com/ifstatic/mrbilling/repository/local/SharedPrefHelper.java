package com.ifstatic.mrbilling.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.ifstatic.mrbilling.base.MyBaseApplication;

public class SharedPrefHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static SharedPrefHelper sharedPrefHelper;

    private SharedPrefHelper(){
        sharedPreferences = MyBaseApplication.getApplication().getSharedPreferences("MRBilling", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefHelper getInstance(){
        if(sharedPrefHelper == null){
            sharedPrefHelper = new SharedPrefHelper();
        }
        return sharedPrefHelper;
    }
    public void setUidOfUser(String uid){
        editor.putString("uid",uid);
        editor.apply();
    }

}
