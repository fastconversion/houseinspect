package com.houseinspect.activity.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.houseinspect.model.HouseDataModel;

import java.io.File;
import java.util.List;

/**
 * Created by Lalit on 11/15/2016.
 */
public class HouseDataFileController {

    private final SharedPreferences preferences;
    private final static String HOME_DATA = "non_home_data";

    public HouseDataFileController(Context context, String homeKey) {
        preferences = context.getSharedPreferences(homeKey, Context.MODE_PRIVATE);
    }

    public boolean isDataAvailable() {
        String data = preferences.getString(HOME_DATA, null);
        if (data == null)
            return false;
        return true;
    }

    public HouseDataModel getNonHouseDataModel() {
        String data = preferences.getString(HOME_DATA, null);
        if (data == null)
            return (new HouseDataModel());
        return ((new Gson()).fromJson(data, HouseDataModel.class));
    }

    public void updateNonHouseModel(HouseDataModel nonHouseDataModel) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HOME_DATA, ((new Gson()).toJson(nonHouseDataModel)));
        editor.apply();
    }

    public static void deleteOldFile(String fileName) {
        File sharedPreferenceFile = new File("/data/data/com.houseinspect/shared_prefs/" + fileName + ".xml");
        sharedPreferenceFile.deleteOnExit();
    }

    public static void removeAllKeys(List<String> homeKeyList, Context context) {
        for (String homeKey : homeKeyList) {
            SharedPreferences preferences = context.getSharedPreferences(homeKey, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(HOME_DATA);
            editor.commit();
        }
    }

    public static void removeHouseData(String homeKey, Context context) {
            SharedPreferences preferences = context.getSharedPreferences(homeKey, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(HOME_DATA);
            editor.commit();
    }
}
