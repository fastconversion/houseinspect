package com.houseinspect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.houseinspect.activity.controller.DataController;
import com.houseinspect.activity.mainactivity.LoginActivity;
import com.houseinspect.activity.mainactivity.MainActivity;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.notification.FCMIdUpdateService;
import com.houseinspect.util.Constants;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((TextView) findViewById(R.id.textView_versionName)).setText(R.string.version);
        handler = new Handler();
        //TODO start intent Service...
    }


    private boolean isFCMFound() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.FCM_SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        if (!TextUtils.isEmpty(regId)){
            return true;
        }
        else{
            return false;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startLoginActivity();
        }
    };

    private void startLoginActivity() {
        Intent intentService = new Intent(this, FCMIdUpdateService.class);
        startService(intentService);
        RegisterData registerData = (new DataController(SplashActivity.this)).getUserData();
        if (registerData == null)
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        else
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null)
            handler.removeCallbacks(runnable);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(handler != null)
            handler.postDelayed(runnable, 2000L);
    }

}
