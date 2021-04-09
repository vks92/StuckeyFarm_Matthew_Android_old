package com.stuckeyfarm.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stuckeyfarm.R;
import com.stuckeyfarm.Util.SavePref;
import com.stuckeyfarm.ui.activity.HomeActivity;

import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";
    SavePref savePref;
    String new_token = "";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.d(TAG, "NEW_TOKEN: " + token);
        savePref = new SavePref(getApplicationContext());
        sendRegistrationToActivity(token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        if (remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);
        String message = "";

        try {

            message = object.optString("message");
            Log.e("message", "++++++++++" + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "StuckyFarm", NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorAccent);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        Intent intent;

        if (message.contains("sent you a Friend request")) {

            intent = new Intent(this, HomeActivity.class);

        } else {

            intent = new Intent(this, HomeActivity.class);


        }

//        intent.putExtra("BackPress", "Notify");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.app_logo)
                .setContentIntent(pendingIntent)
//                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("StuckyFarm")
                .setContentText(message);
//                .setContentInfo("Info");

          notificationManager.notify(/*notification id*/1, notificationBuilder.build());

//   NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.icon_app).setContentTitle("Lokahi").setContentText(message).setAutoCancel(true);
//   NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//   notificationManager.notify(1410, notificationBuilder.build());

    }

    private void sendRegistrationToActivity(String token) {

        if (token != null && !token.isEmpty()) {

            Log.e(TAG, "++token++113++" + token);

            savePref.setFirebaseToken(token);
        }
    }

}


