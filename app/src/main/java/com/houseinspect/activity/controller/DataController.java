package com.houseinspect.activity.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.houseinspect.model.FormModel.RegisterData;


/**
 * Created by Lalit on 10/15/2016.
 */
public class DataController {

    private static final String USER_DATA= "com.houseinspect.user_data";
    private SharedPreferences preferences;

    public DataController(Context context) {
        preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    public void clearAllData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_DATA, null);
        editor.apply();
    }


    public RegisterData getUserData(){
        String userData =  preferences.getString(USER_DATA,null);
        if(userData == null)
            return  null;
        return ((new Gson()).fromJson(userData, RegisterData.class));
    }

    public void updateUserData(RegisterData registerData){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_DATA,(new Gson()).toJson(registerData));
        editor.apply();
    }
}
