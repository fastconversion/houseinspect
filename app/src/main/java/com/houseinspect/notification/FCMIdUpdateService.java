package com.houseinspect.notification;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.houseinspect.activity.controller.DataController;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.SuccessResponse;
import com.houseinspect.network.HouseLabApi;
import com.houseinspect.network.service.HouseLabService;
import com.houseinspect.network.utils.Callback;
import com.houseinspect.network.utils.Result;
import com.houseinspect.util.Constants;

import retrofit.RetrofitError;

/**
 * Created by Lalit on 1/3/2017.
 */
public class FCMIdUpdateService extends IntentService {

    public FCMIdUpdateService() {
        super(FCMIdUpdateService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.FCM_SHARED_PREF, 0);
        String fcmId = pref.getString("regId",null);
        if(fcmId == null) {
            return;
        }
        RegisterData registerData = (new DataController(this)).getUserData();
        if(registerData == null){
            return;
        }
        boolean isUploaded =  pref.getBoolean("isUploaded",false);
        if(isUploaded)
            return;

        HouseLabService service = (new HouseLabApi()).getHouseLabService();
        service.updateFCMID(fcmId, registerData.getUserId(), new Callback<SuccessResponse>() {
            @Override
            public void success(Result<SuccessResponse> result) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isUploaded", true);
                editor.commit();
            }

            @Override
            public void failure(RetrofitError error, int code) {

            }

            @Override
            public void onNetworkFail(String errorMessage) {

            }
        });

    }


}
