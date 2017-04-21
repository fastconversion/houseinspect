package com.houseinspect.network;

import android.content.Context;

import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.util.Constants;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by Lalit on 6/28/2016.
*/
public class HouseLabApi {

    private Context context;

    public HouseLabApi() {
    }

    private RestAdapter restAdapter(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .setClient(new OkClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(Constants.APP_TAG))
                .build();
        return adapter;
    }

    public <T> T  getService(Class<T> service){
        return restAdapter().create(service);
    }

    public HouseLabService getHouseLabService(){
        return this.getService(HouseLabService.class);
    }

}
