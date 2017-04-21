package com.houseinspect;

import android.app.Application;

import com.houseinspect.model.HouseDataModel;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Lalit on 11/14/2016.
 */
public class HouseInspectApplication extends Application {

    private static HouseInspectApplication mInstance;
    private HouseDataModel nonHouseDataModel;
    private String homeKey;

    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance =  this;
    }

    public HouseInspectApplication getInstance(){
        return mInstance;
    }

    public static HouseInspectApplication getmInstance() {
        return mInstance;
    }


    public HouseDataModel getNonHouseDataModel() {
        if(nonHouseDataModel ==  null){
            nonHouseDataModel = new HouseDataModel();
        }
        return nonHouseDataModel;
    }

    public void setNonHouseDataModel(HouseDataModel nonHouseDataModel) {
        this.nonHouseDataModel = nonHouseDataModel;
    }

    public void setNonSubKey(String homeKey) {
        this.homeKey =  homeKey;
    }

    public String getHomeKey() {
        return homeKey;
    }

    public void setHomeKey(String homeKey) {
        this.homeKey = homeKey;
    }
}
