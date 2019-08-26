package com.daus.notificationexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    //full documentation https://developer.android.com/training/notify-user/build-notification

    private final Context context;
    private final NotificationManagerCompat manager;
    private int channelImportanceLevel = NotificationManager.IMPORTANCE_DEFAULT;
    private String channelID;
    private String channelName;
    private String channelDescription;

    public NotificationHelper(Context context) {
        this.context = context;
        channelID = context.getResources().getString(R.string.app_name);
        channelName = context.getResources().getString(R.string.app_name);
        channelDescription = context.getResources().getString(R.string.app_name);

        manager = NotificationManagerCompat.from(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNotificationChannel(String channelID, String channelName, String channelDescription, int channelImportanceLevel) {
        this.channelID = channelID;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        if (channelImportanceLevel < NotificationManager.IMPORTANCE_NONE || channelImportanceLevel > NotificationManager.IMPORTANCE_MAX) {
            this.channelImportanceLevel = NotificationManager.IMPORTANCE_DEFAULT;
        }
    }

    private NotificationManager createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelID, channelName, channelImportanceLevel);
            channel.setDescription(channelDescription);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            return notificationManager;
        }
        return null;
    }


    public void addSimpleNotification(int notifID,// if you use this with same notifID, the other one will replaced
                                       int smallIcon, String contentTitle, String message, // NotificationCompat.PRIORITY_DEFAULT as Priority
                                       Class<?> objectActivity, int requestCode, int flags//if you want to click the notification and open some Activity, set null if you don't want use this
    ) {
        createNotificationChannel();

        PendingIntent pendingIntent = null;
        if (objectActivity != null) {
            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(context, objectActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(smallIcon)//from drawable
                .setContentTitle(contentTitle)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }


        // Add as notification
        if (manager != null) {
            manager.notify(notifID, builder.build());
        }
    }

    public NotificationManagerCompat getManager() {
        return manager;
    }

    public void cancelNotification(int notifID) {
        manager.cancel(notifID);
    }
}
