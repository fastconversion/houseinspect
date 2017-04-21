package com.houseinspect.notification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.houseinspect.util.Constants;

/**
 * Created by Lalit on 1/3/2017.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);
        Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        FirebaseMessaging.getInstance().subscribeToTopic("TopicName");
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.FCM_SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.putBoolean("isUploaded", false);
        editor.commit();
        Intent intentService = new Intent(this, FCMIdUpdateService.class);
        startService(intentService);
    }
}