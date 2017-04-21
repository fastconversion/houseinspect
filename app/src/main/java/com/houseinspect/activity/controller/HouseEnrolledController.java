package com.houseinspect.activity.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Lalit on 11/15/2016.
 */
public class HouseEnrolledController {

    public static final String NON_SUB_HOME = "non_inspect_homeList";
    private static final String SUB_HOME = "sub_inspect_homeList";
    private final SharedPreferences preferences;

    public HouseEnrolledController(Context context) {
        preferences = context.getSharedPreferences(NON_SUB_HOME, Context.MODE_PRIVATE);
    }

    public HouseEnrolledController(Context context, boolean isSubsidy) {
        preferences = context.getSharedPreferences(SUB_HOME, Context.MODE_PRIVATE);
    }

    public String addNewNonSubHome(String homeKey, HouseKeyDataModel nonSubHomeListModel) {
        /*if(isHomeKeyAvailable(homeKey))
            return null;*/
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(homeKey, (new Gson()).toJson(nonSubHomeListModel));
        editor.commit();
        return homeKey;
    }

    public void updateHouseKey(String homeKey, HouseKeyDataModel nonSubHomeListModel) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(homeKey, (new Gson()).toJson(nonSubHomeListModel));
        editor.commit();
    }

    public void removeHomeKey(String homeKey) {
        if (isHomeKeyAvailable(homeKey)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(homeKey);
            editor.commit();
        }
    }

    public boolean isHomeKeyAvailable(String homeKey) {
        if (preferences.getString(homeKey, null) == null)
            return false;
        return true;
    }

    public List<HouseKeyDataModel> getAllNonSubHomes(boolean isReAssigned) {
        List<HouseKeyDataModel> homeList = new ArrayList<>();
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String str = entry.getValue().toString();
            HouseKeyDataModel houseKeyDataModel = (new Gson()).fromJson(str, HouseKeyDataModel.class);
            //// this check added to remove reassigned
            if (isReAssigned) {
                if (houseKeyDataModel.getExpectedDate() != null)
                    homeList.add(houseKeyDataModel);
            } else {
                if (houseKeyDataModel.getExpectedDate() == null)
                    homeList.add(houseKeyDataModel);
            }
        }
        if (homeList.size() > 0) {
            Collections.sort(homeList, new Comparator<HouseKeyDataModel>() {
                @Override
                public int compare(HouseKeyDataModel lhs, HouseKeyDataModel rhs) {
                    return lhs.getLongUpdatedOn() > rhs.getLongUpdatedOn() ? -1 :
                            (lhs.getLongUpdatedOn() < rhs.getLongUpdatedOn()) ? 1 : 0;
                }
            });
        }
        return homeList;
    }

    public static void removeAllEntry(Context context) {
        List<String> homeKeyList = new ArrayList<>();
        SharedPreferences preferences = context.getSharedPreferences(SUB_HOME, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            homeKeyList.add(entry.getKey());
            editor.remove(entry.getKey());
        }
        editor.commit();
        preferences = context.getSharedPreferences(NON_SUB_HOME, Context.MODE_PRIVATE);
        allEntries = preferences.getAll();
        editor = preferences.edit();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            homeKeyList.add(entry.getKey());
            editor.remove(entry.getKey());
        }
        editor.commit();
        HouseDataFileController.removeAllKeys(homeKeyList, context);
    }

    public static void removeFromList(Context context, String houseType, String houseKey) {
        SharedPreferences preferences = null;
        if (houseType.equalsIgnoreCase(Constants.NON_SUBSIDY_TYPE)) {
            preferences = context.getSharedPreferences(NON_SUB_HOME, Context.MODE_PRIVATE);
        } else {
            preferences = context.getSharedPreferences(SUB_HOME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(houseKey);
        editor.commit();
        HouseDataFileController.removeHouseData(houseKey, context);
    }

    public void updateHouseKeyModel(String homeKey) {
        SharedPreferences.Editor editor = preferences.edit();
        String houseKeyData = preferences.getString(homeKey, null);
        if (houseKeyData == null)
            return;
        HouseKeyDataModel nonSubHomeListModel = (new Gson()).fromJson(houseKeyData, HouseKeyDataModel.class);
        nonSubHomeListModel.setUpdatedOn("" + (System.currentTimeMillis() / 1000L));
        editor.putString(homeKey, (new Gson()).toJson(nonSubHomeListModel));
        editor.commit();
    }

    public HouseKeyDataModel getHouseKeyModel(String homeKey) {
        String houseKeyData = preferences.getString(homeKey, null);
        if (houseKeyData == null)
            return null;
        return (new Gson()).fromJson(houseKeyData, HouseKeyDataModel.class);
    }

}
