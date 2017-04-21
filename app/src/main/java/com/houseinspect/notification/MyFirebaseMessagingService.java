package com.houseinspect.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.houseinspect.DummyBroadcast;
import com.houseinspect.R;
import com.houseinspect.activity.controller.HouseEnrolledController;
import com.houseinspect.model.HouseKeyDataModel;
import com.houseinspect.notification.model.Notification;
import com.houseinspect.util.Constants;

import org.json.JSONObject;

/**
 *
 * Created by Lalit on 1/3/2017.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null)
            return;
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject data = json.getJSONObject("msg");
                handleDataMessage((new Gson()).fromJson(data.toString(), Notification.class));
            } catch (Exception e) {
            }
        }
    }

    private void handleDataMessage(Notification notification) {
        switch (notification.getNotificationType()) {
            case "assignment":
                createAssignment(notification);
                break;
            case "re_inspection":
                createReInspection(notification);
                break;
            case "general_notification":
                //createNotification(notification.getNotificationType());
                break;
        }
    }

    private void createReInspection(Notification notification) {
        if(notification.getPayloadType().startsWith("sub")){
            HouseKeyDataModel houseKeyDataModel = (new Gson()).fromJson((new Gson()).toJson(notification.getPayload()),HouseKeyDataModel.class);
            String key = Constants.getSubHouseKey(houseKeyDataModel.getDemographicDetails());
            houseKeyDataModel.setMobileKey(key);
            (new HouseEnrolledController(this, true)).addNewNonSubHome(key, houseKeyDataModel);
            createNotification("New Subsidy House assign for Re-Inspection");
        }else {
            HouseKeyDataModel houseKeyDataModel = (new Gson()).fromJson((new Gson())
                    .toJson(notification.getPayload()),HouseKeyDataModel.class);
            String key = Constants.getNonSubHouseKey(houseKeyDataModel.getDemographicDetails());
            houseKeyDataModel.setMobileKey(key);
            (new HouseEnrolledController(this)).addNewNonSubHome(key, houseKeyDataModel);
            createNotification("New Non-Subsidy House assign for Re-Inspection");
        }
    }

    private void createAssignment(Notification notification) {
        if(notification.getPayloadType().startsWith("sub")){
            HouseKeyDataModel houseKeyDataModel = (new Gson()).fromJson((new Gson()).toJson(notification.getPayload()),HouseKeyDataModel.class);
            String key = Constants.getSubHouseKey(houseKeyDataModel.getDemographicDetails());
            houseKeyDataModel.setMobileKey(key);
            (new HouseEnrolledController(this, true)).addNewNonSubHome(key, houseKeyDataModel);
            createNotification("New Subsidy House assign for inspection");
        }else {
            HouseKeyDataModel houseKeyDataModel = (new Gson()).fromJson((new Gson())
                    .toJson(notification.getPayload()),HouseKeyDataModel.class);
            String key = Constants.getNonSubHouseKey(houseKeyDataModel.getDemographicDetails());
            houseKeyDataModel.setMobileKey(key);
            (new HouseEnrolledController(this)).addNewNonSubHome(key, houseKeyDataModel);
            createNotification("New Non-Subsidy House assign for inspection");
        }
    }

    private void createNotification(String message) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setContentTitle("HouseInspect");
        nBuilder.setContentText(message);
        nBuilder.setTicker("HouseInspect");
        nBuilder.setAutoCancel(true);
        nBuilder.setSmallIcon(R.mipmap.ic_launcher);
        nBuilder.setNumber(1);

        Intent intent = new Intent(this, DummyBroadcast.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        nBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(123, nBuilder.build());
    }

}
