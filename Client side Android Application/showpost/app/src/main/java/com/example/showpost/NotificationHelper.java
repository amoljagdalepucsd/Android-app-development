package com.example.showpost;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class NotificationHelper extends ContextWrapper {
    private static final String channel_id="com.example.showpost";
    private static final String channel_name="showpost";
    public NotificationManager manager;
    public NotificationHelper(Context base){
        super(base);
        createChannels();
    }


    private void createChannels (){
        NotificationChannel channel= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(android.R.color.holo_green_dark);
            getManager().createNotificationChannel(channel);
        }
    }
    public NotificationManager getManager(){
        if (manager==null)
            manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getchannelnotification(String title, String body, PendingIntent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(),channel_id).setContentText(body).setContentTitle(title).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setContentIntent(intent);
        }


        return null;
    }


}
