package edu.wmich.cs.johnharvey.weatherhw4_johnharvey;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by John on 6/19/2017.
 */

public class RebootReceiver extends BroadcastReceiver {


    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ServiceClass.class);
        serviceIntent.putExtra("caller", "RebootReceiver");
        context.startService(serviceIntent);
    }



/*
    public void scheduleAlarms(Context context, Notification notification){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        long alarmStart = calendar.get(Calendar.MILLISECOND) + 60000;
        long alarmFinish = 60000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStart, alarmFinish, pendingIntent);
    }
    */


}
